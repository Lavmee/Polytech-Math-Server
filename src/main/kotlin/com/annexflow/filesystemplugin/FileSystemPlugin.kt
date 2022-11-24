package com.annexflow.filesystemplugin

import io.ktor.server.application.*

val FileSystemPlugin =
    createApplicationPlugin(name = "FileSystemPlugin", createConfiguration = { FileSystemApplication.init() }) {
    }

