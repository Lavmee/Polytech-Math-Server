package com.annexflow.features.library.routes

import com.annexflow.features.library.API_LIBRARY_KEYWORD
import com.annexflow.features.library.Connection
import com.annexflow.features.library.exchanges.CalculateParameters
import com.annexflow.features.library.interactors.GetLibraryByIDInteractor
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.delay
import org.koin.ktor.ext.inject
import java.io.File
import java.lang.reflect.Method
import java.net.URL
import java.net.URLClassLoader
import java.util.*

fun Route.calculate() {
    val getLibraryByIDInteractor by inject<GetLibraryByIDInteractor>()
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket("$API_LIBRARY_KEYWORD.calculate") {
        val thisConnection = Connection(this)
        connections += thisConnection

        val request = thisConnection.session.receiveDeserialized<CalculateParameters>()
        println("Request Params: $request")
        val library = getLibraryByIDInteractor.invoke(request.libraryId)
        if (library == null) {
            println("Library not found!")
            call.respond(HttpStatusCode.BadRequest, "Library not found!")
            return@webSocket
        }
        val jarFile = File(library.libraryPath)
        println("filepath: ${jarFile.absolutePath}")
        val child = URLClassLoader(arrayOf<URL>(jarFile.toURI().toURL()), this.javaClass.classLoader)

        try {
            request.classes.forEach { classModel ->
                val classToLoad = Class.forName(classModel.name, true, child)
                val constructorParameters =
                    buildList { repeat(classModel.constructorParameters.size) { add(String::class.java) } }
                val constructor = classToLoad.getDeclaredConstructor(*constructorParameters.toTypedArray())
                val instance = constructor.newInstance(*classModel.constructorParameters.toTypedArray())

                classModel.methods.forEach { methodModel ->
                    val method: Method = classToLoad.getDeclaredMethod(methodModel.name)
                    val methodResult: String = method.invoke(instance).toString()
                    println("${methodModel.name}: $methodResult")
                    if (request.isDelayed) {
                        delay(delayTime)
                    }
                    thisConnection.session.sendSerialized(methodResult)
                }
            }
        } catch (e: Exception) {
            println(e.toString())
        } finally {
            connections -= thisConnection
        }
    }
}

val delayTime
    get() = (500L..1200L).random()
