package com.example.springstudy.utils;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class VersionUtil {

    public static final String VERSION_REGEX_STRING = "\\d+(\\.\\d+)*";

    public static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX_STRING);

    public static final String VERSION_SEPARATOR = ".";

    /**
     * 将字符串版本转换为整型版本
     */
    public static Long toVersionNum(String version) {
        if (StringUtils.isBlank(version)) {
            return 0L;
        }

        boolean versionMatcher = VERSION_PATTERN.matcher(version).matches();

        if (!versionMatcher) {
            return 0L;
        }

        String[] versionArray = StringUtils.split(version, VERSION_SEPARATOR);

        int length = Math.max(versionArray.length, 4);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            String versionValue;
            if (i < versionArray.length) {
                versionValue = versionArray[i];
            } else {
                versionValue = "";
            }
            builder.append(StringUtils.leftPad(versionValue, 4, "0"));
        }

        return Long.parseLong(builder.toString());
    }

    public static void main(String[] args) {
        System.out.println(toVersionNum("3.4.5"));
    }
}
