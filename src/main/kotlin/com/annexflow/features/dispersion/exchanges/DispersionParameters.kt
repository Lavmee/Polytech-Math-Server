package com.annexflow.features.dispersion.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Lavmee on 02.09.2022
 **/

@Serializable
data class DispersionParameters(
    @SerialName("values")
    val values: List<Double>,
    @SerialName("factor_a_gradations_number")
    val factorAGradationsNumber: Int,
    @SerialName("factor_b_gradations_number")
    val factorBGradationsNumber: Int,
    @SerialName("repeat_number")
    val repeatNumber: Int
)