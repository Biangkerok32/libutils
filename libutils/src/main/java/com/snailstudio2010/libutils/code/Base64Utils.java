/**
 * Copyright (C) 2016 Snailstudio. All rights reserved.
 * <p>
 * https://xuqiqiang.github.io/
 *
 * @author xuqiqiang (the sole member of Snailstudio)
 */
package com.snailstudio2010.libutils.code;

import android.util.Base64;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class Base64Utils {

    public static String encode(String str) {
        String str_64 = Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
        str_64 = str_64.replace("/", "_a");
        return str_64.trim();
    }

    public static String decode(String str_64) {
        str_64 = str_64.replace("_a", "/");
        String str = new String(
                Base64.decode(str_64.getBytes(), Base64.DEFAULT));
        return str.trim();
    }
}