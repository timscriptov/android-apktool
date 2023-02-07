package com.mcal.example

import android.app.Activity
import android.os.Bundle
import brut.androlib.Androlib
import brut.androlib.ApkDecoder
import com.mcal.androlib.options.BuildOptions
import java.io.File

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * @param toolsDir путь к директории с бинарниками и фреймфорками:
     *      ../toolsDir/1.apk
     *      ../toolsDir/android-framework.jar
     *      ../toolsDir/aapt
     *      ../toolsDir/aapt2
     */
    private fun decode(apkPath: File, decodeRootPath: File, toolsDir: String) {
        try {
            ApkDecoder(apkPath, Androlib(BuildOptions().apply {
                frameworkFolderLocation = toolsDir
                aaptPath = toolsDir + File.separator + "aapt"
                aapt2Path = toolsDir + File.separator + "aapt2"
                isAaptRules = true
                isJsonConfig = true
            }, null)).apply {
                setApkFile(apkPath)
                setBaksmaliDebugMode(false)
                setFrameworkDir(toolsDir)
                setDecodeResources(ApkDecoder.DECODE_RESOURCES_FULL)
                setDecodeSources(ApkDecoder.DECODE_SOURCES_SMALI)
                setOutDir(decodeRootPath)
                setApiLevel(14)
                setForceDelete(true)
                decode()
            }.decode()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}