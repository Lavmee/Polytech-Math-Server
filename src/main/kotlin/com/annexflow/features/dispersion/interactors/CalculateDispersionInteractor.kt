package com.annexflow.features.dispersion.interactors

import com.annexflow.features.dispersion.exchanges.DispersionParameters
import com.annexflow.features.dispersion.exchanges.DispersionResult
import com.annexflow.features.dispersion.mapper.DispersionMapper
import com.annexflow.system.DispersionAnalyzer

/**
 * @author Lavmee on 02.09.2022
 **/

interface CalculateDispersionInteractor {
    suspend operator fun invoke(request: DispersionParameters): DispersionResult

    class Base(
        private val analyzer: DispersionAnalyzer,
        private val dispersionMapper: DispersionMapper
    ) : CalculateDispersionInteractor {
        override suspend fun invoke(request: DispersionParameters): DispersionResult {
            val mappedValue = dispersionMapper.map(
                value = request.values,
                a = request.factorAGradationsNumber,
                b = request.factorBGradationsNumber,
                r = request.repeatNumber
            )
            return analyzer.calculateDispersion(
                value = mappedValue,
                factorAGradationsNumber = request.factorAGradationsNumber,
                factorBGradationsNumber = request.factorBGradationsNumber,
                repeat = request.repeatNumber
            )
        }
    }
}