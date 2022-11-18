package com.annexflow.database

import com.annexflow.database.entity.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class Database(config: DatabaseConfig) {

    private val database: Database
    init {
        val driverClassName = config.driverClassName
        val jdbcURL = config.jdbcURL
        val username = config.username
        val password = config.password
        val defaultDatabase = config.database
        database = Database.connect(
            url = "$jdbcURL/$defaultDatabase",
            driver = driverClassName,
            user = username,
            password = password
        )
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(Libraries)
        }
    }

    suspend fun <T> databaseTransaction(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}