package com.annexflow.dispersion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Lavmee on 02.09.2022
 **/

@Serializable
sealed class DispersionResult {
    @Serializable
    @SerialName("average_observations_number_a_gradation_factor")
    data class AverageObservationsNumberAGradationFactor(
        val items: List<Double>
    ) : DispersionResult()

    @Serializable
    @SerialName("average_observations_number_b_gradation_factor")
    data class AverageObservationsNumberBGradationFactor(
        val items: List<Double>
    ) : DispersionResult()

    @Serializable
    @SerialName("total_average_observations_number")
    data class TotalAverageObservationsNumber(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("squared_sum_deviations_explained_by_factor_a")
    data class SquaredSumDeviationsExplainedByFactorA(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("squared_sum_deviations_explained_by_factor_b")
    data class SquaredSumDeviationsExplainedByFactorB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("squared_sum_deviations_explained_by_factor_ab_interaction")
    data class SquaredSumDeviationsExplainedByFactorABInteraction(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("squared_sum_deviations_unexplained_error")
    data class SquaredSumDeviationsUnexplainedError(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("total_squared_sum_deviations")
    data class TotalSquaredSumDeviations(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("degrees_freedom_number_explained_by_factor_a")
    data class DegreesFreedomNumberExplainedByFactorA(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("degrees_freedom_number_explained_by_factor_b")
    data class DegreesFreedomNumberExplainedByFactorB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("degrees_freedom_number_explained_by_factor_ab_interaction")
    data class DegreesFreedomNumberExplainedByFactorABInteraction(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("degrees_freedom_number_unexplained_variance_error")
    data class DegreesFreedomNumberUnexplainedVarianceError(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("total_degrees_freedom_number")
    data class TotalDegreesFreedomNumber(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("dispersion_explained_by_factor_a")
    data class DispersionResultExplainedByFactorA(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("dispersion_explained_by_factor_b")
    data class DispersionResultExplainedByFactorB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("dispersion_explained_by_factor_ab_interaction")
    data class DispersionResultExplainedByFactorABInteraction(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("dispersion_unexplained_error")
    data class DispersionResultUnexplainedError(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("actual_fisher_ratio_factor_a")
    data class ActualFisherRatioFactorA(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("actual_fisher_ratio_factor_b")
    data class ActualFisherRatioFactorB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("actual_fisher_ratio_factor_ab")
    data class ActualFisherRatioFactorAB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("critical_fisher_ratio_value_factor_a")
    data class CriticalFisherRatioValueFactorA(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("critical_fisher_ratio_value_factor_b")
    data class CriticalFisherRatioValueFactorB(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("critical_fisher_ratio_value_factor_ab_interaction")
    data class CriticalFisherRatioValueFactorABInteraction(
        val value: Double
    ) : DispersionResult()

    @Serializable
    @SerialName("is_data_depend_factor_a")
    data class IsDataDependFactorA(
        val value: Boolean
    ) : DispersionResult()

    @Serializable
    @SerialName("is_data_depend_factor_b")
    data class IsDataDependFactorB(
        val value: Boolean
    ) : DispersionResult()

    @Serializable
    @SerialName("is_data_depend_factor_ab_interaction")
    data class IsDataDependFactorABInteraction(
        val value: Boolean
    ) : DispersionResult()

}