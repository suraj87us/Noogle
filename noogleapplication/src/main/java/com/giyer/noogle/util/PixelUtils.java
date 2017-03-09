package com.giyer.noogle.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by giyer7 on 3/8/17.
 */

public class PixelUtils {
    /**
     * converting dip to pixels
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    /**
     * converting sp to pixels
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int SpToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dipValue, metrics);
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px
     *            A value in px (pixels) unit. Which we need to convert into db
     * @param context
     *            Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     *
     * Courtesy of http://stackoverflow.com/a/9563438/2423312
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
