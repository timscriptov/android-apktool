package com.mcal.androlib.utils

import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

object FileHelper {
    @JvmStatic
    fun readInputStream(inputStream: InputStream) =
        inputStream.readBytes().toString(StandardCharsets.UTF_8)

    @JvmStatic
    @Throws(IOException::class)
    fun createNewFile(parent: File, name: String): File {
        val createdFile = File(parent, name)
        parent.mkdirs()
        createdFile.createNewFile()
        return createdFile
    }
}