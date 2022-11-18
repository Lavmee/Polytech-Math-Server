package com.annexflow.dispersion

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DispersionAnalyzerAdapter(json: String) {

    private val analyzer: DispersionAnalyzer

    init {
        val data = Json.decodeFromString<DispersionRequest>(json)
        analyzer = DispersionAnalyzer(
            value = data.values.chunked(data.factorAGradationsNumber).chunked(data.factorBGradationsNumber),
            factorAGradationsNumber = data.factorAGradationsNumber,
            factorBGradationsNumber = data.factorBGradationsNumber,
            repeat = data.repeatNumber
        )
    }

    fun calculateMedXjs(): String {
        val result = DispersionResult.AverageObservationsNumberAGradationFactor(analyzer.calculateMedXjs())
        return Json.encodeToString(result)
    }

    fun calculateMedXis(): String {
        val result = DispersionResult.AverageObservationsNumberBGradationFactor(analyzer.calculateMedXis())
        return Json.encodeToString(result)
    }

    fun calculateTotalMedX(): String {
        val result = DispersionResult.TotalAverageObservationsNumber(analyzer.calculateTotalMedX())
        return Json.encodeToString(result)
    }

    fun calculateSSa(): String {
        val result = DispersionResult.SquaredSumDeviationsExplainedByFactorA(analyzer.calculateSSa())
        return Json.encodeToString(result)
    }

    fun calculateSSb(): String {
        val result = DispersionResult.SquaredSumDeviationsExplainedByFactorB(analyzer.calculateSSb())
        return Json.encodeToString(result)
    }

    fun calculateSSab(): String {
        val result = DispersionResult.SquaredSumDeviationsExplainedByFactorABInteraction(analyzer.calculateSSab())
        return Json.encodeToString(result)
    }

    fun calculateSSe(): String {
        val result = DispersionResult.SquaredSumDeviationsUnexplainedError(analyzer.calculateSSe())
        return Json.encodeToString(result)
    }

    fun calculateSS(): String {
        val result = DispersionResult.TotalSquaredSumDeviations(analyzer.calculateSS())
        return Json.encodeToString(result)
    }

    fun calculateVA(): String {
        val result = DispersionResult.DegreesFreedomNumberExplainedByFactorA(analyzer.calculateVA())
        return Json.encodeToString(result)
    }

    fun calculateVB(): String {
        val result = DispersionResult.DegreesFreedomNumberExplainedByFactorB(analyzer.calculateVB())
        return Json.encodeToString(result)
    }

    fun calculateVAB(): String {
        val result = DispersionResult.DegreesFreedomNumberExplainedByFactorABInteraction(analyzer.calculateVAB())
        return Json.encodeToString(result)
    }

    fun calculateVE(): String {
        val result = DispersionResult.DegreesFreedomNumberUnexplainedVarianceError(analyzer.calculateVE())
        return Json.encodeToString(result)
    }

    fun calculateV(): String {
        val result = DispersionResult.TotalDegreesFreedomNumber(analyzer.calculateV())
        return Json.encodeToString(result)
    }

    fun calculateMSa(): String {
        val result = DispersionResult.DispersionResultExplainedByFactorA(analyzer.calculateMSa())
        return Json.encodeToString(result)
    }

    fun calculateMSb(): String {
        val result = DispersionResult.DispersionResultExplainedByFactorB(analyzer.calculateMSb())
        return Json.encodeToString(result)
    }

    fun calculateMSab(): String {
        val result = DispersionResult.DispersionResultExplainedByFactorABInteraction(analyzer.calculateMSab())
        return Json.encodeToString(result)
    }

    fun calculateMSe(): String {
        val result = DispersionResult.DispersionResultUnexplainedError(analyzer.calculateMSe())
        return Json.encodeToString(result)
    }

    fun calculateFa(): String {
        val result = DispersionResult.ActualFisherRatioFactorA(analyzer.calculateFa())
        return Json.encodeToString(result)
    }

    fun calculateFb(): String {
        val result = DispersionResult.ActualFisherRatioFactorB(analyzer.calculateFb())
        return Json.encodeToString(result)
    }

    fun calculateFab(): String {
        val result = DispersionResult.ActualFisherRatioFactorAB(analyzer.calculateFab())
        return Json.encodeToString(result)
    }

    fun calculateFka(): String {
        val result = DispersionResult.CriticalFisherRatioValueFactorA(analyzer.calculateFka())
        return Json.encodeToString(result)
    }

    fun calculateFkb(): String {
        val result = DispersionResult.CriticalFisherRatioValueFactorB(analyzer.calculateFkb())
        return Json.encodeToString(result)
    }

    fun calculateFkab(): String {
        val result = DispersionResult.CriticalFisherRatioValueFactorABInteraction(analyzer.calculateFkab())
        return Json.encodeToString(result)
    }

    fun calculateAResult(): String {
        val result = DispersionResult.IsDataDependFactorA(analyzer.calculateAResult())
        return Json.encodeToString(result)
    }

    fun calculateBResult(): String {
        val result = DispersionResult.IsDataDependFactorB(analyzer.calculateBResult())
        return Json.encodeToString(result)
    }

    fun calculateABResult(): String {
        val result = DispersionResult.IsDataDependFactorABInteraction(analyzer.calculateABResult())
        return Json.encodeToString(result)
    }


}