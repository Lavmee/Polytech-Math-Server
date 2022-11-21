package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CalculateParameters(
    @SerialName("library_id")
    val libraryId: Int,
    @SerialName("classes")
    val classes: List<ClassModel>,
    @SerialName("is_delayed")
    val isDelayed: Boolean = false
)
