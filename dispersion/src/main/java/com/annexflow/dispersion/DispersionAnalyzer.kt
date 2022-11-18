package com.annexflow.dispersion

class DispersionAnalyzer {
    private var value: List<List<List<Double>>> = emptyList()
    private var factorAGradationsNumber: Int = 0
    private var factorBGradationsNumber: Int = 0
    private var repeat: Int = 0

    private var medXjs: List<Double> = listOf()
    private var medXis: List<Double> = listOf()
    private var totalMedX: Double = 0.0

    fun setDefaultValues(
        value: List<List<List<Double>>>,
        factorAGradationsNumber: Int,
        factorBGradationsNumber: Int,
        repeat: Int
    ) {
        this.value = value
        this.factorAGradationsNumber = factorAGradationsNumber
        this.factorBGradationsNumber = factorBGradationsNumber
        this.repeat = repeat
    }

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


}