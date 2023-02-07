package com.mcal.androlib.utils

import com.mcal.androlib.options.BuildOptions
import com.mcal.androlib.utils.FileHelper.createNewFile
import com.mcal.androlib.utils.FileHelper.readInputStream
import com.mcal.common.tasks.LogHelper.formatLog
import java.io.File


object Aapt2 {
    @JvmStatic
    fun build(
        apkFile: File,
        include: Array<File>,
        manifest: File,
        resDir: File,
        minSdk: String?,
        targetSdk: String?,
        options: BuildOptions
    ) {
        val buildDir = File(resDir.parent, "build")
        compile(resDir, buildDir, options)
        link(apkFile, buildDir, include, manifest, minSdk, targetSdk, options)
    }

    private fun compile(
        resDir: File,
        buildDir: File,
        options: BuildOptions
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(options.aapt2Path)
        args.add("compile")
        args.add("--dir")
        args.add(resDir.absolutePath)
        args.add("-o")
        args.add(createNewFile(buildDir, resDir.name + ".zip").absolutePath)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = readInputStream(aaptProcess.errorStream)
        if (error.isNotEmpty()) {
            throw Exception(formatLog(error))
        }

        resDir.parent?.let { path ->
            compileLibraries(File(path), buildDir, options)
        }
    }

    private fun compileLibraries(
        path: File,
        buildDir: File,
        options: BuildOptions
    ) {
        path.listFiles()?.let { resources ->
            for (resDir in resources) {
                if (resDir.name.startsWith("res_")) {
                    if (!resDir.exists() || !resDir.isDirectory) {
                        continue
                    }
                    val args: MutableList<String> = ArrayList()
                    args.add(options.aapt2Path)
                    args.add("compile")
                    args.add("--dir")
                    args.add(resDir.absolutePath)
                    args.add("-o")
                    args.add(createNewFile(buildDir, resDir.name + ".zip").absolutePath)

                    val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
                    val error = readInputStream(aaptProcess.errorStream)
                    if (error.isNotEmpty()) {
                        throw Exception(formatLog(error))
                    }
                }
            }
        }
    }

    private fun link(
        apkFile: File,
        buildDir: File,
        include: Array<File>?,
        manifest: File,
        minSdk: String?,
        targetSdk: String?,
        options: BuildOptions
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(options.aapt2Path)
        args.add("link")
        include?.forEach { framework ->
            args.add("-I")
            args.add(framework.path)
        }
        args.add("--allow-reserved-package-id")
        args.add("--no-version-vectors")
        args.add("--no-version-transitions")
        args.add("--auto-add-overlay")
        args.add("--min-sdk-version")
        args.add(minSdk ?: "21")
        args.add("--target-sdk-version")
        args.add(targetSdk ?: "32")
        buildDir.listFiles()?.let { resources ->
            for (resource in resources) {
                if (resource.isDirectory) {
                    continue
                }
                if (!resource.name.endsWith(".zip")) {
                    continue
                }
                args.add("-R")
                args.add(resource.absolutePath)
            }
        }

        args.add("--manifest")
        args.add(manifest.absolutePath)

        args.add("-o")
        if (!apkFile.exists()) {
            apkFile.createNewFile()
        }
        args.add(apkFile.path)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = readInputStream(aaptProcess.errorStream)
        if (error.isNotEmpty()) {
            throw Exception(formatLog(error))
        }
    }
}