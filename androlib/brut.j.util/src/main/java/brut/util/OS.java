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
package brut.util;

import brut.common.BrutException;
import com.mcal.androlib.utils.FileHelper;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.logging.Logger;

public class OS {

    private static final Logger LOGGER = Logger.getLogger("");

    public static void rmdir(@NotNull File dir) throws BrutException {
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                rmdir(file);
            } else {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
        //noinspection ResultOfMethodCallIgnored
        dir.delete();
    }

    public static void cpdir(@NotNull File src, @NotNull File dest) throws BrutException {
        //noinspection ResultOfMethodCallIgnored
        dest.mkdirs();
        File[] files = src.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            File destFile = new File(dest.getPath() + File.separatorChar + file.getName());
            if (file.isDirectory()) {
                cpdir(file, destFile);
                continue;
            }
            try {
                try (InputStream in = new FileInputStream(file)) {
                    try (OutputStream out = new FileOutputStream(destFile)) {
                        FileHelper.copyFile(in, out);
                    }
                }
            } catch (IOException ex) {
                throw new BrutException("Could not copy file: " + file, ex);
            }
        }
    }
}
