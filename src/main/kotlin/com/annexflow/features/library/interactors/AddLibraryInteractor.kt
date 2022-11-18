package com.annexflow.features.library.interactors


/**
 * @author Lavmee on 18.11.2022
 **/

interface AddLibraryInteractor {

    suspend operator fun invoke(libraryPath: String, className: String) : Int

}