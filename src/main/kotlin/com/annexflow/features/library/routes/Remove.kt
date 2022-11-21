package com.annexflow.features.library.routes

import com.annexflow.features.library.API_LIBRARY_KEYWORD
import com.annexflow.features.library.exchanges.LibraryIdResponse
import com.annexflow.features.library.interactors.RemoveLibraryInteractor
import com.annexflow.plugins.JAR_EXTENSION
import com.annexflow.plugins.LIBRARY_DIRECTORY_PATH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Route.remove() {
    val removeLibraryInteractor by inject<RemoveLibraryInteractor>()
    get("$API_LIBRARY_KEYWORD.remove") {
        val request = call.receive<LibraryIdResponse>()
        if (!removeLibraryInteractor(libraryId = request.id)) {
            call.respond(HttpStatusCode.InternalServerError, "Can't...")
            return@get
        }
        val file = File("$LIBRARY_DIRECTORY_PATH${request.id}$JAR_EXTENSION")
        if (file.delete()) {
            println("${file.absolutePath} was delete")
            call.respond(HttpStatusCode.OK, LibraryIdResponse(id = request.id))
            return@get
        }
        call.respond(HttpStatusCode.InternalServerError, "Can't 2...")
    }

}