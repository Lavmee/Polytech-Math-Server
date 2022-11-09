package com.annexflow.system

import com.annexflow.features.dispersion.exchanges.Dispersion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow

class BaseDispersionAnalyzerFlow : DispersionAnalyzerFlow {
    override suspend fun calculateDispersion(
        value: List<List<List<Double>>>,
        factorAGradationsNumber: Int, factorBGradationsNumber: Int, repeat: Int
    ): Flow<Dispersion> {
        return flow {

            val medXjs = arrayListOf<Double>()
            for (i in value.indices) {
                val medXj = value[i].flatten().average()
                medXjs.add(medXj)
            }
            emit(Dispersion.AverageObservationsNumberAGradationFactor(medXjs))

            val medXis = arrayListOf<Double>()
            for (j in 0 until factorBGradationsNumber) {
                val medXi = arrayListOf<Double>()
                for (i in value.indices) {
                    value[i][j].forEach { medXi.add(it) }
                }
                medXis.add(medXi.average())
            }
            emit(Dispersion.AverageObservationsNumberBGradationFactor(medXis))

            val totalMedX = (medXjs + medXis).average()

            emit(Dispersion.TotalAverageObservationsNumber(totalMedX))

            val squaredDeviationAMedium = medXis.map { (it - totalMedX).pow(2.0) }
            val squaredDeviationBMedium = medXjs.map { (it - totalMedX).pow(2.0) }

            val SSa = squaredDeviationAMedium.sum() * factorAGradationsNumber * repeat
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorA(SSa))

            val SSb = squaredDeviationBMedium.sum() * factorBGradationsNumber * repeat
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorB(SSb))

            val SSab = calculateSSab(medXis, medXjs, value, totalMedX, repeat)
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorABInteraction(SSab))

            val SSe = calculateSSe(value)
            emit(Dispersion.SquaredSumDeviationsUnexplainedError(SSe))

            val SS = SSa + SSb + SSab + SSe
            emit(Dispersion.TotalSquaredSumDeviations(SS))


            val va = (factorBGradationsNumber - 1).toDouble()
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorA(va))

            val vb = (factorAGradationsNumber - 1).toDouble()
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorB(vb))

            val vab = (va * vb)
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorABInteraction(vab))

            val ve = (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1)).toDouble()
            emit(Dispersion.DegreesFreedomNumberUnexplainedVarianceError(ve))

            val v = (factorBGradationsNumber * factorAGradationsNumber * repeat - 1).toDouble()
            emit(Dispersion.TotalDegreesFreedomNumber(v))

            val MSa = SSa / (factorBGradationsNumber - 1)
            emit(Dispersion.DispersionExplainedByFactorA(MSa))

            val MSb = SSb / (factorAGradationsNumber - 1)
            emit(Dispersion.DispersionExplainedByFactorB(MSb))

            val MSab = SSab / ((factorBGradationsNumber - 1) * (factorAGradationsNumber - 1))
            emit(Dispersion.DispersionExplainedByFactorABInteraction(MSab))

            val MSe = SSe / (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1))
            emit(Dispersion.DispersionUnexplainedError(MSe))

            val Fa = MSa / MSe
            emit(Dispersion.ActualFisherRatioFactorA(Fa))

            val Fb = MSb / MSe
            emit(Dispersion.ActualFisherRatioFactorB(Fb))

            val Fab = MSab / MSe
            emit(Dispersion.ActualFisherRatioFactorAB(Fab))

            val Fda = FDistribution(va, ve)
            val Fdb = FDistribution(vb, ve)
            val Fdab = FDistribution(vab, ve)

            val Fka = Fda.inverseCumulativeProbability(0.95)
            emit(Dispersion.CriticalFisherRatioValueFactorA(Fka))

            val Fkb = Fdb.inverseCumulativeProbability(0.95)
            emit(Dispersion.CriticalFisherRatioValueFactorB(Fkb))

            val Fkab = Fdab.inverseCumulativeProbability(0.95)
            emit(Dispersion.CriticalFisherRatioValueFactorABInteraction(Fkab))

            val aResult = Fa > Fka
            emit(Dispersion.IsDataDependFactorA(aResult))

            val bResult = Fb > Fkb
            emit(Dispersion.IsDataDependFactorB(bResult))

            val abResult = Fab > Fkab
            emit(Dispersion.IsDataDependFactorABInteraction(abResult))
        }
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