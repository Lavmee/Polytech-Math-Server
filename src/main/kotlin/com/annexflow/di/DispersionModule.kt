package com.annexflow.di

import com.annexflow.features.dispersion.interactors.CalculateDispersionInteractor
import com.annexflow.features.dispersion.mapper.DispersionMapper
import com.annexflow.system.DispersionAnalyzer
import org.koin.dsl.module

/**
 * @author Lavmee on 02.09.2022
 **/

val dispersionModule = module {
    single<DispersionAnalyzer> {
        DispersionAnalyzer.Base()
    }

    single<CalculateDispersionInteractor> {
        CalculateDispersionInteractor.Base(get(), get())
    }

    single<DispersionMapper> {
        DispersionMapper.Base()
    }
}