package com.annexflow.filesystemplugin

import java.io.File

class FileSystemApplication {


    fun paths(vararg paths: String): FileSystemApplication {
        return paths(paths.toList())
    }

    fun paths(paths: List<String>): FileSystemApplication {
        paths.forEach { path ->
            val pathFile = File(path)
            if (!pathFile.exists()){
                pathFile.mkdirs()
            }
        }
        return this
    }

    companion object {

        fun init(): FileSystemApplication {
            return FileSystemApplication()
        }
    }

}