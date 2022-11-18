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
        try {
            val request = thisConnection.session.receiveDeserialized<CalculateParameters>()
            val library = getLibraryByIDInteractor.invoke(request.libraryId)
            if (library == null) {
                call.respond(HttpStatusCode.BadRequest, "Library not found!")
                return@webSocket
            }

            val jarFile = File(library.libraryPath)
            val child = URLClassLoader(arrayOf<URL>(jarFile.toURI().toURL()), this.javaClass.classLoader)
            val classToLoad = Class.forName(library.className, true, child)
            val constructorParameters = buildList { repeat(request.constructorValues.size) { add(String::class.java) } }

            val constructor = classToLoad.getDeclaredConstructor(*constructorParameters.toTypedArray())
            val instance = constructor.newInstance(*request.constructorValues.toTypedArray())
            request.methods.forEach { methodName ->
                val method: Method = classToLoad.getDeclaredMethod(methodName)
                val result: String = method.invoke(instance) as String
                println("$methodName: $result")
                if (request.isDelayed) { delay(delayTime) }
                thisConnection.session.sendSerialized(result)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            thisConnection.session.sendSerialized(e.localizedMessage)
        } finally {
            connections -= thisConnection
        }
    }
}

val delayTime
    get() = (500L..1200L).random()
