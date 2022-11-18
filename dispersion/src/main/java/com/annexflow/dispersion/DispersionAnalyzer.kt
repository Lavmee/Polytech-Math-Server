package com.annexflow.dispersion

import org.apache.commons.math3.distribution.FDistribution
import kotlin.math.pow

class DispersionAnalyzer(
    private val value: List<List<List<Double>>>,
    private val factorAGradationsNumber: Int,
    private val factorBGradationsNumber: Int,
    private val repeat: Int
) {

    private var medXjs: ArrayList<Double> = arrayListOf()
    private var medXis: ArrayList<Double> = arrayListOf()
    private var totalMedX: Double = 0.0
    private var SSa: Double = 0.0
    private var SSb: Double = 0.0
    private var SSab: Double = 0.0
    private var SSe: Double = 0.0
    private var SS: Double = 0.0
    private var va: Double = 0.0
    private var vb: Double = 0.0
    private var vab: Double = 0.0
    private var ve: Double = 0.0
    private var v: Double = 0.0
    private var MSa: Double = 0.0
    private var MSb: Double = 0.0
    private var MSab: Double = 0.0
    private var MSe: Double = 0.0
    private var Fa: Double = 0.0
    private var Fb: Double = 0.0
    private var Fab: Double = 0.0
    private var Fka: Double = 0.0
    private var Fkb: Double = 0.0
    private var Fkab: Double = 0.0
    private var aResult: Boolean = false
    private var bResult: Boolean = false
    private var abResult: Boolean = false

    fun calculateMedXjs(): ArrayList<Double> {
        val medXjs = arrayListOf<Double>()
        for (i in value.indices) {
            val medXj = value[i].flatten().average()
            medXjs.add(medXj)
        }
        this.medXjs = medXjs
        return medXjs
    }

    fun calculateMedXis(): ArrayList<Double> {
        val medXis = arrayListOf<Double>()
        for (j in 0 until factorBGradationsNumber) {
            val medXi = arrayListOf<Double>()
            for (i in value.indices) {
                value[i][j].forEach { medXi.add(it) }
            }
            medXis.add(medXi.average())
        }
        this.medXis = medXis
        return medXis
    }

    fun calculateTotalMedX(): Double {
        val totalMedX = (medXjs + medXis).average()
        this.totalMedX = totalMedX
        return totalMedX
    }

    fun calculateSSa(): Double {
        val squaredDeviationAMedium = medXis.map { (it - totalMedX).pow(2.0) }
        val SSa = squaredDeviationAMedium.sum() * factorAGradationsNumber * repeat
        this.SSa = SSa
        return SSa
    }

    fun calculateSSb(): Double {
        val squaredDeviationBMedium = medXjs.map { (it - totalMedX).pow(2.0) }
        val SSb = squaredDeviationBMedium.sum() * factorBGradationsNumber * repeat
        this.SSb = SSb
        return SSb
    }

    fun calculateSSab(): Double {
        val SSab = calculateSSab(medXis, medXjs, value, totalMedX, repeat)
        this.SSab = SSab
        return SSab
    }

    fun calculateSSe(): Double {
        val SSe = calculateSSe(value)
        this.SSe = SSe
        return SSe
    }

    fun calculateSS(): Double {
        val SS = SSa + SSb + SSab + SSe
        this.SS = SS
        return SS
    }

    fun calculateVA(): Double {
        val va = (factorBGradationsNumber - 1).toDouble()
        this.va = va
        return va
    }

    fun calculateVB(): Double {
        val vb = (factorAGradationsNumber - 1).toDouble()
        this.vb = vb
        return vb
    }

    fun calculateVAB(): Double {
        val vab = (va * vb)
        this.vab = vab
        return vab
    }

    fun calculateVE(): Double {
        val ve = (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1)).toDouble()
        this.ve = ve
        return ve
    }

    fun calculateV(): Double {
        val v = (factorBGradationsNumber * factorAGradationsNumber * repeat - 1).toDouble()
        this.v = v
        return v
    }

    fun calculateMSa(): Double {
        val MSa = SSa / (factorBGradationsNumber - 1)
        this.MSa = MSa
        return MSa
    }

    fun calculateMSb(): Double {
        val MSb = SSb / (factorAGradationsNumber - 1)
        this.MSb = MSb
        return MSb
    }

    fun calculateMSab(): Double {
        val MSab = SSab / ((factorBGradationsNumber - 1) * (factorAGradationsNumber - 1))
        this.MSab = MSab
        return MSab
    }

    fun calculateMSe(): Double {
        val MSe = SSe / (factorBGradationsNumber * factorAGradationsNumber * (repeat - 1))
        this.MSe = MSe
        return MSe
    }

    fun calculateFa(): Double {
        val Fa = MSa / MSe
        this.Fa = Fa
        return Fa
    }

    fun calculateFb(): Double {
        val Fb = MSb / MSe
        this.Fb = Fb
        return Fb
    }

    fun calculateFab(): Double {
        val Fab = MSab / MSe
        this.Fab = Fab
        return Fab
    }

    fun calculateFka(): Double {
        val Fda = FDistribution(va, ve)
        val Fka = Fda.inverseCumulativeProbability(0.95)
        this.Fka = Fka
        return Fka
    }

    fun calculateFkb(): Double {
        val Fdb = FDistribution(vb, ve)
        val Fkb = Fdb.inverseCumulativeProbability(0.95)
        this.Fkb = Fkb
        return Fkb
    }

    fun calculateFkab(): Double {
        val Fdab = FDistribution(vab, ve)
        val Fkab = Fdab.inverseCumulativeProbability(0.95)
        this.Fkab = Fkab
        return Fkab
    }

    fun calculateAResult(): Boolean {
        val aResult = Fa > Fka
        this.aResult = aResult
        return aResult
    }

    fun calculateBResult(): Boolean {
        val bResult = Fb > Fkb
        this.bResult = bResult
        return bResult
    }

    fun calculateABResult(): Boolean {
        val abResult = Fab > Fkab
        this.abResult = abResult
        return abResult
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