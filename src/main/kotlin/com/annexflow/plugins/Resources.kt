package com.annexflow.plugins

import io.ktor.server.application.*
import io.ktor.server.resources.*

/**
 * @author Lavmee on 02.09.2022
 **/

fun Application.configureLocations() {
    install(Resources)
}
