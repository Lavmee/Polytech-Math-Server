package com.annexflow.plugins

import com.annexflow.features.dispersion.dispersionRoutes
import io.ktor.server.application.*

/**
 * @author Lavmee on 02.09.2022
 **/

fun Application.configureRouting() {
    dispersionRoutes()
}
