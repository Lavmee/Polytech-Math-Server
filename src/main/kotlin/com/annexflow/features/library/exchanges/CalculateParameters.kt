package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalculateParameters(
    @SerialName("library_id")
    val libraryId: Int,
    @SerialName("constructor_values")
    val constructorValues: List<String>,
    @SerialName("methods")
    val methods: List<String>,
    @SerialName("is_delayed")
    val isDelayed: Boolean = false
)
