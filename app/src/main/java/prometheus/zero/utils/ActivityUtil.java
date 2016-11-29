package prometheus.zero.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

import java.util.HashMap;

/**
 * Created by ishwor on 02/11/16.
 */

public class ActivityUtil {

    private static Intent intent;

    public static void openActivity(Class className, Activity activity, boolean hasData, HashMap<String, String> data, boolean animate) {
        intent = new Intent(activity, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (animate) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }

        if (hasData) {
            for (String key : data.keySet()) {
                intent.putExtra(key, data.get(key));
            }
        }

        activity.startActivity(intent);
    }

    public static void clearOpenActivity(Activity activity, Class classname) {
        intent = new Intent(activity, classname);
        ComponentName cn = intent.getComponent();
        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
        activity.startActivity(mainIntent);
    }
}
