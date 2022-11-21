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

    override suspend operator fun invoke(libraryPath: String): Int? {
        val library = database.databaseTransaction {
            val libraryNoPath = libraryDataSource.insertLibrary(libraryPath = "")
            if (libraryNoPath != null) {
                libraryDataSource.changePath(
                    libraryId = libraryNoPath.id,
                    libraryPath = "$libraryPath${libraryNoPath.id}$JAR_EXTENSION"
                )
            }
            libraryNoPath
        }
        return library?.id
    }
}