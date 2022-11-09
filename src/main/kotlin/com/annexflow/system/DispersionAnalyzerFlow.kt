package com.annexflow.system

import com.annexflow.features.dispersion.exchanges.Dispersion
import kotlinx.coroutines.flow.Flow

/**
 * @author Lavmee on 02.09.2022
 **/

interface DispersionAnalyzerFlow {
    suspend fun calculateDispersion(
        value: List<List<List<Double>>>,
        factorAGradationsNumber: Int,
        factorBGradationsNumber: Int,
        repeat: Int
    ): Flow<Dispersion>

}


