/**
 * Copyright (C) 2016 Snailstudio. All rights reserved.
 * <p>
 * https://xuqiqiang.github.io/
 *
 * @author xuqiqiang (the sole member of Snailstudio)
 */
package com.snailstudio2010.libutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class IntentUtils {

    public static final String camera_photo_path = ".camera_photo";
    public static final String camera_photo_name = "camera_photo.jpg";
    public static final int DEFAULT_REQUEST_CODE = 99;
    public static final String PHOTO_CROP_PATH = ".PhotoCrop";
    public static final String PHOTO_CROP_FILE_NAME = "photo_cropped.jpg";
    private static final String TAG = IntentUtils.class.getSimpleName();

    public static File getPhotographFile() {
        return new File(Cache.getRealFilePath(camera_photo_path),
                camera_photo_name);
    }

    public static boolean takePhotograph(Activity context, int requestCode) {
        try {
//            Cache.createDir(camera_photo_path);
            FileUtils.createDir(camera_photo_path);

            Intent intent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getPhotographFile()));
            context.startActivityForResult(intent,
                    requestCode);
        } catch (ActivityNotFoundException e) {
//            ToastMaster.showToast(context, R.string.not_install_app);
            return false;
        }
        return true;
    }

    public static boolean getImageFromGallery(Activity context, int requestCode) {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            context.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            return false;
        }
        return true;
    }

    public static File getCropFile() {
        return new File(
                Cache.getRealFilePath(PHOTO_CROP_PATH),
                PHOTO_CROP_FILE_NAME);
    }

    /**
     * 调用系统图片编辑进行裁剪
     */
    public static boolean startPhotoCrop(Activity context, Uri uri, int requestCode) {

        try {
            FileUtils.createDir(PHOTO_CROP_PATH);

            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getCropFile()));
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true); // no face detection
            context.startActivityForResult(intent, requestCode);

        } catch (ActivityNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void showImage(Activity context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        context.startActivity(intent);
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null,
                null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        Log.d(TAG, "file path:" + result);
        return result;
    }

    public static void getLocalImage(Activity context, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        context.startActivityForResult(intent, requestCode);
    }

    public static boolean checkPermission(Context context, String permission) {
//        Log.d(TAG, "checkSelfPermission:" + context.checkSelfPermission(
//                permission));
//        Log.d(TAG, "checkCallingOrSelfPermission:" + context.checkCallingOrSelfPermission(
//                permission));
//        Log.d(TAG, "checkPermission:" + context.checkPermission(
//                permission,
//                android.os.Process.myPid(), android.os.Process.myUid()));
        return context.checkPermission(
                permission,
                android.os.Process.myPid(), android.os.Process.myUid())
                == PackageManager.PERMISSION_GRANTED;
    }

//    public boolean selfPermissionGranted(String permission) {
//        // For Android < Android M, self permissions are always granted.
//        boolean result = true;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (targetSdkVersion >= Build.VERSION_CODES.M) {
//                // targetSdkVersion >= Android M, we can
//                // use Context#checkSelfPermission
//                result = context.checkSelfPermission(permission)
//                        == PackageManager.PERMISSION_GRANTED;
//            } else {
//                // targetSdkVersion < Android M, we have to use PermissionChecker
//                result = PermissionChecker.checkSelfPermission(context, permission)
//                        == PermissionChecker.PERMISSION_GRANTED;
//            }
//        }
//
//        return result;
//    }

    public static void requestPermissions(Activity activity,
                                          String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                ReflectionUtils.getMethod(activity.getClass(),
                        "validateRequestPermissionsRequestCode",
                        int.class).invoke(DEFAULT_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            activity.requestPermissions(new String[]{
                    permission}, DEFAULT_REQUEST_CODE);

        }
//        } else {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    final int[] grantResults = new int[permissions.length];
//
//                    PackageManager packageManager = activity.getPackageManager();
//                    String packageName = activity.getPackageName();
//
//                    final int permissionCount = permissions.length;
//                    for (int i = 0; i < permissionCount; i++) {
//                        grantResults[i] = packageManager.checkPermission(
//                                permissions[i], packageName);
//                    }
//
//                }
//            });
//        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                  int[] grantResults) {
        if (requestCode == DEFAULT_REQUEST_CODE) {

        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkAndRequestPermissions(Activity activity, String[] permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        List<String> lackedPermission = new ArrayList<>();
        for (String permission : permissions) {
            if (!(activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)) {
                lackedPermission.add(permission);
            }
        }
//        if (!(activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
//            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
//        }
//
//        if (!(activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
//            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        }
//
//        if (!(activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
//            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }

        if (lackedPermission.size() == 0) {
            return true;
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            activity.requestPermissions(requestPermissions, DEFAULT_REQUEST_CODE);
        }
        return false;
    }

    public static void runOnUiThread(final Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (runnable != null) runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void runOnUiThread(final Runnable runnable, Handler handler) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (runnable != null) runnable.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            runOnUiThread(runnable);
        }
    }

    public static void runOnUiThread(final Runnable runnable, Activity activity) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (runnable != null) runnable.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            runOnUiThread(runnable);
        }
    }
}