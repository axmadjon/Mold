package uz.mold.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import androidx.core.content.ContextCompat;

import java.io.File;

public class SysUtil {

    public static void checkMainLooper(String message) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new RuntimeException(message);
        }
    }

    public static boolean deleteFolderRecursively(File path) {
        if (path.isDirectory()) {
            for (File file : path.listFiles()) {
                if (!deleteFolderRecursively(file)) {
                    return false;
                }
            }
        }
        return path.delete();
    }


    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return wifi.isConnected() || mobile.isConnected();
    }

    public static String formatFileSize(long length) {
        if (length < 1 << 10) {
            return "" + length + " byte";
        }

        if (length < 1 << 20) {
            long b = length & (1 << 10 - 1);
            long k = length >>> 10;
            if (b == 0) {
                return "" + k + " kb";
            } else {
                return "" + k + "." + b + " kb";
            }
        }

        length = length >>> 10;
        long k = length & (1 << 10 - 1);
        long m = length >>> 10;
        if (k == 0) {
            return "" + m + " mb";
        } else {
            return "" + m + "." + k + " mb";
        }
    }

    public static String formatFileSize(File file) {
        return formatFileSize(file.length());
    }

    public static String formatFileSize(String path) {
        return formatFileSize(new File(path));
    }

    public static boolean existFile(String serverPath, String fileName) {
        return new File(serverPath, fileName).exists();
    }

    public static boolean checkSelfPermissionGranted(Context ctx, String permission) {
        if (ctx == null) return false;
        return ContextCompat.checkSelfPermission(ctx, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
