/**
 * Copyright (c) 2015 Cloudant, Inc. All rights reserved.
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

package com.cloudant.common;

import com.cloudant.mazha.CouchConfig;
import com.cloudant.mazha.SpecifiedCouch;
import com.cloudant.sync.util.Misc;
import com.google.common.base.Strings;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by tomblench on 20/01/15.
 */
public abstract class CouchTestBase {

    private static final Boolean SPECIFIED_COUCH = Boolean.valueOf(
            System.getProperty("test.with.specified.couch",Boolean.FALSE.toString()));

    public static final Boolean IGNORE_COMPACTION = Boolean.valueOf(
            System.getProperty("test.couch.ignore.compaction",Boolean.FALSE.toString()));

    public static final Boolean IGNORE_AUTH_HEADERS = Boolean.valueOf(
            System.getProperty("test.couch.ignore.auth.headers",Boolean.FALSE.toString()));

    public CouchConfig getCouchConfig(String db) {

        //Android is always a specified couch via the AndroidTest runner
        if (SPECIFIED_COUCH) {
            return SpecifiedCouch.defaultConfig(db);
        }
        else {
            return this.defaultConfig("127.0.0.1", db);
        }
    }

    public CouchConfig defaultConfig(String host, String databasePath) {
        try {
            // we use String.format rather than the multi-arg URI constructor to avoid database
            // names being (double) escaped
            String urlString = String.format("http://%s:5984/%s", host, databasePath);
            CouchConfig config = new CouchConfig(new URI(urlString));
            return config;
        } catch (URISyntaxException use) {
            use.printStackTrace();
            return null;
        }
    }
}
