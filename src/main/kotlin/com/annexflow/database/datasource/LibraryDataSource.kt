package com.annexflow.database.datasource

import com.annexflow.database.entity.Library


/**
 * @author Lavmee on 18.11.2022
 **/

interface LibraryDataSource {
    suspend fun insertLibrary(libraryPath: String): Library?
    suspend fun retrieveLibrary(libraryId: Int): Library?
    suspend fun retrieveLastLibrary(): Library?
    suspend fun deleteLibrary(libraryId: Int) : Boolean
    suspend fun changePath(libraryId: Int, libraryPath: String)
}