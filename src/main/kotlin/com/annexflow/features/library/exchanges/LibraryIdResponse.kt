package com.annexflow.features.library.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LibraryIdResponse(
    @SerialName("id")
    val id: Int
)