package com.snailstudio2010.libutils;

/**
 * Created by xuqiqiang on 2019/11/13.
 */
public class MathUtils {

    public static boolean eq0(float number) {
        return number > -0.000001 && number < 0.000001;
    }

    public static boolean eq0(double number) {
        return number > -0.000001 && number < 0.000001;
    }
}