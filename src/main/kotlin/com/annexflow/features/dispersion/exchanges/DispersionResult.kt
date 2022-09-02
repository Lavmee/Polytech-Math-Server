package com.annexflow.features.dispersion.exchanges

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Lavmee on 02.09.2022
 **/

@Serializable
data class DispersionResult(
    @SerialName("average_observations_number_a_gradation_factor")
    val averageObservationsNumberAGradationFactor: ArrayList<Double>,
    @SerialName("average_observations_number_b_gradation_factor")
    val averageObservationsNumberBGradationFactor: ArrayList<Double>,
    @SerialName("total_average_observations_number")
    val totalAverageObservationsNumber: Double,

    @SerialName("squared_sum_deviations_explained_by_factor_a")
    val squaredSumDeviationsExplainedByFactorA: Double,
    @SerialName("squared_sum_deviations_explained_by_factor_b")
    val squaredSumDeviationsExplainedByFactorB: Double,
    @SerialName("squared_sum_deviations_explained_by_factor_ab_interaction")
    val squaredSumDeviationsExplainedByFactorABInteraction: Double,
    @SerialName("squared_sum_deviations_unexplained_error")
    val squaredSumDeviationsUnexplainedError: Double,
    @SerialName("total_squared_sum_deviations")
    val totalSquaredSumDeviations: Double,

    @SerialName("degrees_freedom_number_explained_by_factor_a")
    val degreesFreedomNumberExplainedByFactorA: Double,
    @SerialName("degrees_freedom_number_explained_by_factor_b")
    val degreesFreedomNumberExplainedByFactorB: Double,
    @SerialName("degrees_freedom_number_explained_by_factor_ab_interaction")
    val degreesFreedomNumberExplainedByFactorABInteraction: Double,
    @SerialName("degrees_freedom_number_unexplained_variance_error")
    val degreesFreedomNumberUnexplainedVarianceError: Double,
    @SerialName("total_degrees_freedom_number")
    val totalDegreesFreedomNumber: Double,

    @SerialName("dispersion_explained_by_factor_a")
    val dispersionExplainedByFactorA: Double,
    @SerialName("dispersion_explained_by_factor_b")
    val dispersionExplainedByFactorB: Double,
    @SerialName("dispersion_explained_by_factor_ab_interaction")
    val dispersionExplainedByFactorABInteraction: Double,
    @SerialName("dispersion_unexplained_error")
    val dispersionUnexplainedError: Double,

    @SerialName("actual_fisher_ratio_factor_a")
    val actualFisherRatioFactorA: Double,
    @SerialName("actual_fisher_ratio_factor_b")
    val actualFisherRatioFactorB: Double,
    @SerialName("actual_fisher_ratio_factor_ab")
    val actualFisherRatioFactorAB: Double,

    @SerialName("critical_fisher_ratio_value_factor_a")
    val criticalFisherRatioValueFactorA: Double,
    @SerialName("critical_fisher_ratio_value_factor_b")
    val criticalFisherRatioValueFactorB: Double,
    @SerialName("critical_fisher_ratio_value_factor_ab_interaction")
    val criticalFisherRatioValueFactorABInteraction: Double,

    @SerialName("is_data_depend_factor_a")
    val isDataDependFactorA: Boolean,
    @SerialName("is_data_depend_factor_b")
    val isDataDependFactorB: Boolean,
    @SerialName("is_data_depend_factor_ab_interaction")
    val isDataDependFactorABInteraction: Boolean,
)