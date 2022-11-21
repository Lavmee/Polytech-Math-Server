package com.annexflow.features.library.routes

import com.annexflow.features.library.API_LIBRARY_KEYWORD
import com.annexflow.features.library.exchanges.LibraryIdResponse
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.plugins.JAR_EXTENSION
import com.annexflow.plugins.LIBRARY_DIRECTORY_PATH
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ktor.ext.inject
import java.io.File

fun Route.upload() {
    val addLibraryInteractor by inject<AddLibraryInteractor>()
    post("$API_LIBRARY_KEYWORD.upload") {
        val multiPartData = call.receiveMultipart()
        val parts = multiPartData.readAllParts().filterIsInstance<PartData.FileItem>()
        val fileName = parts.first().originalFileName
        if (fileName == null) {
            call.respond(HttpStatusCode.BadRequest, "Incorrect filename")
            return@post
        }
        if (!fileName.endsWith(JAR_EXTENSION)) {
            call.respond(HttpStatusCode.BadRequest, "Not jar file")
            return@post
        }

        val libraryId = addLibraryInteractor.invoke(
            libraryPath = LIBRARY_DIRECTORY_PATH
        )
        if (libraryId == null) {
            call.respond(HttpStatusCode.BadRequest, "Error save to database.")
            return@post
        }
        val filePath = "$LIBRARY_DIRECTORY_PATH$libraryId$JAR_EXTENSION"
        val file = File(filePath)
        withContext(Dispatchers.IO) {
            file.createNewFile()
            parts.forEach {
                val fileBytes = it.streamProvider().readBytes()
                file.writeBytes(fileBytes)
                it.dispose()
            }
        }

        call.respond(HttpStatusCode.OK, LibraryIdResponse(id = libraryId))
    }
}