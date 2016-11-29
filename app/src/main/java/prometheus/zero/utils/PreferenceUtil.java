package prometheus.zero.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ishwor on 02/11/16.
 */

public class PreferenceUtil {
    private static SharedPreferences sharedPreference;

    public static SharedPreferences getSharedPreference(Context context) {
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreference;
    }

    public static SharedPreferences.Editor getPreferenceEditor(Context context) {
        return sharedPreference.edit();
    }
}
