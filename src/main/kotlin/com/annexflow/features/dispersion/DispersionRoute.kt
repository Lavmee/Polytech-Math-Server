package com.annexflow.features.dispersion

import io.ktor.server.application.*
import io.ktor.server.routing.*

/**
 * @author Lavmee on 02.09.2022
 **/

const val API_DISPERSION_KEYWORD = "dispersion"

fun Application.dispersionRoutes() {
    routing {
//        dispersionCalculate()
//        dispersionCalculateFlow()
    }
}

//fun Route.dispersionCalculate() {
//    val calculateDispersionInteractor by inject<CalculateDispersionInteractor>()
//    post("$API_DISPERSION_KEYWORD.twoWay") {
//        val request = call.receive<CalculateParameters>()
//        call.respond(HttpStatusCode.OK, calculateDispersionInteractor.default(request))
//
//    }
//}
//
//fun Route.dispersionCalculateFlow() {
//    val calculateDispersionInteractor by inject<CalculateDispersionInteractor>()
//    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
//    webSocket("$API_DISPERSION_KEYWORD.twoWayFlow") {
//        val thisConnection = Connection(this)
//        connections += thisConnection
//        try {
//            val request = thisConnection.session.receiveDeserialized<CalculateParameters>()
//            calculateDispersionInteractor.flow(request).collect {
//                thisConnection.session.sendSerialized(it)
//            }
//        } catch (e: Exception) {
//            println(e.localizedMessage)
//            thisConnection.session.sendSerialized("Wrong Data")
//        } finally {
//            connections -= thisConnection
//        }
//    }
//}