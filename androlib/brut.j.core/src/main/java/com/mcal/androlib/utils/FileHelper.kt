package com.mcal.androlib.utils

import java.io.*

object FileHelper {
    @JvmStatic
    fun getExtension(file: String): String {
        return File(file).extension
    }

    @JvmStatic
    fun getExtension(file: File): String {
        return file.extension
    }

    @JvmStatic
    fun moveFile(sourceFile: File, destFile: File) {
        if (!destFile.exists()) {
            sourceFile.renameTo(destFile)
        } else {
            throw FileAlreadyExistsException(
                file = destFile,
                other = null,
                reason = "Destination file already exists."
            )
        }
    }

    @JvmStatic
    @Throws(IOException::class)
    fun createNewFile(parent: File, name: String): File {
        val createdFile = File(parent, name)
        parent.mkdirs()
        createdFile.createNewFile()
        return createdFile
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyFile(srcFilePath: String, dstFilePath: String) {
        copyFile(File(srcFilePath), File(dstFilePath))
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyFile(filename: File, output: File) {
        copyFile(FileInputStream(filename), FileOutputStream(output))
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyFile(input: InputStream, output: File) {
        copyFile(input, FileOutputStream(output))
    }

    @JvmStatic
    @Throws(IOException::class)
    fun copyFile(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } != -1) {
            target.write(buf, 0, length)
        }
    }

    @JvmStatic
    fun toByteArray(input: InputStream): ByteArray {
        return input.readBytes()
    }
}