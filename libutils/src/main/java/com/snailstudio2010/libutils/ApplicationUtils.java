package com.snailstudio2010.libutils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class ApplicationUtils {

    // 获取版本号(内部识别号)
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getApplicationMetaData(Context context, String key) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getActivityMetaData(Activity activity, String key) {
        try {
            ActivityInfo appInfo = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null)
                return appInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getActivityMetaDataInt(Activity activity, String key) {
        try {
            ActivityInfo appInfo = activity.getPackageManager().getActivityInfo(
                    activity.getComponentName(), PackageManager.GET_META_DATA);
            if (appInfo != null && appInfo.metaData != null)
                return appInfo.metaData.getInt(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static boolean isAppInstalled(Context context, String pkg_name,
                                         int version) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkg_name,
                    0);
            Log.d("ApplicationUtils", packageInfo.versionCode + "");
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null && version <= packageInfo.versionCode;
    }

}