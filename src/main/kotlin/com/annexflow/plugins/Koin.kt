package com.annexflow.plugins

import com.annexflow.di.*
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.SLF4JLogger

/**
 * @author Lavmee on 02.09.2022
 **/

fun Application.configureKoin() {
    install(Koin) {
        SLF4JLogger()
        modules(appModule)
    }
}