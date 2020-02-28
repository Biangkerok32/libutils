package com.snailstudio2010.libutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class BitmapUtils {

    private static final String TAG = "BitmapUtils";

    public static Bitmap getBitmapFromFile(String filePath) {

        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;

        int dw = ScreenUtils.getWidth();
        int dh = ScreenUtils.getHeight();
        Bitmap pic = BitmapFactory.decodeFile(filePath, op);
        float wRatio = (float) Math.ceil(op.outWidth / (float) dw);
        float hRatio = (float) Math.ceil(op.outHeight / (float) dh);
        if (wRatio > 1 || hRatio > 1) {
            // op.inSampleSize = (int)(wRatio + hRatio) / 2;
            op.inSampleSize = (int) Math.max(wRatio, hRatio);
        }
        op.inJustDecodeBounds = false;
        try {
            pic = BitmapFactory.decodeFile(filePath, op);
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return pic;
    }


    public static Bitmap getNewBitmapForNewHeight(Bitmap bitmap, float newHeight) {
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scale = newHeight / (float) height;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap newbmp = null;
        try {
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        return newbmp;
    }


    public static Bitmap getBitmapFromFileForPaintPad(File dst, int width,
                                                      int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSizeForPaintPad(opts, width,
                        height);
                Log.d(TAG, "getBitmapFromFile inSampleSize:"
                        + opts.inSampleSize);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static int computeSampleSizeForPaintPad(
            BitmapFactory.Options options, float width, float height) {

        float bmp_width = options.outWidth;
        float bmp_height = options.outHeight;

        Log.d(TAG, "bmp width:" + bmp_width);
        Log.d(TAG, "bmp height:" + bmp_height);

        if ((int) bmp_width == (int) width && (int) bmp_height == (int) height) {
            return 1;
        }

        float scale = 1;

        if (bmp_width < bmp_height) {
            if (bmp_width / bmp_height > width / height) {
                scale = bmp_width / width;
            } else {
                scale = bmp_height / height;
            }

        } else {
            if (bmp_height / bmp_width > width / height) {
                scale = bmp_height / width;
            } else {
                scale = bmp_width / height;
            }

        }

        int roundedSize = (int) scale;
        if (roundedSize < 1)
            roundedSize = 1;

        return roundedSize;
    }

    public static Bitmap getBitmapFromUriForPaintPad(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                    context.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(Bitmap bitmap) {
        return bitmap == null || bitmap.isRecycled();
    }

    public static void recycle(Bitmap bitmap) {
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    public static Bitmap getBitmapFromUri(Activity context, Uri uri) {
        return getBitmapFromUri(context, uri, 300);
    }

    public static Bitmap getBitmapFromUri(Activity context, Uri uri,
                                          int maxWidth) {
        Bitmap bitmap = null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        int dw = 600;
        int dh = 600;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri), null, op);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        float wRatio = (float) Math.ceil(op.outWidth / (float) dw);
        float hRatio = (float) Math.ceil(op.outHeight / (float) dh);
        if (wRatio > 1 || hRatio > 1) {
            op.inSampleSize = (int) Math.max(wRatio, hRatio);
        }
        op.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver()
                    .openInputStream(uri), null, op);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        if (bitmap.getWidth() > maxWidth)
            bitmap = getNewBitmap(bitmap, maxWidth);

        return bitmap;
    }

    public static Bitmap getNewBitmap(Bitmap bitmap, float newWidth) {
        int width = bitmap.getWidth();
        // 计算缩放比例
        float scaleWidth = newWidth / width;
        return getNewBitmapByScale(bitmap, scaleWidth);
    }

    public static Bitmap getNewBitmapByScale(Bitmap bitmap, float scale) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        try {
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap centerCrop(Bitmap bm) {
        int min = Math.min(bm.getWidth(), bm.getHeight());
        return zoomImg(bm, min, min, true);
    }

    public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight, boolean isCircle) {
        int w = bm.getWidth();
        int h = bm.getHeight();
        int retX;
        int retY;
        double wh = (double) w / (double) h;
        double nwh = (double) newWidth / (double) newHeight;
        if (wh > nwh) {
            retX = h * newWidth / newHeight;
            retY = h;
        } else {
            retX = w;
            retY = w * newHeight / newWidth;
        }
        int startX = w > retX ? (w - retX) / 2 : 0;
        int startY = h > retY ? (h - retY) / 2 : 0;

        Bitmap bit = bm;
        try {
            if (!isCircle) {
                bit = Bitmap.createBitmap(bm, startX, startY, retX, retY, null, false);
            } else {
                //依据原有的图片丶创建一个新的图片   格式是：Config.ARGB_4444
                bit = Bitmap.createBitmap(retX, retY, Bitmap.Config.ARGB_4444);
                //创建一个画布
                Canvas canvas = new Canvas(bit);
                //创建一个画笔
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                //画笔的颜色
                paint.setColor(Color.WHITE);
                //画布的格式默认为  零
                canvas.drawARGB(0, 0, 0, 0);
                //求得圆的半径
                float radius = Math.min(retX / 2, retY / 2);
                canvas.drawCircle(retX / 2, retY / 2, radius, paint);
                //重置画笔
                paint.reset();
                //调用截图图层的方法
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                //画图片
                canvas.drawBitmap(bm, -startX, -startY, paint);
            }
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            return bit;
        }
//        bm.recycle();
        return bit;
    }

}