package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassModel(
    @SerialName("name")
    val name: String,
    @SerialName("constructor_parameters")
    val constructorParameters: List<String>,
    @SerialName("methods")
    val methods: List<MethodModel>
)

