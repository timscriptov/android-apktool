package com.mcal.androlib.utils

import com.mcal.androlib.utils.FileHelper.createNewFile
import com.mcal.androlib.utils.LogHelper.formatLog
import java.io.File
import java.nio.charset.StandardCharsets

object Aapt2 {
    @JvmStatic
    fun build(
        apkFile: File,
        include: Array<File>,
        manifest: File,
        resDir: File,
        minSdk: String?,
        targetSdk: String?,
        aapt2Path: String,
        decodeAllPackages: Boolean
    ) {
        val buildDir = File(resDir.parent, "build")
        compile(resDir, buildDir, aapt2Path, decodeAllPackages)
        link(apkFile, buildDir, include, manifest, minSdk, targetSdk, aapt2Path)
    }

    private fun compile(
        resDir: File,
        buildDir: File,
        aapt2Path: String,
        decodeAllPackages: Boolean
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(aapt2Path)
        args.add("compile")
        args.add("--dir")
        args.add(resDir.path)
        args.add("-o")
        args.add(createNewFile(buildDir, resDir.name + ".zip").path)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = aaptProcess.errorStream.readBytes().toString(StandardCharsets.UTF_8)
        if (error.isNotEmpty()) {
            throw Exception(formatLog(error))
        }

        if (decodeAllPackages) {
            resDir.parent?.let { path ->
                compileLibraries(File(path), buildDir, aapt2Path)
            }
        }
    }

    private fun compileLibraries(
        path: File,
        buildDir: File,
        aapt2Path: String,
    ) {
        path.walk().forEach { file ->
            if (file.name.startsWith("res_") && file.exists() && file.isDirectory) {
                val args: MutableList<String> = ArrayList()
                args.add(aapt2Path)
                args.add("compile")
                args.add("--dir")
                args.add(file.path)
                args.add("-o")
                args.add(createNewFile(buildDir, file.name + ".zip").path)

                val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
                val error = aaptProcess.errorStream.readBytes().toString(StandardCharsets.UTF_8)
                if (error.isNotEmpty()) {
                    throw Exception(formatLog(error))
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
        aapt2Path: String,
    ) {
        val args: MutableList<String> = ArrayList()
        args.add(aapt2Path)
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
        args.add(targetSdk ?: "33")
        buildDir.listFiles()?.let { resources ->
            for (resource in resources) {
                if (resource.isDirectory) {
                    continue
                }
                if (!resource.name.endsWith(".zip")) {
                    continue
                }
                args.add("-R")
                args.add(resource.path)
            }
        }

        args.add("--manifest")
        args.add(manifest.path)

        args.add("-o")
        if (!apkFile.exists()) {
            apkFile.createNewFile()
        }
        args.add(apkFile.path)

        val aaptProcess = Runtime.getRuntime().exec(args.toTypedArray())
        val error = aaptProcess.errorStream.readBytes().toString(StandardCharsets.UTF_8)
        if (error.isNotEmpty()) {
            throw Exception(formatLog(error))
        }
    }
}
