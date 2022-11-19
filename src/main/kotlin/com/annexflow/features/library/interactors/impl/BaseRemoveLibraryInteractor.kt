package com.annexflow.features.library.interactors.impl

import com.annexflow.database.Database
import com.annexflow.database.datasource.LibraryDataSource
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.features.library.interactors.RemoveLibraryInteractor
import com.annexflow.plugins.JAR_EXTENSION


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseRemoveLibraryInteractor(
    private val database: Database,
    private val libraryDataSource: LibraryDataSource
) : RemoveLibraryInteractor {

    override suspend operator fun invoke(libraryId: Int): Boolean {
        val library = database.databaseTransaction {
            libraryDataSource.deleteLibrary(libraryId)
        }
        return library
    }
}