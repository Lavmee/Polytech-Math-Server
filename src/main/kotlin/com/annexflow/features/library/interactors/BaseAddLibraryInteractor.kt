package com.annexflow.features.library.interactors

import com.annexflow.database.Database
import com.annexflow.database.datasource.LibraryDataSource


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseAddLibraryInteractor(
    private val database: Database,
    private val libraryDataSource: LibraryDataSource
) : AddLibraryInteractor {

    override suspend operator fun invoke(libraryPath: String, className: String): Int {
        val library = database.databaseTransaction {
            libraryDataSource.insertLibrary(libraryPath = libraryPath, className = className)
        } ?: return -1

        return library.id
    }
}