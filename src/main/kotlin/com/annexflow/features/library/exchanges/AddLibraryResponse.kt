package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddLibraryResponse(
    @SerialName("id")
    val id: Int
)