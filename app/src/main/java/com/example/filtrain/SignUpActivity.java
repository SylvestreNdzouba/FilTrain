package com.example.filtrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filtrain.databinding.ActivitySignUp2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
/*
    private FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    private EditText signupNom, signupPrenom, signupEmail, signupMdp;
    private Button signupBtn;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        auth = FirebaseAuth.getInstance();

        signupNom = findViewById(R.id.signupNom);
        signupPrenom = findViewById(R.id.signupPrenom);
        signupEmail = findViewById(R.id.signUp_email);
        signupMdp = findViewById(R.id.signUp_mdp);
        signupBtn = findViewById(R.id.signUp_btn);
        loginRedirectText = findViewById(R.id.loginRedirect);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String nom = signupNom.getText().toString().trim();
                String prenom = signupPrenom.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String mdp = signupMdp.getText().toString().trim();

                HelperClass helperClass = new HelperClass(nom, prenom, email, mdp);
                reference.child(nom).setValue(helperClass);

                Toast.makeText(SignUpActivity.this, "Vous êtes inscrit !",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                //gestion de l'authentification
                //String email = signupEmail.getText().toString().trim();
                //String mdp = signupMdp.getText().toString().trim();

                if (email.isEmpty()){
                    signupEmail.setError("Renseignez une adresse mail");
                }
                if (mdp.isEmpty()){
                    signupMdp.setError("Créer un mot de passe");
                } else {
                    auth.createUserWithEmailAndPassword(email, mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Vous êtes inscrit !",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, "Inscription échouée" +
                                        task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }*/

    ActivitySignUp2Binding binding;
    HelperClass helper;

    EditText signupNom, signupPrenom, signupEmail, signupMdp;
    Button signupBtn;
    TextView loginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sign_up2);
        binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#410392"));
        actionBar.setBackgroundDrawable(colorDrawable);

        helper = new HelperClass(this);

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String nom = binding.signupNom.getText().toString();
                //String prenom = binding.signupPrenom.getText().toString();
                //String email = binding.signUpEmail.getText().toString();
                //String password = binding.signUpMdp.getText().toString();

                add(view);

                /*

                if (nom.equals("") || prenom.equals("") || email.equals("") || password.equals("")) {
                    Toast.makeText(SignUpActivity.this, "* Remplissez tous les champs *",
                            Toast.LENGTH_SHORT).show();
                } else {
                    if (!nom.equals("") || !prenom.equals("") || !email.equals("") || !password.equals("")) {
                        Boolean checkUserEmail = helper.checkEmail(email);

                        if (checkUserEmail == false){
                            Boolean insert = helper.insertData(nom, prenom, email, password);

                            if (insert == true){
                                Toast.makeText(SignUpActivity.this, "Vous êtes inscrit !",
                                        Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, "Inscription échouée",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Cet utilisateur existe déjà, Connectez vous",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Mot de passe incorrect",
                                Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

        binding.loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
    public void add(View view) {
        signupNom = (EditText) findViewById(R.id.signupNom);
        signupPrenom = (EditText) findViewById(R.id.signupPrenom);
        signupEmail = (EditText) findViewById(R.id.signUp_email);
        signupNom = (EditText) findViewById(R.id.signupNom);
        signupMdp = (EditText) findViewById(R.id.signUp_mdp);
        String nom = signupNom.getText().toString();
        String prenom = signupPrenom.getText().toString();
        String email = signupEmail.getText().toString();
        String password = signupMdp.getText().toString();
        if (helper.verifUsername(email)) {
            Toast.makeText(SignUpActivity.this, "Cet utilisateur existe déjà, Connectez vous",
                    Toast.LENGTH_SHORT).show();
        } else {
            helper.insertData(nom, prenom, email, password);
            Toast.makeText(SignUpActivity.this, "Vous êtes inscrit !",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }


}