package com.annexflow.features.dispersion.mapper


/**
 * @author Lavmee on 02.09.2022
 **/

interface DispersionMapper {
    fun map(value: List<Double>, a: Int, b: Int, r: Int): List<List<List<Double>>>

    class Base : DispersionMapper{
        override fun map(value: List<Double>, a: Int, b: Int, r: Int): List<List<List<Double>>> {
            return value.chunked(a).chunked(b)
        }
    }
}