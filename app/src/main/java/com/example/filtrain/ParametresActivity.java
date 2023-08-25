package com.example.filtrain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ParametresActivity extends AppCompatActivity {

    TextView tLangue;
    CardView cardView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametres_activity);
/*
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);
*/
        //click sur le textview ds parametres
        tLangue = (TextView) findViewById(R.id.tLangue);
        tLangue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ParametresActivity.this, LanguesActivity.class);
                startActivity(intent);
            }
        });

        //click sur le cardview
        cardView = findViewById(R.id.cLangues);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ParametresActivity.this, LanguesActivity.class);
                startActivity(intent);
            }
        });
    }
}