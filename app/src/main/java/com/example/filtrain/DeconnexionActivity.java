package com.example.filtrain;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.example.filtrain.manager.UserManager;

public class DeconnexionActivity extends AppCompatActivity {

    Button btnLogout;
    //UserManager userManager;
    SessionAppli sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deconnexion);

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);

        sessionManager = new SessionAppli(getApplicationContext());

        btnLogout = findViewById(R.id.logoutBtn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.endSession();
                finish();
                if (!sessionManager.isSessionStarted()){
                    Toast.makeText(DeconnexionActivity.this, "Vous êtes déconnecté !",
                            Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DeconnexionActivity.this, "Vous n'êtes pas connecté !",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }
        });
    }
}