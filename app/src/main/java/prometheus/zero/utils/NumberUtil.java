package prometheus.zero.utils;


import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberUtil {
    public static Double convertBalance(Long balance) {
        Double paisa = Double.parseDouble(balance.toString());
        return (paisa / 100);
    }

    public static Long removeComma(String number) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
        try {
            Number num = format.parse(number);
            return Long.parseLong(num.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
