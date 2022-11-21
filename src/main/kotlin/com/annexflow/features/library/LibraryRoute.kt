package com.annexflow.features.library

import com.annexflow.features.library.routes.calculate
import com.annexflow.features.library.routes.remove
import com.annexflow.features.library.routes.upload
import io.ktor.server.application.*
import io.ktor.server.routing.*


/**
 * @author Lavmee on 16.11.2022
 **/

const val API_LIBRARY_KEYWORD = "library"

fun Application.libraryRoutes() {
    routing {
        upload()
        remove()
        calculate()
    }
}