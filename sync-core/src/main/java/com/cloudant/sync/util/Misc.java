/**
 * Copyright (c) 2013 Cloudant, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.cloudant.sync.util;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Internal utility class
 */
public class Misc {

    public static final String ANDROID_RUNTIME = "android runtime";
    private static final Logger logger = Logger.getLogger(Misc.class.getCanonicalName());

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isRunningOnAndroid() {
        String javaRuntime = System.getProperty("java.runtime.name", "");
        return javaRuntime.toLowerCase().contains(ANDROID_RUNTIME);
    }

    public static byte[] getSha1(InputStream shaFis) {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            int bufSiz = 1024;
            byte buf[] = new byte[bufSiz];
            int bytesRead;
            while ((bytesRead = shaFis.read(buf)) != -1) {
                sha1.update(buf, 0, bytesRead);
            }
        } catch (Exception e) {
            logger.log(Level.WARNING,"Problem calacualting SHA1 for stream",e);
            return null;
        }
        return sha1.digest();
    }

}
