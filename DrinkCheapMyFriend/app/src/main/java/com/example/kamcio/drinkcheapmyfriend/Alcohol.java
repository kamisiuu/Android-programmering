package com.example.kamcio.drinkcheapmyfriend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kamcio 20.03.17. Denne klassen brukes for å lage siste aktiviteten i AlcoholList knappen,
 * den tar imot parametere sendt fra Finnbilligstdrikke som hjelper å lokalisere riktig valg av produktet og samtidig
 * viser den resten av informasjonen om det utvalgte produktet
 */

public class Alcohol extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        View view = inflater.inflate(R.layout.alcohol_view, container, false);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if(args !=null){
            updateAlcohol(args.getInt(ARG_POSITION));
        }else if(mCurrentPosition != -1){
            updateAlcohol(mCurrentPosition);
        }
    }

    public void updateAlcohol(int position){


        ArrayList<Vare> data  = AlcoholList.getFiltered();


        ImageView img = (ImageView) getActivity().findViewById(R.id.imageView);

        try {
            // denne testen sjekker om stringen fra listen er ikke tom og hvis den ikke er så kjøres det et tråd som dekoder stringen og henter bildet fra nettet
            if (!data.get(position - 1).getImage().isEmpty()){
                new ImageLoaderThread(img).run(data.get(position - 1).getImage());
            //new ImageLoaderTask(img).execute(data.get(position-1).getImage());
        }

        }catch (Exception e){

            Log.e("bruker standard bilde", e.getMessage());
            img.setImageResource(R.drawable.bottle);
            e.printStackTrace();
        }


        TextView tx1=(TextView) getActivity().findViewById(R.id.alcohol_name);
        TextView tx2=(TextView) getActivity().findViewById(R.id.alcohol_price);
        TextView tx3=(TextView) getActivity().findViewById(R.id.alcohol_volume);
        TextView tx4=(TextView) getActivity().findViewById(R.id.alcohol_smak);
        TextView tx5=(TextView) getActivity().findViewById(R.id.alcohol_lukt);
        TextView tx6=(TextView) getActivity().findViewById(R.id.alcohol_land);
        TextView tx7= (TextView) getActivity().findViewById(R.id.alcohol_prosent);
        TextView tx8 = (TextView) getActivity().findViewById(R.id.alcohol_passertil);
        TextView tx9 = (TextView) getActivity().findViewById(R.id.alcohol_farge);
        TextView tx10 = (TextView) getActivity().findViewById(R.id.alcohol_literprice);
        tx1.setText(data.get(position-1).getNavn());
        tx2.setText("Pris: "+data.get(position-1).getPris());
        tx3.setText("Volum: "+data.get(position-1).getVolume());
        tx4.setText("Smak: "+data.get(position-1).getSmak());
        tx5.setText("Lukt: "+data.get(position-1).getLukt());
        tx6.setText("Land: "+data.get(position-1).getLand());
        tx7.setText("Prosent: "+data.get(position-1).getAlcohol());
        tx9.setText("Farge: "+data.get(position-1).getFarge());
        tx10.setText("Literpris: "+data.get(position-1).getLiterpris());
        if((data.get(position-1).getPassertil01()).isEmpty()&&data.get(position-1).getPassertil02().isEmpty()){
            tx8.setVisibility(View.GONE);
        }else {
            tx8.setText("Passer til: " + data.get(position - 1).getPassertil01() + " " + data.get(position - 1).getPassertil02());
        }
        mCurrentPosition=position;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Lagring av nåværende seleksjonen i tilfelle we trenger å gjenopprette fragmenten
        outState.putInt(ARG_POSITION,mCurrentPosition);
    }
}