package com.mcal.example.utils

import android.content.Context
import java.io.*

object FileHelper {
   fun getToolsDir(context: Context) : File {
        val dir = File(context.filesDir, "bin")
        if(!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    fun getDecodeDir(context: Context) : File {
        val dir = File(context.filesDir, "decode")
        if(!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    @Throws(IOException::class)
    fun copyAssetsFile(context: Context, filename: String, output: File, execute: Boolean) {
        val input: InputStream = context.assets.open(filename)
        try {
            FileOutputStream(output).use { target ->
                copyFile(input, target, input.available())
            }
            output.setExecutable(execute)
        } finally {
            input.close()
        }
    }

    @Throws(IOException::class)
    fun copyFile(source: InputStream, target: FileOutputStream, size: Int) {
        val buf = ByteArray(size)
        var length: Int
        while (source.read(buf).also { length = it } != -1) {
            target.write(buf, 0, length)
        }
    }
}