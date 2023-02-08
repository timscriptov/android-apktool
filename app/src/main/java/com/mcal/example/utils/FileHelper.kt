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
    fun copyAssetsFile(context: Context, filename: String, output: File) {
        copyFile(context.assets.open(filename), FileOutputStream(output))
    }

    @Throws(IOException::class)
    fun copyFile(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } != -1) {
            target.write(buf, 0, length)
        }
    }
}