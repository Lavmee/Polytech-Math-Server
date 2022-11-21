package com.annexflow.database.datasource

import com.annexflow.database.entity.Libraries
import com.annexflow.database.entity.Library
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


/**
 * @author Lavmee on 18.11.2022
 **/

class BaseLibraryDataSource : LibraryDataSource {

    private fun resultRowToNode(row: ResultRow) = Library(
        id = row[Libraries.id], libraryPath = row[Libraries.libraryPath]
    )

    override suspend fun insertLibrary(libraryPath: String): Library? {
        val insertStatement = Libraries.insert {
            it[Libraries.libraryPath] = libraryPath
        }

        return insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToNode)
    }

    override suspend fun retrieveLibrary(libraryId: Int): Library? {
        val library = Libraries.select { (Libraries.id eq libraryId) }.map { resultRowToNode(it) }
        return library.singleOrNull()
    }

    override suspend fun retrieveLastLibrary(): Library? {
        val library = Libraries
            .selectAll()
            .limit(1)
            .orderBy(Libraries.id to SortOrder.DESC)
            .map { resultRowToNode(it) }
        return library.singleOrNull()
    }

    override suspend fun deleteLibrary(libraryId: Int): Boolean {
        Libraries.deleteWhere { Libraries.id eq libraryId }
        return true
    }

    override suspend fun changePath(libraryId: Int, libraryPath: String) {
        Libraries.update({ Libraries.id eq libraryId }) {
            it[Libraries.libraryPath] = libraryPath
        }
    }

}