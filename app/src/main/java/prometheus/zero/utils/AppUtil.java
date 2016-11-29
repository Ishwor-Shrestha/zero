package prometheus.zero.utils;

public class AppUtil {
    public static Class getClassName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getPackageName(String serviceName) {
        return serviceName.substring(0, 1).toLowerCase() + serviceName.substring(1);
    }
}
