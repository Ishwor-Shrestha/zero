package prometheus.zero.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;

import prometheus.zero.R;

public class UserInterfaceUtil {
    public static void showPermissionInfo(Context context, String title, String body, final Fragment fragment, final String permission) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(body)
                .positiveText(R.string.button_ok)
                .negativeText(R.string.button_cancel)
                .onPositive((materialDialog, dialogAction) -> {
                    materialDialog.dismiss();
                    fragment.requestPermissions(new String[]{permission}, 123);
                })
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    public static void showSnackBar(Context context, CoordinatorLayout coordinatorLayout, String message, boolean action, String buttonText,
                                    int snackBarLength, SnackBarAction snackBarAction) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, snackBarLength);
        if (action) {
            snackbar.setActionTextColor(ResourceUtil.getColor(context, R.color.accentColor));
            snackbar.setAction(buttonText, view -> snackBarAction.action());
        }
        snackbar.show();
    }

    public static MaterialDialog runProgressDialog(Context context, String title, String body) {
        final MaterialDialog progressDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(body)
                .progress(true, 0)
                .show();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static void showInfoDialog(Context context, String title, String body, MyDialogAction myDialogAction) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(body)
                .positiveText(R.string.button_ok)
                .onPositive((materialDialog, dialogAction) -> myDialogAction.onOk(materialDialog))
                .show();
    }

    public static String showInputDialog(Context context, String title, String content, MyDialogAction myDialogAction) {

        String[] inputField = {""};
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input(content, null, (dialog, input) -> inputField[0] = input.toString()).negativeText("Cancel").show();

        return Arrays.toString(inputField);

    }

    public static void showToast(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    /*Interfaces*/
    public interface MyDialogAction {
        void onOk(MaterialDialog materialDialog);

        void onCancel(MaterialDialog materialDialog);
    }

    public interface SnackBarAction {
        void action();
    }
}
