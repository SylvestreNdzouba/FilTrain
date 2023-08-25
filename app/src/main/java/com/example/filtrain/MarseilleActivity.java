package com.example.filtrain;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MarseilleActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<String>train_arrivee_Direction,train_arrivee_Type,train_arrivee_Nombre, train_arrivee_Date;
    public static ArrayList<String>train_depart_Direction, train_depart_Type, train_depart_Nombre, train_depart_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        train_arrivee_Direction=new ArrayList<>();
        train_arrivee_Type=new ArrayList<>();
        train_arrivee_Nombre=new ArrayList<>();
        train_arrivee_Date=new ArrayList<>();
        train_depart_Nombre=new ArrayList<>();
        train_depart_Type=new ArrayList<>();
        train_depart_Direction=new ArrayList<>();
        train_depart_Date=new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage);

        try {
            JSONArray arrivals=stringToJsonObject(retrieveArrivals()).getJSONArray("arrivals");
            JSONArray departures= stringToJsonObject(retrieveDepartures()).getJSONArray("departures");
            for (int i=0;i<arrivals.length();i++){
                JSONObject train=arrivals.getJSONObject(i).getJSONObject("display_informations");
                train_arrivee_Direction.add(train.getString("direction"));
                train_arrivee_Type.add(train.getString("physical_mode"));
                train_arrivee_Nombre.add(train.getString("headsign"));
                System.out.println("Direction :"+train.getString("direction"));
                System.out.println("Type de train :"+train.getString("physical_mode"));
                System.out.println("Numéro du train :"+train.getString("headsign"));

                JSONObject arrival_time=arrivals.getJSONObject(i).getJSONObject("stop_date_time");
                String arrival_time_input = arrival_time.getString("arrival_date_time");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM\nHH:mm");
                try {
                    Date date = inputFormat.parse(arrival_time_input);
                    train_arrivee_Date.add(outputFormat.format(date));
                    System.out.println(outputFormat.format(date));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i <departures.length() ; i++) {

                JSONObject train=departures.getJSONObject(i).getJSONObject("display_informations");
                train_depart_Type.add(train.getString("physical_mode"));
                train_depart_Nombre.add(train.getString("headsign"));
                train_depart_Direction.add(train.getString("direction"));
                System.out.println("Direction :"+train.getString("direction"));
                System.out.println("Type de train :"+train.getString("physical_mode"));
                System.out.println("Numéro du train :"+train.getString("headsign"));

                JSONObject departure_time=departures.getJSONObject(i).getJSONObject("stop_date_time");
                String departure_time_input = departure_time.getString("departure_date_time");
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM\nHH:mm");
                try {
                    Date date = inputFormat.parse(departure_time_input);
                    train_depart_Date.add(outputFormat.format(date));
                    System.out.println(outputFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabLayoutAdapter.addFragment(new MarseilleDepartFragment(), "Départs");
        tabLayoutAdapter.addFragment(new MarseilleArriveeFragment(), "Arrivées");
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baselin_logout);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_login);
    }
    public static JSONObject stringToJsonObject(String str) {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
    public String generateTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.now().toString().replace(":", "").replace("-", "").split("\\.")[0];
        }
        return "";
    }
    public String retrieveDepartures(){
        ArrayList<String> lines = new ArrayList<String>();
        String url_str = "https://api.sncf.com/v1/coverage/sncf/coords/5.380407;43.302666/departures?datetime="+generateTime();
        System.out.println(url_str);
        String final_str="";
        try {
            URL url = new URL(url_str);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "f7f5c759-1974-42a9-8a43-58bb45126c6c");

            InputStream res = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(res));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                lines.add(inputLine);
            }
            for (String string : lines) {
                final_str+=string;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return final_str;
    }
    public String retrieveArrivals(){
        ArrayList<String> lines = new ArrayList<String>();
        String url_str = "https://api.sncf.com/v1/coverage/sncf/coords/5.380407;43.302666/arrivals?datetime="+generateTime();

        System.out.println(url_str);
        String final_str="";
        try {
            URL url = new URL(url_str);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "f7f5c759-1974-42a9-8a43-58bb45126c6c");

            InputStream res = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(res));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                lines.add(inputLine);
            }
            for (String string : lines) {
                final_str+=string;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return final_str;
    }


}