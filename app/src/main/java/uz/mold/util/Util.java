package uz.mold.util;


import android.content.Context;
import android.util.DisplayMetrics;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;

@SuppressWarnings("WeakerAccess")
public final class Util {

    public static <A> A nvl(A v, A d) {
        return v != null ? v : d;
    }

    public static String nvl(String v) {
        return nvl(v, "");
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String calcSHA(byte[] bytes) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(bytes);
        return bytesToHex(md.digest());
    }

    public static String extractStackTrace(Throwable ex) {
        StringWriter result = new StringWriter();
        PrintWriter writer = new PrintWriter(result);
        ex.printStackTrace(writer);
        writer.close();
        return result.toString();
    }

    public static int dpToPx(Context context, int d) {
        if (context == null) return d;
        return Math.round(d * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static String detectTextColor(String backgroundHex) {
        if (backgroundHex.isEmpty()) return "#ffffff";
        int r = Integer.parseInt(backgroundHex.substring(1, 2), 16);
        int g = Integer.parseInt(backgroundHex.substring(3, 4), 16);
        int b = Integer.parseInt(backgroundHex.substring(5, 6), 16);
        int res = Math.round((r * 299F + g * 587F + b * 114F) / 1000F);
        return res > 170 ? "#000000" : "#ffffff";
    }

}
