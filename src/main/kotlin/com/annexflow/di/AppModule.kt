package com.annexflow.di

import com.annexflow.database.Database
import com.annexflow.database.DatabaseConfig
import com.annexflow.database.datasource.BaseLibraryDataSource
import com.annexflow.database.datasource.LibraryDataSource
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.features.library.interactors.impl.BaseAddLibraryInteractor
import com.annexflow.features.library.interactors.impl.BaseGetLibraryByIDInteractor
import com.annexflow.features.library.interactors.GetLibraryByIDInteractor
import com.annexflow.features.library.interactors.RemoveLibraryInteractor
import com.annexflow.features.library.interactors.impl.BaseRemoveLibraryInteractor
import com.typesafe.config.ConfigFactory
import org.koin.dsl.module

/**
 * @author Lavmee on 02.09.2022
 **/

val appModule = module {

    factory {
        DatabaseConfig(
            driverClassName = ConfigFactory.load().getString("database.driverClassName").toString(),
            jdbcURL = ConfigFactory.load().getString("database.jdbcURL").toString(),
            username = ConfigFactory.load().getString("database.user").toString(),
            password = ConfigFactory.load().getString("database.password").toString(),
            database = ConfigFactory.load().getString("database.database").toString(),
        )
    }

    single { Database(get()) }
    single<LibraryDataSource> { BaseLibraryDataSource() }

    single<AddLibraryInteractor> { BaseAddLibraryInteractor(get(), get()) }
    single<RemoveLibraryInteractor> { BaseRemoveLibraryInteractor(get(), get()) }
    single<GetLibraryByIDInteractor> { BaseGetLibraryByIDInteractor(get(), get()) }
}