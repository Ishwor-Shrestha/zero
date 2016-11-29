package prometheus.zero.utils;

/**
 * Created by ishwor on 02/11/16.
 */

public class ConvertUtil {
    public static Integer stringToInteger(String number) {
        return Integer.parseInt(number);
    }

    public static String anyToString(Object number) {
        return number.toString();
    }
}
