package com.example.filtrain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class ProPassageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private AutoCompleteTextView auto;
    ArrayList<String> gareListe = new ArrayList<>();
    static String ville;
    static double latitude;
    static double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propassage);
        btn = (Button) findViewById(R.id.passage_btn);
        btn.setOnClickListener(this);
        auto = (AutoCompleteTextView) findViewById(R.id.villedt);


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
        auto.setAdapter(adapter);

    }

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
        ville = auto.getText().toString();
        try {
            JSONObject obj = new JSONObject(loadJSONFile());
            JSONArray jsArray = obj.getJSONArray("records");

            for (int i = 0; i < jsArray.length(); i++) {
                JSONObject jsObj = jsArray.getJSONObject(i).getJSONObject("fields");
                String gareValue = jsObj.getString("gare_alias_libelle_noncontraint");
                if(ville == gareValue){
                    double latitude1 = jsObj.getDouble("latitude_entreeprincipale_wgs84");
                    double longitude1 = jsObj.getDouble("longitude_entreeprincipale_wgs84");
                    latitude = latitude1;
                    longitude = longitude1;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent i = new Intent(ProPassageActivity.this, PassageActivity.class);
        i.putExtra("Latitude", latitude);
        i.putExtra("Longitude", longitude);
        startActivity(i);
    }


}
