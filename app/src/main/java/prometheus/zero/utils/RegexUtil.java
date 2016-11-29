package prometheus.zero.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ishwor on 02/11/16.
 */

public class RegexUtil {
    public static boolean validMobileNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("([9][678][0-9]{8})");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }

    public static boolean validLandLineNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("(\\d{5}|\\d{6}|\\d{7})");
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }

    public static String formatMobileNumber(String phoneNumber) {
        String formattedPhoneNumber;
        if (phoneNumber.length() > 0) {
            formattedPhoneNumber = phoneNumber.replaceAll("\\D+", "");
            formattedPhoneNumber = formattedPhoneNumber.substring(formattedPhoneNumber.length() - 10, formattedPhoneNumber.length());
            if (validMobileNumber(formattedPhoneNumber)) {
                return formattedPhoneNumber;
            }
        }
        return "";
    }

    public static String formatLandLineNumber(String phoneNumber) {
        String formattedPhoneNumber;
        if (phoneNumber.length() > 0) {
            formattedPhoneNumber = phoneNumber.replaceAll("\\D+", "");
            formattedPhoneNumber = formattedPhoneNumber.substring(formattedPhoneNumber.length() - 7, formattedPhoneNumber.length());

            if (validLandLineNumber(formattedPhoneNumber)) {
                return formattedPhoneNumber;
            }
        }
        return "";
    }

    public static boolean validateTelecom(String phoneNumber, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.find();
    }
}
