package prometheus.zero.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by ishwor on 02/11/16.
 */

public class AppPermissionUtil {
    public static boolean checkAndroidPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static void askPermission(Context context, Fragment fragment, String permission, String messageBody, MyPermission myPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasPermission = ContextCompat.checkSelfPermission(context, permission);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                if (!fragment.shouldShowRequestPermissionRationale(permission)) {
                    UserInterfaceUtil.showPermissionInfo(context, "Grant permission", messageBody, fragment, permission);
                    return;
                }
                fragment.requestPermissions(new String[]{permission},
                        123);
                return;
            }
            myPermission.onPermission();
        } else {
            myPermission.onPermission();
        }
    }

    /*Interfaces*/

    public interface MyPermission {
        void onPermission();
    }
}
