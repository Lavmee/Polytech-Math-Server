package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MethodModel(
    @SerialName("name")
    val name: String,
    @SerialName("parameters")
    val parameters: List<String>
)
