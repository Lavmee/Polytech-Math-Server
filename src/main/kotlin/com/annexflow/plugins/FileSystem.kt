package com.annexflow.plugins

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author Lavmee on 02.09.2022
 **/

const val LIBRARY_DIRECTORY_PATH = "polytech/libraries/"
const val JAR_EXTENSION = ".jar"

suspend fun configureFileSystem() {
    withContext(Dispatchers.IO) {
        val librariesDirectory = File(LIBRARY_DIRECTORY_PATH)
        if (!librariesDirectory.exists()) {
            librariesDirectory.mkdirs()
        }
    }
}
