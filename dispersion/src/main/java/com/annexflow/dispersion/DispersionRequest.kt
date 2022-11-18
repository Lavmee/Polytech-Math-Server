package com.annexflow.dispersion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DispersionRequest(
    @SerialName("values")
    val values: List<Double>,
    @SerialName("factor_a_gradations_number")
    val factorAGradationsNumber: Int,
    @SerialName("factor_b_gradations_number")
    val factorBGradationsNumber: Int,
    @SerialName("repeat_number")
    val repeatNumber: Int
)