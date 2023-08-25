package com.example.filtrain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProchainPassageActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView tours, quimper, parisMont, bordeaux, lille, marseille, brest, nantes, rennes, lyon;
    public static String recupVille;
    private TextView nomTours, nomQuimper, nomParisMont, nomBordeaux, nomLille, nomMarseille, nomBrest, nomNantes, nomRennes, nomLyon;
    private TextView nomVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prochainpassage);

        tours = (CardView) findViewById(R.id.Tours);
        quimper = (CardView) findViewById(R.id.Quimper);
        parisMont = (CardView) findViewById(R.id.parisMontparnasse);
        bordeaux = (CardView) findViewById(R.id.Bordeaux);
        lille = (CardView) findViewById(R.id.Lille);
        marseille = (CardView) findViewById(R.id.Marseille);
        brest = (CardView) findViewById(R.id.Brest);
        nantes = (CardView) findViewById(R.id.Nantes);
        rennes = (CardView) findViewById(R.id.Rennes);
        lyon = (CardView) findViewById(R.id.Lyon);

        tours.setOnClickListener(this);
        quimper.setOnClickListener(this);
        parisMont.setOnClickListener(this);
        bordeaux.setOnClickListener(this);
        lille.setOnClickListener(this);
        marseille.setOnClickListener(this);
        brest.setOnClickListener(this);
        nantes.setOnClickListener(this);
        rennes.setOnClickListener(this);
        lyon.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.Tours:
                nomVille = (TextView) findViewById(R.id.textTours);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, ToursActivity.class);
                startActivity(i);
                break;

            case R.id.Quimper:
                nomVille = (TextView) findViewById(R.id.textQuimper);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, QuimperActivity.class);
                startActivity(i);
                break;

            case R.id.parisMontparnasse:
                nomVille = (TextView) findViewById(R.id.textParisMont);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, ParisMontActivity.class);
                startActivity(i);
                break;

            case R.id.Bordeaux:
                nomVille = (TextView) findViewById(R.id.textBordeaux);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, BordeauxActivity.class);
                startActivity(i);
                break;

            case R.id.Lille:
                nomVille = (TextView) findViewById(R.id.textLille);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, LilleActivity.class);
                startActivity(i);
                break;

            case R.id.Marseille:
                nomVille = (TextView) findViewById(R.id.textMarseille);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, MarseilleActivity.class);
                startActivity(i);
                break;

            case R.id.Brest:
                nomVille = (TextView) findViewById(R.id.textBrest);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, BrestActivity.class);
                startActivity(i);
                break;

            case R.id.Nantes:
                nomVille = (TextView) findViewById(R.id.textNantes);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, NantesActivity.class);
                startActivity(i);
                break;

            case R.id.Lyon:
                nomVille = (TextView) findViewById(R.id.textLyon);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, LyonActivity.class);
                startActivity(i);
                break;

            case R.id.Rennes:
                nomVille = (TextView) findViewById(R.id.textRennes);
                recupVille = nomVille.getText().toString();
                i = new Intent(ProchainPassageActivity.this, RennesActivity.class);
                startActivity(i);
                break;
        }


    }
}
