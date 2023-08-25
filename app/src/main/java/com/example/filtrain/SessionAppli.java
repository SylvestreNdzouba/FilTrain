package com.example.filtrain;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionAppli extends Application {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Constructeur

    public SessionAppli() {
    }

    public SessionAppli(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // Démarre une session avec le nom d'utilisateur de l'utilisateur
    public void startSession(String usereEmail) {
        editor.putString("email", usereEmail);
        editor.commit();
    }

    // Retourne vrai si une session est en cours
    public boolean isSessionStarted() {
        return pref.getString("email", null) != null;
    }

    // Ferme la session de l'utilisateur
    public void endSession() {
        editor.clear();
        editor.commit();
    }

    public String getUsername() {
        return pref.getString("nom", null);
    }

    public String getUserEmail(){
        return pref.getString("email", null);
    }
    public String getUserPrenom(){
        return pref.getString("prenom", null);
    }
}
