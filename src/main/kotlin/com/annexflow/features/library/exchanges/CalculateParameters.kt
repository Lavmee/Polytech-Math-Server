package com.annexflow.features.library.exchanges

import com.annexflow.features.library.API_LIBRARY_KEYWORD
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Lavmee on 02.09.2022
 **/

@Serializable
@Resource("$API_LIBRARY_KEYWORD.calculate")
data class CalculateParameters(
    @SerialName("library_id")
    val libraryId: Int,
    @SerialName("methods")
    val methods: List<String>,
    @SerialName("constructor_parameters")
    val constructorParameters: List<String>,
    @SerialName("constructor_values")
    val constructorValues: List<String>
)