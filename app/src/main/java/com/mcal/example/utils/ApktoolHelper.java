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
    public static void decode(
            @NotNull File apkFile,
            @NotNull File decodeDir,
            @NotNull String frameworkPath,
            @NotNull Logger logger) {
        try {
            Config config = Config.getDefaultConfig();
            config.setDecodeSources(Config.DECODE_SOURCES_SMALI);
            config.setDecodeResources(Config.DECODE_RESOURCES_FULL);
            config.setDecodeAssets(Config.DECODE_ASSETS_NONE);
            config.setDefaultFramework(frameworkPath);
            config.setDecodeAllPackages(true);
            config.setForceDelete(true);
            new ApkDecoder(config, new ExtFile(apkFile), logger).decode(decodeDir);
        } catch (AndrolibException | IOException | DirectoryException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildProject(
            @NotNull File apkFile,
            @NotNull File decodeDir,
            @NotNull String frameworkPath,
            @NotNull String aaptPath,
            @NotNull String aapt2Path,
            boolean useAapt2,
            @NotNull Logger logger) {
        try {
            logger.info("Aapt version: " + (useAapt2 ? "2" : "1"));
            ExtFile appDecodeDir = new ExtFile(decodeDir);
            Config config = Config.getDefaultConfig();
            config.setUseAapt2(useAapt2);
            config.setAaptPath(aaptPath);
            config.setAapt2Path(aapt2Path);
            config.setDefaultFramework(frameworkPath);
            config.setDecodeAllPackages(false); // aapt and aapt2 not support multi packages
            config.setForceDelete(true);
            new ApkBuilder(config, appDecodeDir, logger).build(apkFile);
        } catch (BrutException e) {
            throw new RuntimeException(e);
        }
    }
}
