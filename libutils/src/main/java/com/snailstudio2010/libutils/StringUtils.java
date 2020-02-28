package com.snailstudio2010.libutils;

public class StringUtils {

    public static boolean startsWithIgnoreCaseMaybe(String text, String prefix) {
        return text != null && (text.startsWith(prefix)
                || text.toLowerCase().startsWith(prefix.toLowerCase())
                || text.toUpperCase().startsWith(prefix.toUpperCase()));
    }

    public static boolean endsWithIgnoreCaseMaybe(String text, String suffix) {
        return text != null && (text.endsWith(suffix)
                || text.toLowerCase().endsWith(suffix.toLowerCase())
                || text.toUpperCase().endsWith(suffix.toUpperCase()));
    }

    public static boolean startsWithIgnoreCase(String text, String prefix) {
        return text != null && (text.startsWith(prefix)
                || text.startsWith(prefix.toLowerCase())
                || text.startsWith(prefix.toUpperCase()));
    }

    public static boolean endsWithIgnoreCase(String text, String suffix) {
        return text != null && (text.endsWith(suffix)
                || text.endsWith(suffix.toLowerCase())
                || text.endsWith(suffix.toUpperCase()));
    }

    /**
     * 字节 转换为B MB GB
     *
     * @param size 字节大小
     * @return
     */
    public static String getPrintSize(long size) {
        long rest = 0;
        if (size < 1024) {
            return size + "B";
        } else {
            size /= 1024;
        }

        if (size < 1024) {
            return size + "KB";
        } else {
            rest = size % 1024;
            size /= 1024;
        }

        if (size < 1024) {
            size = size * 100;
            return size / 100 + "." + rest * 100 / 1024 % 100 + "MB";
        } else {
            size = size * 100 / 1024;
            return size / 100 + "." + size % 100 + "GB";
        }
    }
}