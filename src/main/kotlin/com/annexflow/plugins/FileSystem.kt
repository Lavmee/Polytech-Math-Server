package com.annexflow.plugins

import io.ktor.server.application.*
import java.io.File

/**
 * @author Lavmee on 02.09.2022
 **/

fun Application.configureFileSystem() {
    val librariesDirectory = File("libraries/")
    if (!librariesDirectory.exists()) {
        librariesDirectory.mkdir()
    }
}
