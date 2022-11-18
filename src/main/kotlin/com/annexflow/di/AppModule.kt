package com.annexflow.di

import com.annexflow.database.Database
import com.annexflow.database.DatabaseConfig
import com.annexflow.database.datasource.BaseLibraryDataSource
import com.annexflow.database.datasource.LibraryDataSource
import com.annexflow.features.dispersion.mapper.DispersionMapper
import com.annexflow.features.library.interactors.AddLibraryInteractor
import com.annexflow.features.library.interactors.BaseAddLibraryInteractor
import com.annexflow.features.library.interactors.BaseGetLibraryByIDInteractor
import com.annexflow.features.library.interactors.GetLibraryByIDInteractor
import com.annexflow.system.BaseDispersionAnalyzerFlow
import com.annexflow.system.DispersionAnalyzer
import com.annexflow.system.DispersionAnalyzerFlow
import com.typesafe.config.ConfigFactory
import org.koin.dsl.module

/**
 * @author Lavmee on 02.09.2022
 **/

val appModule = module {
    single<DispersionAnalyzer> {
        DispersionAnalyzer.Base()
    }

    single<DispersionAnalyzerFlow> {
        BaseDispersionAnalyzerFlow()
    }

    single<DispersionMapper> {
        DispersionMapper.Base()
    }

    single<LibraryDataSource> { BaseLibraryDataSource() }

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

    single<AddLibraryInteractor> { BaseAddLibraryInteractor(get(), get()) }
    single<GetLibraryByIDInteractor> { BaseGetLibraryByIDInteractor(get(), get()) }
}