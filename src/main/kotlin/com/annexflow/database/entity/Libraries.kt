package com.annexflow.database.entity

import org.jetbrains.exposed.sql.Table


/**
 * @author Lavmee on 18.11.2022
 **/

object Libraries : Table() {
    val id = integer("id").autoIncrement()
    val libraryPath = varchar("library_path", 1024)
    val className = varchar("class_name", 1024)

    override val primaryKey = PrimaryKey(id)
}

data class Library(
    val id: Int,
    val libraryPath: String,
    val className: String
)