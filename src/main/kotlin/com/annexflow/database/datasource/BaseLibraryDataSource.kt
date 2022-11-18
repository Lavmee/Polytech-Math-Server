package com.annexflow.database.datasource

import com.annexflow.database.entity.Libraries
import com.annexflow.database.entity.Library
import org.jetbrains.exposed.sql.*


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseLibraryDataSource : LibraryDataSource {

    private fun resultRowToNode(row: ResultRow) = Library(
        id = row[Libraries.id], libraryPath = row[Libraries.libraryPath], className = row[Libraries.className]
    )

    override suspend fun insertLibrary(libraryPath: String, className: String): Library? {
        val insertStatement = Libraries.insert {
            it[Libraries.libraryPath] = libraryPath
            it[Libraries.className] = className
        }

        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNode)
    }

    override suspend fun retrieveLibrary(libraryId: Int): Library {
        val library = Libraries.select { (Libraries.id eq libraryId) }.map { resultRowToNode(it) }
        return library.first()
    }

    override suspend fun retrieveLastLibrary(): Library {
        val library = Libraries
            .selectAll()
            .limit(1)
            .orderBy(Libraries.id to SortOrder.DESC)
            .map { resultRowToNode(it) }
        return library.first()
    }

}