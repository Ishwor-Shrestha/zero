package prometheus.zero.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by ishwor on 02/11/16.
 */

public class StringUtil {
    public static String getNameIcon(String name) {
        String[] splitName = name.split("\\s+");
        String icon;
        if (splitName.length > 1) {
            String iconName = splitName[0].substring(0, 1) + splitName[splitName.length - 1].substring(0, 1);
            icon = (iconName.toUpperCase());
        } else {
            icon = splitName[0].substring(0, 1).toUpperCase();
        }
        return icon;
    }

    public static String formatBalance(Double balance) {
        return String.format("%.2f", balance);
    }

    public static String addComma(Long number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }
}
