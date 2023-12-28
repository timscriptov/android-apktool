/*
 *  Copyright (C) 2010 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2010 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.androlib;

import com.mcal.androlib.utils.Aapt;
import com.mcal.androlib.utils.Aapt2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import brut.androlib.apk.ApkInfo;
import brut.androlib.exceptions.AndrolibException;
import brut.common.BrutException;
import brut.util.AaptManager;

public class AaptInvoker {
    private final static Logger LOGGER = Logger.getLogger(AaptInvoker.class.getName());
    private final Config mConfig;
    private final ApkInfo mApkInfo;

    public AaptInvoker(Config config, ApkInfo apkInfo) {
        mConfig = config;
        mApkInfo = apkInfo;
    }

    private File getAaptBinaryFile() throws AndrolibException {
        try {
            if (getAaptVersion() == 2) {
                return AaptManager.getAapt2(mConfig.frameworkDirectory);
            }
            return AaptManager.getAapt1(mConfig.frameworkDirectory);
        } catch (BrutException ex) {
            throw new AndrolibException(ex);
        }
    }

    private int getAaptVersion() {
        return mConfig.isAapt2() ? 2 : 1;
    }

    private File createDoNotCompressExtensionsFile(ApkInfo apkInfo) throws AndrolibException {
        if (apkInfo.doNotCompress == null || apkInfo.doNotCompress.isEmpty()) {
            return null;
        }

        File doNotCompressFile;
        try {
            doNotCompressFile = File.createTempFile("APKTOOL", null);
            doNotCompressFile.deleteOnExit();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(doNotCompressFile));
            for (String extension : apkInfo.doNotCompress) {
                fileWriter.write(extension);
                fileWriter.newLine();
            }
            fileWriter.close();

            return doNotCompressFile;
        } catch (IOException ex) {
            throw new AndrolibException(ex);
        }
    }

    public void invokeAapt(File apkFile, File manifest, File resDir, File rawDir, File assetDir, File[] include)
            throws AndrolibException {
        if (mConfig.isAapt2()) {
            Aapt2.build(apkFile, include, manifest, resDir, mApkInfo.getMinSdkVersion(), mApkInfo.getTargetSdkVersion(), mConfig.aapt2Path, mConfig.ignoreMultiRes);
        } else {
            Aapt.build(apkFile, include, manifest, resDir, mApkInfo.getMinSdkVersion(), mApkInfo.getTargetSdkVersion(), mConfig.aaptPath, mConfig.ignoreMultiRes);
        }
    }
}
