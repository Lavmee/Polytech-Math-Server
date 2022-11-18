package com.annexflow.features.library.routes

import com.annexflow.features.library.exchanges.AddLibraryParameters
import com.annexflow.features.library.exchanges.AddLibraryResponse
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.plugins.JAR_EXTENSION
import com.annexflow.plugins.LIBRARY_DIRECTORY_PATH
import com.annexflow.plugins.configureFileSystem
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.ktor.ext.inject
import java.io.File

fun Route.upload() {
    val addLibraryInteractor by inject<AddLibraryInteractor>()
    post<AddLibraryParameters> { parameters ->
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
            libraryPath = LIBRARY_DIRECTORY_PATH,
            className = parameters.className
        )
        if (libraryId == null) {
            call.respond(HttpStatusCode.BadRequest, "Error save to database.")
            return@post
        }
        configureFileSystem()
        val filePath = "$LIBRARY_DIRECTORY_PATH$libraryId$JAR_EXTENSION"
        val file = File(filePath)
        parts.forEach {
            withContext(Dispatchers.IO) {
                val fileBytes = it.streamProvider().readBytes()
                file.createNewFile()
                file.writeBytes(fileBytes)
            }
            it.dispose()
        }

        call.respond(HttpStatusCode.OK, AddLibraryResponse(id = libraryId))
    }
}