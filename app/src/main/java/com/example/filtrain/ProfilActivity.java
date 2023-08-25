package com.example.filtrain;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {
    TextView profilNom, profilPrenom, profilEmail, profilPassword;
    //TextView titleName, titleEmail;
    HelperClass hc ;
    SessionAppli sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_activity);
/*
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);
*/
        hc = new HelperClass(this);
        sessionManager = new SessionAppli(getApplicationContext());

        profilNom = findViewById(R.id.profilNom);
        profilPrenom = findViewById(R.id.profilPrenom);
        profilEmail = findViewById(R.id.profilEmail);
        profilPassword = findViewById(R.id.profilPassword);

        String username = sessionManager.getUsername();
        String userPrenom = sessionManager.getUserPrenom();
        String userEmail = sessionManager.getUserEmail();

        profilNom.setText(username);
        profilNom.setVisibility(View.VISIBLE);
        profilPrenom.setText(userPrenom);
        profilPrenom.setVisibility(View.VISIBLE);
        profilEmail.setText(userEmail);
        profilEmail.setVisibility(View.VISIBLE);

        //showUserData();
    }

    //méthode à supprimer
    public void showUserData(){
        Intent intent = getIntent();

        String nameUser = intent.getStringExtra("Nom");
        String prenomUser = intent.getStringExtra("Prénom");
        String emailUser = intent.getStringExtra("Email");
        String mdpUser = intent.getStringExtra("Mot de passe");

        profilNom.setText(nameUser);
        profilPrenom.setText(prenomUser);
        profilEmail.setText(emailUser);
        profilPassword.setText(mdpUser);
    }

}