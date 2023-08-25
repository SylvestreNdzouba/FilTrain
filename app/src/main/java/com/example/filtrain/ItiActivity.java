package com.example.filtrain;

import static com.example.filtrain.MainActivity.heureDepart;
import static com.example.filtrain.MainActivity.dateDepart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.Date;

public class ItiActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static ArrayList<String> train_arrivee_Date;
    public static ArrayList<String> train_depart_Date;
    public static ArrayList<String> train_arrivee_heure;
    public static ArrayList<String> train_depart_heure;
    public TextView villeDes;
    public TextView villeDep;
    private String heureDep, dateDep;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        villeDes = (TextView) findViewById(R.id.villeDestination);
        villeDep = (TextView) findViewById(R.id.villeDepart);
        train_arrivee_Date=new ArrayList<>();
        train_depart_Date=new ArrayList<>();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itineraire);

        //Récuper les informations concernant l'itinéraire dans les listes
        try {
            JSONArray journey=stringToJsonObject(retrieveJourney()).getJSONArray("journeys");
            for (int i=0;i<journey.length();i++){
                JSONObject train=journey.getJSONObject(i).getJSONObject("sections");

                String arrival_time = train.getString("arrival_date_time");
                String departure_time = train.getString("departure_date_time");

                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM");
                SimpleDateFormat outputFormat1 = new SimpleDateFormat("HH:mm");
                try {
                    Date date1 = inputFormat.parse(departure_time);
                    train_depart_Date.add(outputFormat.format(date1));
                    Date date2 = inputFormat.parse(arrival_time);
                    train_arrivee_heure.add(outputFormat1.format(date2));
                    Date date3 = inputFormat.parse(departure_time);
                    train_depart_heure.add(outputFormat1.format(date3));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tabLayout = findViewById(R.id.tabLayout1);
        viewPager = findViewById(R.id.viewPager1);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tabLayoutAdapter.addFragment(new ItiFragment(), "Itinéraires");
        viewPager.setAdapter(tabLayoutAdapter);

    }
    //méthode pour récupérer le JSON du String entrée en paramètre
    public static JSONObject stringToJsonObject(String str) {
        JSONObject json = new JSONObject();
        try {
            json = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String retrieveJourney() throws ParseException {
        //transformation de la date et heure au format de l'url
        heureDep = heureDepart;
        dateDep = dateDepart;
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat1 = new SimpleDateFormat("HHmm00");
        SimpleDateFormat outputFormat2 = new SimpleDateFormat("yyyyMMdd'T'");
        Date heure = inputFormat1.parse(heureDep);
        heureDep = outputFormat1.format(heure);
        Date date = inputFormat2.parse(dateDep);
        dateDep = outputFormat2.format(date);
        ArrayList<String> lines = new ArrayList<String>();
        //Cette url ne contient pas la date et l'heure ni les coordonnées des villes entrées par l'utilisateur
        // car pour une raison inconnu les listes dans la méthode onCreate ressortent vident
        //Pour tester l'url ci-dessous a été envoyé lors de la demande de clé d'accès
        //L'url fonctionnelle aurait du ressembler à ceci :
        // "https://api.navitia.io/v1/coverage/sandbox/journeys?from="+coord1Ville1+";"+coord2Ville1+"&to="+coord1Ville2+";"+coord2Ville2"&datetime="+dateDep+""+heureDep;
        String url_str = "https://api.navitia.io/v1/coverage/sandbox/journeys?from=2.3749036;48.8467927&to=2.2922926;48.8583736";
        System.out.println(url_str);
        String final_str="";
        //vérification clé d'accès API
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