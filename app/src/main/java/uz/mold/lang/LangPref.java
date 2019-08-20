package uz.mold.lang;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import uz.mold.MoldApplication;

public class LangPref {
    public static final String LANGUAGE_RU = "ru";
    public static final String LANGUAGE_EN = "en";

    private static final String LANGUAGE = "language";

    private static SharedPreferences getPref() {
        return MoldApplication.getSharedPreferences("mold_pref");
    }

    @Nullable
    private static String load(String code) {
        return load(code, null);
    }

    @Nullable
    private static String load(String code, @Nullable String defaultValue) {
        return getPref().getString(code, defaultValue);
    }

    private static void save(String code, String value) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor edit = pref.edit();
        if (TextUtils.isEmpty(value)) {
            edit.remove(code);
        } else {
            edit.putString(code, value);
        }
        edit.apply();
    }

    //------------------------------------------------------------------------------------------------------------------

    public static String getLanguage(Context ctx) {
        String language = load(LANGUAGE);

        if (TextUtils.isEmpty(language)) {
            language = LocaleHelper.getLocale(ctx.getResources()).getLanguage();
            return LANGUAGE_RU.equals(language) || LANGUAGE_EN.equals(language) ? language : LANGUAGE_EN;
        }
        return language;
    }

    public static void setLanguage(String language) {
        if (!LANGUAGE_RU.equals(language) && !LANGUAGE_EN.equals(language)) language = LANGUAGE_EN;
        save(LANGUAGE, language);
        MoldApplication.changeLanguageCode();
    }
}
