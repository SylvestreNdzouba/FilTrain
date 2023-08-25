package com.example.filtrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.filtrain.databinding.ActivityLoginBinding;
//import com.example.filtrain.databinding.ActivitySignUp2Binding;
import com.example.filtrain.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    EditText loginEmail, loginMdp, loginNom, loginPrenom;
    Button loginBtn;
    TextView signupRedirectText;
    ActivityLoginBinding binding;
    HelperClass helper;
    SessionAppli sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);

        helper = new HelperClass(this);
        sessionManager = new SessionAppli(getApplicationContext());

        loginEmail = findViewById(R.id.login_email);
        loginMdp = findViewById(R.id.login_mdp);
        loginBtn = findViewById(R.id.loginBtn);
        signupRedirectText = findViewById(R.id.signupRedirect);
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
        //loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gestion avec realtime firebase
                String mail = loginEmail.getText().toString();
                String password = loginMdp.getText().toString();

                //gestion de la base
                /*if (!validateEmail() | !validatePassword()){

                } else {
                    checkUser();
                    Toast.makeText(LoginActivity.this, "Vous êtes connecté !",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,
                            MainActivity.class));
                }*/
                //SQLite
/*
                if (helper.verifConnexion(mail, password)) {

                    User currentUser = helper.getUserData(mail);
                    sessionManager.startSession(mail);

                    Toast.makeText(LoginActivity.this, "Bonjour " + mail + " !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Données entrées incorrectes",
                            Toast.LENGTH_SHORT).show();
                }
*/
                if (sessionManager.isSessionStarted()) {
                    Toast.makeText(LoginActivity.this, "Vous êtes déjà connecté !",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,
                            ProfilActivity.class));
                } else {
                    if (mail.equals("") || password.equals("")) {
                        Toast.makeText(LoginActivity.this, "* Remplissez tous les champs *",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkCredentials = helper.checkEmailPassword(mail, password);

                        if (checkCredentials == true){
                            Toast.makeText(LoginActivity.this, "Vous êtes connecté !",
                                    Toast.LENGTH_SHORT).show();

                            Toast.makeText(LoginActivity.this, "Bonjour " + mail + " !",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Mot de passe incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                //gestion avec authentification firebase
                /*String email = loginEmail.getText().toString().trim();
                String mdp = loginMdp.getText().toString().trim();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!mdp.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, mdp)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(LoginActivity.this, "Vous êtes connecté !",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this,
                                                MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(LoginActivity.this, "Connexion échouée ! Réessayez",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        loginMdp.setError("Renseignez votre mot de passe");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Renseignez une adresse mail");
                } else {
                    loginEmail.setError("Renseignez un mail valide");
                }*/
            }
        });
/*
        binding.signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });*/
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,
                        SignUpActivity.class));
            }
        });

    }

    public Boolean validateEmail(){
        String val = loginEmail.getText().toString();
        if (val.isEmpty()){
            loginEmail.setError("Renseignez un email");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = loginMdp.getText().toString();
        if (val.isEmpty()){
            loginMdp.setError("Renseignez votre mot de passe");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String userEmail = loginEmail.getText().toString().trim();
        String userMdp = loginMdp.getText().toString().trim();
        String userNom = loginNom.getText().toString().trim();
        String userPrenom = loginPrenom.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("email").equalTo(userEmail);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    loginEmail.setError(null);
                    /*String mdpFromDB = snapshot.child(userEmail).child("Mot de passe").
                            getValue(String.class);*/
                    String mdpFromDB = snapshot.child(userMdp).child("password").
                            getValue(String.class);

                    if (mdpFromDB.equals(userMdp)) {

                        String nomFromDB = snapshot.child(userNom).child("nom").
                                getValue(String.class);
                        String prenomFromDB = snapshot.child(userPrenom).child("prenom").
                                getValue(String.class);
                        String emailFromDB = snapshot.child(userEmail).child("email").
                                getValue(String.class);

                        loginEmail.setError(null);
                        //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Intent intent = new Intent(LoginActivity.this, ProfilActivity.class);

                        intent.putExtra("Nom", nomFromDB);
                        intent.putExtra("Prénom", prenomFromDB);
                        intent.putExtra("Email", emailFromDB);
                        intent.putExtra("Mot de passe", mdpFromDB);

                        startActivity(intent);
                    } else {
                        loginMdp.setError("Mot de passe invalide");
                    }
                } else {
                    loginEmail.setError("Utilisateur inconnu");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}