package com.annexflow.system

import com.annexflow.features.dispersion.exchanges.Dispersion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow


val isDelayed = true
val delayTime
    get() = (500L..1200L).random()
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
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.AverageObservationsNumberAGradationFactor(medXjs))

            val medXis = arrayListOf<Double>()
            for (j in 0 until factorBGradationsNumber) {
                val medXi = arrayListOf<Double>()
                for (i in value.indices) {
                    value[i][j].forEach { medXi.add(it) }
                }
                medXis.add(medXi.average())
            }
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.AverageObservationsNumberBGradationFactor(medXis))

            val totalMedX = (medXjs + medXis).average()

            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.TotalAverageObservationsNumber(totalMedX))

            val squaredDeviationAMedium = medXis.map { (it - totalMedX).pow(2.0) }
            val squaredDeviationBMedium = medXjs.map { (it - totalMedX).pow(2.0) }

            val SSa = squaredDeviationAMedium.sum() * factorAGradationsNumber * repeat
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorA(SSa))

            val SSb = squaredDeviationBMedium.sum() * factorBGradationsNumber * repeat
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorB(SSb))

            val SSab = calculateSSab(medXis, medXjs, value, totalMedX, repeat)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.SquaredSumDeviationsExplainedByFactorABInteraction(SSab))

            val SSe = calculateSSe(value)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.SquaredSumDeviationsUnexplainedError(SSe))

            val SS = SSa + SSb + SSab + SSe
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.TotalSquaredSumDeviations(SS))


            val va = (factorBGradationsNumber - 1).toDouble()
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorA(va))

            val vb = (factorAGradationsNumber - 1).toDouble()
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorB(vb))

            val vab = (va * vb)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DegreesFreedomNumberExplainedByFactorABInteraction(vab))

            val ve = (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1)).toDouble()
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DegreesFreedomNumberUnexplainedVarianceError(ve))

            val v = (factorBGradationsNumber * factorAGradationsNumber * repeat - 1).toDouble()
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.TotalDegreesFreedomNumber(v))

            val MSa = SSa / (factorBGradationsNumber - 1)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DispersionExplainedByFactorA(MSa))

            val MSb = SSb / (factorAGradationsNumber - 1)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DispersionExplainedByFactorB(MSb))

            val MSab = SSab / ((factorBGradationsNumber - 1) * (factorAGradationsNumber - 1))
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DispersionExplainedByFactorABInteraction(MSab))

            val MSe = SSe / (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1))
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.DispersionUnexplainedError(MSe))

            val Fa = MSa / MSe
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.ActualFisherRatioFactorA(Fa))

            val Fb = MSb / MSe
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.ActualFisherRatioFactorB(Fb))

            val Fab = MSab / MSe
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.ActualFisherRatioFactorAB(Fab))

            val Fda = FDistribution(va, ve)
            val Fdb = FDistribution(vb, ve)
            val Fdab = FDistribution(vab, ve)

            val Fka = Fda.inverseCumulativeProbability(0.95)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.CriticalFisherRatioValueFactorA(Fka))

            val Fkb = Fdb.inverseCumulativeProbability(0.95)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.CriticalFisherRatioValueFactorB(Fkb))

            val Fkab = Fdab.inverseCumulativeProbability(0.95)
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.CriticalFisherRatioValueFactorABInteraction(Fkab))

            val aResult = Fa > Fka
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.IsDataDependFactorA(aResult))

            val bResult = Fb > Fkb
            if(isDelayed){ delay(delayTime) }
            emit(Dispersion.IsDataDependFactorB(bResult))

            val abResult = Fab > Fkab
            if(isDelayed){ delay(delayTime) }
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