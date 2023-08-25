package com.example.filtrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.os.LocaleListCompat;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class LanguesActivity extends AppCompatActivity {

    CardView cFrench, cEnglish, cSpanish;
    TextView french, english, spanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.langues_activity);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);

        cFrench = findViewById(R.id.cFrench);
        cEnglish = findViewById(R.id.cEnglish);
        cSpanish = findViewById(R.id.cSpanish);

        cFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("fr");
                AppCompatDelegate.setApplicationLocales(appLocale);
                recreate();
                //changeLocaleTo("fr");
                //recreate();
            }
        });

        cEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("en");
                AppCompatDelegate.setApplicationLocales(appLocale);
                onLocalesChanged(appLocale);
                recreate();
            }
        });

        cSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es");
                //onLocalesChanged(appLocale);
                AppCompatDelegate.setApplicationLocales(appLocale);
                recreate();
            }
        });
        spanish = findViewById(R.id.spanish);
        spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("es");
                //onLocalesChanged(appLocale);
                AppCompatDelegate.setApplicationLocales(appLocale);
                recreate();
            }
        });

    }

    public void changeLocaleTo(String langue){
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langue));
        Locale locale = new Locale(langue);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", langue);
        editor.apply();
    }

    @Override
    protected void onLocalesChanged(@NonNull LocaleListCompat locales) {
        super.onLocalesChanged(locales);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(String.valueOf(locales)));
    }
}