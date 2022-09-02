package com.annexflow.features.dispersion

import com.annexflow.features.dispersion.exchanges.DispersionParameters
import com.annexflow.features.dispersion.interactors.CalculateDispersionInteractor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/**
 * @author Lavmee on 02.09.2022
 **/

const val API_DISPERSION_KEYWORD = "dispersion"

fun Application.dispersionRoutes(){
    routing {
        dispersionCalculate()
    }
}

fun Route.dispersionCalculate(){

    val calculateDispersionInteractor by inject<CalculateDispersionInteractor>()

    post("$API_DISPERSION_KEYWORD.twoWay") {
        val request = call.receiveOrNull<DispersionParameters>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        call.respond(HttpStatusCode.OK, calculateDispersionInteractor.invoke(request))

    }

}