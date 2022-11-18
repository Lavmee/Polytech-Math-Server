package com.annexflow.features.library.interactors

import com.annexflow.database.entity.Library

/**
 * @author Lavmee on 18.11.2022
 **/

interface GetLibraryByIDInteractor {
    suspend operator fun invoke(libraryId: Int) : Library?

}