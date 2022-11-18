package com.annexflow.features.library

import com.annexflow.features.library.exchanges.AddLibraryParameters
import com.annexflow.features.library.exchanges.CalculateParameters
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.features.library.interactors.GetLibraryByIDInteractor
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ktor.ext.inject
import java.io.File
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader


/**
 * @author Lavmee on 16.11.2022
 **/

const val API_LIBRARY_KEYWORD = "library"

fun Application.libraryRoutes() {
    routing {
        upload()
        calculate()
    }
}

fun Route.upload() {
    val addLibraryInteractor by inject<AddLibraryInteractor>()

    post<AddLibraryParameters> { parameters ->
        val multiPartData = call.receiveMultipart()
        var filePath = ""
        multiPartData.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val fileName = part.originalFileName as String
                    if (!fileName.endsWith(".jar")) {
                        call.respond(HttpStatusCode.BadRequest, "NOT JAR")
                        return@forEachPart
                    }
                    val fileBytes = part.streamProvider().readBytes()
                    val file = File("libraries/$fileName")
                    withContext(Dispatchers.IO) {
                        file.createNewFile()
                    }
                    file.writeBytes(fileBytes)
                    filePath = file.absolutePath
                }

                else -> {}
            }
            part.dispose()
        }
        val libraryId = addLibraryInteractor.invoke(libraryPath = filePath, className = parameters.className)
        call.respond(HttpStatusCode.OK, libraryId)

    }
}

fun Route.calculate() {
    val getLibraryByIDInteractor by inject<GetLibraryByIDInteractor>()

    get<CalculateParameters> { parameters ->
        val library = getLibraryByIDInteractor.invoke(parameters.libraryId)

        if (library == null) {
            call.respond(HttpStatusCode.BadRequest, "Library not found!")
            return@get
        }

        val jarFile = File(library.libraryPath)
        val child = URLClassLoader(
            arrayOf<URL>(jarFile.toURI().toURL()),
            this.javaClass.classLoader
        )
        val classToLoad = Class.forName(library.className, true, child)

        val constructorParameters = parameters.constructorParameters.map { Class.forName(it) }
        val constructor = classToLoad.getDeclaredConstructor(*constructorParameters.toTypedArray())
        val instance = constructor.newInstance(parameters.constructorValues)

        val response = parameters.methods.map { methodName ->
            val method: Method = classToLoad.getDeclaredMethod(methodName)
            val result: Any = method.invoke(instance)
            result
        }

        call.respond(HttpStatusCode.OK, response.toString())
    }
}