package com.mcal.example.utils;

import brut.androlib.ApkBuilder;
import brut.androlib.ApkDecoder;
import brut.androlib.Config;
import brut.androlib.exceptions.AndrolibException;
import brut.common.BrutException;
import brut.directory.DirectoryException;
import brut.directory.ExtFile;
import com.mcal.androlib.utils.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ApktoolHelper {
    public static void decode(@NotNull File apkFile, @NotNull File decodeDir, @NotNull File frameworkFile, Logger logger) {
        try {
            Config config = Config.getDefaultConfig();
            config.setDecodeSources(Config.DECODE_SOURCES_SMALI);
            config.setDecodeResources(Config.DECODE_RESOURCES_FULL);
            config.setDecodeAssets(Config.DECODE_ASSETS_NONE);
            config.setDefaultFramework(frameworkFile.getPath());
            config.setDecodeAllPackages(true);
            config.setForceDelete(true);
            new ApkDecoder(config, new ExtFile(apkFile), logger).decode(decodeDir);
        } catch (AndrolibException | IOException | DirectoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildProject(@NotNull File apkFile, @NotNull File decodeDir, @NotNull String toolsDir, @NotNull File frameworkFile, Logger logger) {
        try {
            ExtFile appDecodeDir = new ExtFile(decodeDir);
            Config config = Config.getDefaultConfig();
            config.setUseAapt2(true);
            config.setAaptPath(toolsDir + "/aapt");
            config.setAapt2Path(toolsDir + "/aapt2");
            config.setDefaultFramework(frameworkFile.getPath());
            config.setDecodeAllPackages(false); // aapt and aapt2 not support multi packages
            config.setForceDelete(true);
            new ApkBuilder(config, appDecodeDir, logger).build(apkFile);
        } catch (BrutException e) {
            throw new RuntimeException(e);
        }
    }
}
