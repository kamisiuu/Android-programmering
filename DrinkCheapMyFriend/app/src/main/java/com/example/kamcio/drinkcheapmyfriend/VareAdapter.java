package com.example.kamcio.drinkcheapmyfriend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kamcio on 03.05.17. Adapteren tar seg av å fylle ut listView , den går gjennom listen som den den får under skapingen av ny array, den itererer
 * gjennom listen og fyller neste produkt for hver posisjon.
 */

public class VareAdapter extends ArrayAdapter<Vare> {


    private static class ViewHolder {
        TextView id,navn,volum,pris,prosent;

    }

    public VareAdapter(Context context, ArrayList<Vare> products) {
        super(context, R.layout.activity_listview, products);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Vare vare = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
            viewHolder.id=(TextView) convertView.findViewById(R.id.id);
            viewHolder.navn = (TextView) convertView.findViewById(R.id.varenavn);
            viewHolder.volum = (TextView) convertView.findViewById(R.id.volum);
            viewHolder.pris = (TextView) convertView.findViewById(R.id.pris);
            viewHolder.prosent = (TextView) convertView.findViewById(R.id.prosent);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.id.setText(String.valueOf(vare.getproductId()));
        viewHolder.navn.setText(vare.getNavn());
        viewHolder.volum.setText(String.valueOf(vare.getVolume()));
        viewHolder.pris.setText(String.valueOf(vare.getPris()));
        viewHolder.prosent.setText(String.valueOf(vare.getAlcohol()));
        // Return the completed view to render on screen
        return convertView;
    }

}
