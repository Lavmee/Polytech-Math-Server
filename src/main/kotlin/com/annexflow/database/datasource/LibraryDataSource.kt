package com.annexflow.database.datasource

import com.annexflow.database.entity.Library


/**
 * @author Lavmee on 18.11.2022
 **/

interface LibraryDataSource {
    suspend fun insertLibrary(libraryPath: String, className: String): Library?
    suspend fun retrieveLibrary(libraryId: Int): Library?
    suspend fun retrieveLastLibrary(): Library
}