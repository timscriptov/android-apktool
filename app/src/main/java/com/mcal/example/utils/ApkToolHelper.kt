package com.mcal.example.utils

import brut.androlib.ApkBuilder
import brut.androlib.ApkDecoder
import brut.androlib.Config
import brut.directory.ExtFile
import com.mcal.androlib.utils.Logger
import java.io.File

object ApkToolHelper {
    /**
     * @param toolsDir путь к директории с бинарниками и фреймфорками:
     *      ../toolsDir/1.apk
     *      ../toolsDir/android-framework.jar
     *      ../toolsDir/aapt
     *      ../toolsDir/aapt2
     */
    fun decode(apkPath: File, decodeRootPath: File, toolsDir: String, logger: Logger) {
        try {
            val apkFile = ExtFile(apkPath)
            val config = Config.getDefaultConfig()
            config.setDecodeSources(Config.DECODE_SOURCES_SMALI)
            config.setDecodeResources(Config.DECODE_RESOURCES_FULL)
            config.setDecodeAssets(Config.DECODE_ASSETS_NONE)
            config.setDefaultFramework(toolsDir)
            config.setDecodeAllPackages(true)
            config.setForceDelete(true)
            ApkDecoder(config, apkFile, logger).decode(decodeRootPath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun buildProject(apkPath: File, decodeRootPath: File, toolsDir: String, logger: Logger) {
        val appDecodeDir = ExtFile(decodeRootPath)
        val config = Config.getDefaultConfig()
        config.setUseAapt2(true)
        config.setAaptPath("$toolsDir/aapt")
        config.setAapt2Path("$toolsDir/aapt2")
        config.setDefaultFramework(toolsDir)
        config.setDecodeAllPackages(true)
        config.setForceDelete(true)
        ApkBuilder(config, appDecodeDir, logger).build(apkPath)
    }
}