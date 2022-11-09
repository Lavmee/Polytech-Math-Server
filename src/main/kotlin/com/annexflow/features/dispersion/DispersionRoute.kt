package com.annexflow.features.dispersion

import com.annexflow.features.dispersion.exchanges.DispersionParameters
import com.annexflow.features.dispersion.interactors.CalculateDispersionInteractor
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import java.util.*

/**
 * @author Lavmee on 02.09.2022
 **/

const val API_DISPERSION_KEYWORD = "dispersion"

fun Application.dispersionRoutes() {
    routing {
        dispersionCalculate()
        dispersionCalculateFlow()
    }
}

fun Route.dispersionCalculate() {
    val calculateDispersionInteractor by inject<CalculateDispersionInteractor>()
    post("$API_DISPERSION_KEYWORD.twoWay") {
        val request = call.receive<DispersionParameters>()
        call.respond(HttpStatusCode.OK, calculateDispersionInteractor.default(request))

    }
}

fun Route.dispersionCalculateFlow() {
    val calculateDispersionInteractor by inject<CalculateDispersionInteractor>()
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    webSocket("$API_DISPERSION_KEYWORD.twoWayFlow") {
        val thisConnection = Connection(this)
        connections += thisConnection
        try {
            val request = thisConnection.session.receiveDeserialized<DispersionParameters>()
            calculateDispersionInteractor.flow(request).collect {
                thisConnection.session.sendSerialized(it)
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            thisConnection.session.sendSerialized("Wrong Data")
        } finally {
            connections -= thisConnection
        }
    }
}