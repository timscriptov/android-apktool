package com.mcal.androlib.utils

import java.io.File
import java.nio.charset.StandardCharsets

object Aapt {
    @JvmStatic
    fun build(
        apkFile: File,
        include: Array<File?>?,
        manifest: File,
        resDir: File,
        minSdk: String?,
        targetSdk: String?,
        aaptPath: String,
        buildAllPackages: Boolean
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(aaptPath)
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
        args.add(resDir.path)
        if (buildAllPackages) {
            resDir.parent?.let { path ->
                File(path).walk().forEach { file ->
                    if (file.name.startsWith("res_") && file.exists() && file.isDirectory) {
                        args.add("-S")
                        args.add(file.path)
                    }
                }
            }
        }
        args.add("-M")
        args.add(manifest.path)
        args.add("-F")
        if (!apkFile.exists()) {
            apkFile.createNewFile()
        }
        args.add(apkFile.path)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = aaptProcess.errorStream.readBytes().toString(StandardCharsets.UTF_8)
        if (error.isNotEmpty()) {
            throw Exception(LogHelper.formatLog(error))
        }
    }
}
