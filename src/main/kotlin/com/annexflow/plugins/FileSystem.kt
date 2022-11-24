package com.annexflow.plugins

import com.annexflow.filesystemplugin.FileSystemPlugin
import io.ktor.server.application.*

/**
 * @author Lavmee on 02.09.2022
 **/

const val LIBRARY_DIRECTORY_PATH = "/root/polytech/libraries/"
const val JAR_EXTENSION = ".jar"

fun Application.configureFileSystem() {
    install(FileSystemPlugin) {
        paths(LIBRARY_DIRECTORY_PATH)
    }
}
