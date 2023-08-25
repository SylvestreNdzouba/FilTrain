package com.example.filtrain;

import static com.example.filtrain.MainActivity.lArrivee;
import static com.example.filtrain.MainActivity.leDepart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ItiFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Current view
        View view = inflater.inflate(R.layout.fragment_itineraire, container,false);
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> train_date_depart = ItiActivity.train_depart_Date;
        ArrayList<String> train_heure_arrivee = ItiActivity.train_arrivee_heure;
        ArrayList<String> train_heure_depart = ItiActivity.train_depart_heure;
        String ville_depart = leDepart;
        String ville_destination = lArrivee;


        int[] heure = new int[] {R.id.horaireTrajet1, R.id.horaireTrajet2, R.id.horaireTrajet3, R.id.horaireTrajet4, R.id.horaireTrajet5, R.id.horaireTrajet6, R.id.horaireTrajet7, R.id.horaireTrajet8, R.id.horaireTrajet9, R.id.horaireTrajet10};

        int[] date = new int[] {R.id.dateTrajet1, R.id.dateTrajet2, R.id.dateTrajet3, R.id.dateTrajet4, R.id.dateTrajet5, R.id.dateTrajet6, R.id.dateTrajet7, R.id.dateTrajet8, R.id.dateTrajet9, R.id.dateTrajet10};

        int[] lieuDepart = new int[] {R.id.lieuDepart1, R.id.lieuDepart2, R.id.lieuDepart3, R.id.lieuDepart4, R.id.lieuDepart5, R.id.lieuDepart6, R.id.lieuDepart7, R.id.lieuDepart8, R.id.lieuDepart9, R.id.lieuDepart10};

        int[] lieuArrivee = new int[] {R.id.lieuDestination1, R.id.lieuDestination2, R.id.lieuDestination3, R.id.lieuDestination4, R.id.lieuDestination5, R.id.lieuDestination6, R.id.lieuDestination7, R.id.lieuDestination8, R.id.lieuDestination9, R.id.lieuDestination10};

        //cette boucle affiche tour à tour les intinéraires
        for(int i=0; i<10; i++) {
            //Cette deux TextView sont en commentaires car ils ressortent vident ce provoque une erreur de fonctionnement
            //Pour tester l'appli il a fallu les retirer
            /*TextView train_heure = view.findViewById(heure[i]);
            train_heure.setText(train_heure_depart.get(i) +" - "+train_heure_arrivee);

            TextView train_date = view.findViewById(date[i]);
            train_date.setText(String.valueOf(train_date_depart.get(i)));*/

            TextView lieuDep = view.findViewById(lieuDepart[i]);
            lieuDep.setText("Lieu de départ : "+ville_depart);

            TextView lieuDes = view.findViewById(lieuArrivee[i]);
            lieuDes.setText("Lieu de destination : "+ville_destination);

        }

        TextView villeDep = view.findViewById(R.id.villeDepart);
        villeDep.setText(ville_depart);

        TextView villeDes = view.findViewById(R.id.villeDestination);
        villeDes.setText(ville_destination);


        return view;
    }


}