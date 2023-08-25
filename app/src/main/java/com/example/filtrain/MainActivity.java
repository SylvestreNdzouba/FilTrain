package com.example.filtrain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.os.LocaleListCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

//import com.example.filtrain.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    Intent intent;
    //LinearLayout menuLayout;
    EditText edtDate, edtTime;
    TextView textTitle;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    //ActivityMainBinding binding;
    Toolbar toolbar;
    NavController navController;
    DatePickerDialog datePickerDialog;
    Calendar c;
    private Button valider;
    private EditText date;
    public static String leDepart, lArrivee, dateDepart, heureDepart;
    ArrayList<String> gareListe = new ArrayList<>();
    private AutoCompleteTextView autoDepart, autoArrivee;
    private EditText startTime, startDate;
    DrawerLayout drawerLayout;
    CardView cFrench, cEnglish, cSpanish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home, R.id.connexion,R.id.inscription,R.id.profil,R.id.prochainPassage,
                R.id.deconnexion,R.id.comparateur,R.id.parametres)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#2D0462"));
        actionBar.setBackgroundDrawable(colorDrawable);

        //LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("fr");
        //AppCompatDelegate.setApplicationLocales(appLocale);

        // trouver comment récupérer les titres des pages
        /*
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController,
                @NonNull NavDestination destination, @Nullable Bundle bundle) {
                textTitle.setText(destination.getLabel());
            }
        });
         */

        valider = (Button) findViewById(R.id.search_buses);
        valider.setOnClickListener(this);
        date = (EditText) findViewById(R.id.date);

        autoDepart = (AutoCompleteTextView) findViewById(R.id.edtVilleDep);
        autoArrivee = (AutoCompleteTextView) findViewById(R.id.edtVilleAr);
        startDate = (EditText) findViewById(R.id.date);
        startTime = (EditText) findViewById(R.id.edtTime);



        //récupération des noms des gares pour les afficher en liste lors de saisie sur texte des villes
        try {
            JSONObject obj = new JSONObject(loadJSONFile());
            JSONArray jsArray = obj.getJSONArray("records");

            for (int i = 0; i < jsArray.length(); i++) {
                JSONObject jsObj = jsArray.getJSONObject(i).getJSONObject("fields");
                //String gareValue = jsObj.getString("gare_ut_libelle");
                String gareValue = jsObj.getString("gare_alias_libelle_noncontraint");
                //gareListe[i] = gareValue;
                gareListe.add(gareValue);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, gareListe);
        autoDepart.setAdapter(adapter);
        autoArrivee.setAdapter(adapter);


        // initiate the date picker and a button
        edtDate = (EditText) findViewById(R.id.date);
        // perform click event on edit text
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                edtDate.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        //  initiate the edit text
        edtTime = (EditText) findViewById(R.id.edtTime);
        // perform click event listener on edit text
        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Heure de départ");
                mTimePicker.show();

            }
        });

    }

    //Cette méthode charge le ficher JSON enregistré dans le dossier assets
    public String loadJSONFile() {
        String json = null;
        try {
            InputStream jsonFile = getAssets().open("gareA.json");
            int size = jsonFile.available();
            byte[] buffer = new byte[size];
            jsonFile.read(buffer);
            jsonFile.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onClick(View view) {
        leDepart = autoDepart.getText().toString();
        lArrivee = autoArrivee.getText().toString();
        dateDepart = startDate.getText().toString();
        heureDepart = startTime.getText().toString();

        Intent i = new Intent(MainActivity.this, ItiActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutUs:
                intent = new Intent(MainActivity.this, aboutUs.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void onStart() {
        super.onStart();
        Log.d("onStart", "onStart - Activité visible");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume - Intéractions");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause", "onPause - Intéractions plus possibles");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop","onStop - Activité plus visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy","onDestroy - Activité arrêtée et détruite");
    }

}