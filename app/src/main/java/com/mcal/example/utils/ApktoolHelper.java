package com.mcal.example.utils;

import com.mcal.androlib.utils.Logger;

import java.io.File;
import java.io.IOException;

import brut.androlib.ApkBuilder;
import brut.androlib.ApkDecoder;
import brut.androlib.Config;
import brut.androlib.exceptions.AndrolibException;
import brut.common.BrutException;
import brut.directory.DirectoryException;
import brut.directory.ExtFile;
import brut.androlib.res.Framework;

public class ApktoolHelper {
    /**
     * @param toolsDir путь к директории с бинарниками и фреймфорками:
     *                 ../toolsDir/1.apk
     *                 ../toolsDir/android-framework.jar
     *                 ../toolsDir/aapt
     *                 ../toolsDir/aapt2
     */
    public static void decode(File apkPath, File decodeRootPath, String toolsDir, String fwPath, Logger logger) {
        try {
            ExtFile apkFile = new ExtFile(apkPath);
            Config config = Config.getDefaultConfig();
            config.setDecodeSources(Config.DECODE_SOURCES_SMALI);
            config.setDecodeResources(Config.DECODE_RESOURCES_FULL);
            config.setDecodeAssets(Config.DECODE_ASSETS_NONE);
            config.setDefaultFramework(toolsDir);
            config.setDecodeAllPackages(true);
            config.setForceDelete(true);
            if (!fwPath.matches("")) {
                Framework fw = new Framework(config);
                fw.installFramework(new File(fwPath), logger);
            }
            new ApkDecoder(config, apkFile, logger).decode(decodeRootPath);
        } catch (AndrolibException | IOException | DirectoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildProject(File apkPath, File decodeRootPath, String toolsDir, Logger logger) {
        try {
            ExtFile appDecodeDir = new ExtFile(decodeRootPath);
            Config config = Config.getDefaultConfig();
            config.setUseAapt2(true);
            config.setAaptPath(toolsDir + "/aapt");
            config.setAapt2Path(toolsDir + "/aapt2");
            config.setDefaultFramework(toolsDir);
            config.setDecodeAllPackages(false); // aapt and aapt2 not support multi packages
            config.setForceDelete(true);
            new ApkBuilder(config, appDecodeDir, logger).build(apkPath);
        } catch (BrutException e) {
            throw new RuntimeException(e);
        }
    }
}
