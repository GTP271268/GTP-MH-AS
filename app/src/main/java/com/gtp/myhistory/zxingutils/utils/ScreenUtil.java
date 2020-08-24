package com.gtp.myhistory.zxingutils.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author Gtp
 * @description:
 * @date :2020/1/1 0001 9:05
 */
public class ScreenUtil {
    public static int spToPx(Context context, float spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }

    public static int getWidthPixels() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int dpToPx(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public static final int dip2pix(Context context, float dip) {
        final float scale = context.getResources()
                .getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }
}
