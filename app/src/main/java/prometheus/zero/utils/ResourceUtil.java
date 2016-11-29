package prometheus.zero.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by ishwor on 02/11/16.
 */

public class ResourceUtil {
    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static TypedArray getIntegerArray(Context context, int id) {
        return context.getResources().obtainTypedArray(id);
    }

    public static int getColor(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }
}
