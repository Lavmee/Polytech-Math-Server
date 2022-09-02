package com.annexflow.system

import com.annexflow.features.dispersion.exchanges.DispersionResult
import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow

/**
 * @author Lavmee on 02.09.2022
 **/

interface DispersionAnalyzer {
    suspend fun calculateDispersion(value: List<List<List<Double>>>, factorAGradationsNumber: Int, factorBGradationsNumber: Int, repeat: Int): DispersionResult

    class Base : DispersionAnalyzer {
        override suspend fun calculateDispersion(
            value: List<List<List<Double>>>,
            factorAGradationsNumber: Int, factorBGradationsNumber: Int, repeat: Int
        ): DispersionResult {
            val medXjs = arrayListOf<Double>()
            for (i in value.indices) {
                val medXj = value[i].flatten().average()
                medXjs.add(medXj)
            }

            val medXis = arrayListOf<Double>()
            for (j in 0 until factorBGradationsNumber) {
                val medXi = arrayListOf<Double>()
                for (i in value.indices) {
                    value[i][j].forEach { medXi.add(it) }
                }
                medXis.add(medXi.average())
            }

            val totalMedX = (medXjs + medXis).average()

            val squaredDeviationAMedium = medXis.map { (it - totalMedX).pow(2.0) }
            val squaredDeviationBMedium = medXjs.map { (it - totalMedX).pow(2.0) }

            val SSa = squaredDeviationAMedium.sum() * factorAGradationsNumber * repeat
            val SSb = squaredDeviationBMedium.sum() * factorBGradationsNumber * repeat

            val SSab = calculateSSab(medXis, medXjs, value, totalMedX, repeat)

            val SSe = calculateSSe(value)

            val SS = SSa + SSb + SSab + SSe

            val va = (factorBGradationsNumber - 1).toDouble()
            val vb = (factorAGradationsNumber - 1).toDouble()
            val vab = (va * vb)
            val ve = (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1)).toDouble()
            val v = (factorBGradationsNumber * factorAGradationsNumber * repeat - 1).toDouble()

            val MSa = SSa / (factorBGradationsNumber - 1)
            val MSb = SSb / (factorAGradationsNumber - 1)
            val MSab = SSab / ((factorBGradationsNumber - 1) * (factorAGradationsNumber - 1))
            val MSe = SSe / (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1))

            val Fa = MSa / MSe
            val Fb = MSb / MSe
            val Fab = MSab / MSe

            val Fda = FDistribution(va, ve)
            val Fdb = FDistribution(vb, ve)
            val Fdab = FDistribution(vab, ve)

            val Fka = Fda.inverseCumulativeProbability(0.95)
            val Fkb = Fdb.inverseCumulativeProbability(0.95)
            val Fkab = Fdab.inverseCumulativeProbability(0.95)

            val aResult = Fa > Fka
            val bResult = Fb > Fkb
            val abResult = Fab > Fkab

            return DispersionResult(
                averageObservationsNumberAGradationFactor = medXjs,
                averageObservationsNumberBGradationFactor = medXis,
                totalAverageObservationsNumber = totalMedX,
                squaredSumDeviationsExplainedByFactorA = SSa,
                squaredSumDeviationsExplainedByFactorB = SSb,
                squaredSumDeviationsExplainedByFactorABInteraction = SSab,
                squaredSumDeviationsUnexplainedError = SSe,
                totalSquaredSumDeviations = SS,
                degreesFreedomNumberExplainedByFactorA = va,
                degreesFreedomNumberExplainedByFactorB = vb,
                degreesFreedomNumberExplainedByFactorABInteraction = vab,
                degreesFreedomNumberUnexplainedVarianceError = ve,
                totalDegreesFreedomNumber = v,
                dispersionExplainedByFactorA = MSa,
                dispersionExplainedByFactorB = MSb,
                dispersionExplainedByFactorABInteraction = MSab,
                dispersionUnexplainedError = MSe,
                actualFisherRatioFactorA = Fa,
                actualFisherRatioFactorB = Fb,
                actualFisherRatioFactorAB = Fab,
                criticalFisherRatioValueFactorA = Fka,
                criticalFisherRatioValueFactorB = Fkb,
                criticalFisherRatioValueFactorABInteraction = Fkab,
                isDataDependFactorA = aResult,
                isDataDependFactorB = bResult,
                isDataDependFactorABInteraction = abResult
            )
        }

        private fun calculateSSe(value: List<List<List<Double>>>): Double {
            val factorLevels = arrayListOf<ArrayList<Double>>()
            for (i in value.indices) {
                factorLevels.add(arrayListOf())
                value[i].forEach {
                    factorLevels[i].add(it.average())
                }
            }
            val otherCalc = arrayListOf<Double>()
            for (i in value.indices) {
                value[i].mapIndexed { index, d ->
                    d.mapIndexed { _, dd ->
                        otherCalc.add((dd - factorLevels[i][index]).pow(2.0))
                    }
                }
            }
            return otherCalc.sum()
        }

        private fun calculateSSab(
            medXis: ArrayList<Double>, medXjs: ArrayList<Double>,
            value: List<List<List<Double>>>, totalMedX: Double, r: Int
        ): Double {

            val factorLevels = arrayListOf<ArrayList<Double>>()
            for (i in value.indices) {
                factorLevels.add(arrayListOf())
                value[i].forEach {
                    factorLevels[i].add(it.average())
                }
            }
            val otherCalc = arrayListOf<Double>()
            for (i in factorLevels.indices) {
                otherCalc.addAll(factorLevels[i].mapIndexed { index, d ->
                    (d - medXis[index] - medXjs[i] + totalMedX).pow(2.0)
                })
            }
            return otherCalc.sum() * r
        }
    }

}