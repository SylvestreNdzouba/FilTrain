package com.example.filtrain;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QuimperArriveeFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Current view
        View view = inflater.inflate(R.layout.fragment_arrivees, container,false);
        super.onViewCreated(view, savedInstanceState);

        Date currentDate = Calendar.getInstance().getTime();
        String nomVille = ProchainPassageActivity.recupVille;

        // Current date
        SimpleDateFormat dfDate = new SimpleDateFormat("dd MMM yyyy", Locale.FRANCE);
        String formattedDate = dfDate.format(currentDate);
        TextView textViewDate = view.findViewById(R.id.text_date_depart);
        textViewDate.setText(nomVille+" le " + formattedDate);

        ArrayList<String> train_date_list = QuimperActivity.train_arrivee_Date;
        ArrayList<String> train_direction_list = QuimperActivity.train_arrivee_Direction;
        ArrayList<String> train_type_list = QuimperActivity.train_arrivee_Type;
        ArrayList<String> train_number_list = QuimperActivity.train_arrivee_Nombre;

        int[] heure = new int[] {R.id.heure_arrivee1, R.id.heure_arrivee2, R.id.heure_arrivee3, R.id.heure_arrivee4, R.id.heure_arrivee5, R.id.heure_arrivee6, R.id.heure_arrivee7, R.id.heure_arrivee8, R.id.heure_arrivee9, R.id.heure_arrivee10};

        int[] direction = new int[] {R.id.text_ville1, R.id.text_ville2, R.id.text_ville3, R.id.text_ville4, R.id.text_ville5, R.id.text_ville6, R.id.text_ville7, R.id.text_ville8, R.id.text_ville9, R.id.text_ville10};

        int[] type = new int[] {R.id.text_typeTrain1, R.id.text_typeTrain2, R.id.text_typeTrain3, R.id.text_typeTrain4, R.id.text_typeTrain5, R.id.text_typeTrain6, R.id.text_typeTrain7, R.id.text_typeTrain8, R.id.text_typeTrain9, R.id.text_typeTrain10};

        int[] numero = new int[] {R.id.text_numero1, R.id.text_numero2, R.id.text_numero3, R.id.text_numero4, R.id.text_numero5, R.id.text_numero6, R.id.text_numero7, R.id.text_numero8, R.id.text_numero9, R.id.text_numero10};

        for(int i=0; i<10; i++) {
            TextView train_heure = view.findViewById(heure[i]);
            train_heure.setText(String.valueOf(train_date_list.get(i)));

            TextView train_direction = view.findViewById(direction[i]);
            train_direction.setText(String.valueOf(train_direction_list.get(i)));

            TextView train_type = view.findViewById(type[i]);
            train_type.setText(train_type_list.get(i) + " [" + train_number_list.get(i) + "]");

            TextView train_numero = view.findViewById(numero[i]);
            train_numero.setText(" [" + train_number_list.get(i) + "]");
        }

        return view;
    }

}