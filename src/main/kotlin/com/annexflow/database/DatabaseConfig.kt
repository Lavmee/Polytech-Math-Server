package com.annexflow.database

data class DatabaseConfig(
    val driverClassName: String,
    val jdbcURL: String,
    val username: String,
    val password: String,
    val database: String
)