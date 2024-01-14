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

import brut.androlib.apk.ApkInfo;
import brut.androlib.exceptions.AndrolibException;
import com.mcal.androlib.utils.Aapt;
import com.mcal.androlib.utils.Aapt2;

import java.io.File;

public class AaptInvoker {
    private final Config mConfig;
    private final ApkInfo mApkInfo;

    public AaptInvoker(Config config, ApkInfo apkInfo) {
        mConfig = config;
        mApkInfo = apkInfo;
    }

    public void invokeAapt(File apkFile, File manifest, File resDir, File rawDir, File assetDir, File[] include)
            throws AndrolibException {
        if (mConfig.isAapt2()) {
            Aapt2.build(apkFile, include, manifest, resDir, mApkInfo.getMinSdkVersion(), mApkInfo.getTargetSdkVersion(), mConfig.aapt2Path, mConfig.decodeAllPackages);
        } else {
            Aapt.build(apkFile, include, manifest, resDir, mApkInfo.getMinSdkVersion(), mApkInfo.getTargetSdkVersion(), mConfig.aaptPath, mConfig.decodeAllPackages);
        }
    }
}
