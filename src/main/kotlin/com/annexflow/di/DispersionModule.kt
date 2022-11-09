package com.annexflow.di

import com.annexflow.features.dispersion.interactors.CalculateDispersionInteractor
import com.annexflow.features.dispersion.mapper.DispersionMapper
import com.annexflow.system.BaseDispersionAnalyzerFlow
import com.annexflow.system.DispersionAnalyzer
import com.annexflow.system.DispersionAnalyzerFlow
import org.koin.dsl.module

/**
 * @author Lavmee on 02.09.2022
 **/

val dispersionModule = module {
    single<DispersionAnalyzer> {
        DispersionAnalyzer.Base()
    }
    single<DispersionAnalyzerFlow> {
        BaseDispersionAnalyzerFlow()
    }

    single<CalculateDispersionInteractor> {
        CalculateDispersionInteractor.Base(get(), get(), get())
    }

    single<DispersionMapper> {
        DispersionMapper.Base()
    }
}