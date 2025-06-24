package com.example.cryptoandstocksapp.util;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Build;

import java.util.Locale;

public class MyContextWrapper extends ContextWrapper {

    public MyContextWrapper(Context base) {
        super(base);
    }

    public static ContextWrapper wrap(Context context, String language) {
        Configuration config = context.getResources().getConfiguration();
        Locale sysLocale = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                ? config.getLocales().get(0)
                : config.locale;

        if (!language.isEmpty() && !sysLocale.getLanguage().equals(language)) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(locale);
                config.setLayoutDirection(locale);
            } else {
                config.locale = locale;
                config.setLayoutDirection(locale);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                context = context.createConfigurationContext(config);
            } else {
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }
        }
        return new MyContextWrapper(context);
    }
}
