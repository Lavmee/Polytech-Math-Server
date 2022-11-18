package com.annexflow.features.library.interactors.impl

import com.annexflow.database.Database
import com.annexflow.database.datasource.LibraryDataSource
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.plugins.JAR_EXTENSION


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseAddLibraryInteractor(
    private val database: Database,
    private val libraryDataSource: LibraryDataSource
) : AddLibraryInteractor {

    override suspend operator fun invoke(libraryPath: String, className: String): Int? {
        val library = database.databaseTransaction {
            val lastLibrary = libraryDataSource.retrieveLastLibrary()
            libraryDataSource.insertLibrary(
                libraryPath = "$libraryPath${lastLibrary.id + 1}$JAR_EXTENSION",
                className = className
            )
        }
        return library?.id
    }
}