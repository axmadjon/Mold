package uz.mold;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import uz.mold.lang.LangPref;
import uz.mold.lang.LocaleHelper;

public class MoldApplication extends Application {

    private static MoldApplication moldInstance;
    private static Context resourceContext;

    public static Context getResourceContext() {
        if (resourceContext == null) {
            String language = LangPref.getLanguage(moldInstance);
            resourceContext = LocaleHelper.setLocale(moldInstance, language);
        }

        return resourceContext;
    }

    public static String getLangString(int resId) {
        return getResourceContext().getString(resId);
    }

    public static String getLangString(int resId, Object... formatArgs) {
        return getResourceContext().getString(resId, formatArgs);
    }

    public static SharedPreferences getSharedPreferences(String name) {
        return moldInstance.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void changeLanguageCode() {
        resourceContext = null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        moldInstance = this;
    }
}
