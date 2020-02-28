package com.snailstudio2010.libutils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by xuqiqiang on 2016/05/17.
 */
public class ViewUtils {

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static void setMargins(View v, int value) {
        setMargins(v, value, value, value, value);
    }

    public static void setSize(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public static Bitmap getViewBitmap(View view) {
        view.setDrawingCacheEnabled(false);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static Bitmap getViewBitmap(View view, int width, int height) {
        view.setDrawingCacheEnabled(false);
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static void fullScreenImmersive(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && window != null) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(uiOptions);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }
}