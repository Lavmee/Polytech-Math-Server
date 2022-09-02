package com.annexflow

import io.ktor.server.application.*
import com.annexflow.plugins.*

/**
 * @author Lavmee on 02.09.2022
 **/

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKtor()
    configureSerialization()
    configureLocations()
    configureRouting()
}
