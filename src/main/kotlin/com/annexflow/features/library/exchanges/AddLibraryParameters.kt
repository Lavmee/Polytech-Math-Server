package com.annexflow.features.library.exchanges

import com.annexflow.features.library.API_LIBRARY_KEYWORD
import io.ktor.resources.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Lavmee on 02.09.2022
 **/

@Serializable
@Resource("$API_LIBRARY_KEYWORD.addLibrary")
data class AddLibraryParameters(
    @SerialName("class_name")
    val className: String
)