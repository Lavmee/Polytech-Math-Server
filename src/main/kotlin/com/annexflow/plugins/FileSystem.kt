package com.annexflow.plugins

import io.ktor.server.application.*
import io.ktor.server.application.*
import java.io.File

/**
 * @author Lavmee on 02.09.2022
 **/

const val LIBRARY_DIRECTORY_PATH = "/root/polytech/libraries/"
const val JAR_EXTENSION = ".jar"

val FileSystemPlugin = createApplicationPlugin(name = "FileSystemPlugin") {
    val librariesDirectory = File(LIBRARY_DIRECTORY_PATH)
    if (!librariesDirectory.exists()) {
        librariesDirectory.mkdirs()
    }
}

fun Application.configureFileSystem() {
    install(FileSystemPlugin)
}
