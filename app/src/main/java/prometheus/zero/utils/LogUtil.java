package prometheus.zero.utils;

import android.util.Log;

/**
 * Created by ishwor on 02/11/16.
 */

public class LogUtil {

    private static final boolean DEBUG = true;

    public static void log(String tag, String message) {
        if (DEBUG) {
            Log.i("Debug", "**/|| " + tag + " ||** ------------------------------------" + message);
        }
    }

    public static void checkpoint(String message) {
        if (DEBUG) {
            Log.i("Debug", "**/|| " + "Checkpoint" + " ||** ------------------------------------" + message);
        }
    }

    public static void sysOut(String message) {
        System.out.print(message);
    }
}
