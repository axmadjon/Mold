package uz.mold.lang;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleHelper {

    public static Locale getLocale(Resources resources) {
        Configuration config = resources.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }

    public static Context setLocale(Context c, String language) {
        return updateResources(c, language);
    }

    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }

        res.updateConfiguration(config, res.getDisplayMetrics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config);
        }
        return new MyContextWrapper(context);
    }

    static class MyContextWrapper extends ContextWrapper {

        public MyContextWrapper(Context base) {
            super(base);
        }
    }
}