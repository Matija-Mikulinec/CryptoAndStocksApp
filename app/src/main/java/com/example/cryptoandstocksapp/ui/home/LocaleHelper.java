package com.example.cryptoandstocksapp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class LocaleHelper {
    private static final String PREFS_NAME = "settings";
    private static final String KEY_LANG = "app_language";

    public static void setLocale(Context ctx, String lang) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_LANG, lang).apply();
    }

    public static String getLocale(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_LANG, "en");
    }


    public static Context updateContext(Context ctx) {
        String lang = getLocale(ctx);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = ctx.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(locale);
            configuration.setLayoutDirection(locale);
            return ctx.createConfigurationContext(configuration);
        } else {
            configuration.locale = locale;
            ctx.getResources().updateConfiguration(configuration, ctx.getResources().getDisplayMetrics());
            return ctx;
        }
    }
}
