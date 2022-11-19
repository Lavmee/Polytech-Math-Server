package com.annexflow.features.library.interactors

/**
 * @author Lavmee on 18.11.2022
 **/

interface RemoveLibraryInteractor {
    suspend operator fun invoke(libraryId: Int) : Boolean

}