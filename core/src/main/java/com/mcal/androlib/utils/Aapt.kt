package com.mcal.androlib.utils

import com.mcal.androlib.options.BuildOptions
import com.mcal.androlib.utils.FileHelper.readInputStream
import com.mcal.common.tasks.LogHelper
import java.io.File

object Aapt {
    @JvmStatic
    fun build(
        apkFile: File,
        include: Array<File?>?,
        manifest: File,
        resDir: File,
        minSdk: String?,
        targetSdk: String?,
        options: BuildOptions
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(options.aaptPath)
        args.add("package")
        args.add("-f")
        include?.forEach { framework ->
            framework?.let {
                args.add("-I")
                args.add(it.path)
            }
        }

        args.add("--min-sdk-version")
        args.add(minSdk ?: "21")

        args.add("--target-sdk-version")
        args.add(targetSdk ?: "32")

        args.add("-S")
        args.add(resDir.absolutePath)
        args.add("-M")
        args.add(manifest.path)
        args.add("-F")
        if (!apkFile.exists()) {
            apkFile.createNewFile()
        }
        args.add(apkFile.path)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = readInputStream(aaptProcess.errorStream)
        if (error.isNotEmpty()) {
            throw Exception(LogHelper.formatLog(error))
        }
    }
}