package com.annexflow.features.library.interactors

import com.annexflow.database.Database
import com.annexflow.database.datasource.LibraryDataSource
import com.annexflow.database.entity.Library


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseGetLibraryByIDInteractor(
    private val database: Database,
    private val libraryDataSource: LibraryDataSource
) : GetLibraryByIDInteractor {

    override suspend operator fun invoke(libraryId: Int): Library? {
        val library = database.databaseTransaction {
            libraryDataSource.retrieveLibrary(libraryId = libraryId)
        }
        return library
    }
}