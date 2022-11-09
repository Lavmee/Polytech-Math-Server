package com.annexflow.features.dispersion.interactors

import com.annexflow.features.dispersion.exchanges.Dispersion
import com.annexflow.features.dispersion.exchanges.DispersionParameters
import com.annexflow.features.dispersion.exchanges.DispersionResult
import com.annexflow.features.dispersion.mapper.DispersionMapper
import com.annexflow.system.DispersionAnalyzer
import com.annexflow.system.DispersionAnalyzerFlow
import kotlinx.coroutines.flow.Flow

/**
 * @author Lavmee on 02.09.2022
 **/

interface CalculateDispersionInteractor {
    suspend fun default(request: DispersionParameters): DispersionResult
    suspend fun flow(request: DispersionParameters): Flow<Dispersion>

    class Base(
        private val analyzer: DispersionAnalyzer,
        private val analyzerFlow: DispersionAnalyzerFlow,
        private val dispersionMapper: DispersionMapper
    ) : CalculateDispersionInteractor {
        override suspend fun default(request: DispersionParameters): DispersionResult {
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

        override suspend fun flow(request: DispersionParameters): Flow<Dispersion> {
            val mappedValue = dispersionMapper.map(
                value = request.values,
                a = request.factorAGradationsNumber,
                b = request.factorBGradationsNumber,
                r = request.repeatNumber
            )
            return analyzerFlow.calculateDispersion(
                value = mappedValue,
                factorAGradationsNumber = request.factorAGradationsNumber,
                factorBGradationsNumber = request.factorBGradationsNumber,
                repeat = request.repeatNumber
            )
        }
    }
}