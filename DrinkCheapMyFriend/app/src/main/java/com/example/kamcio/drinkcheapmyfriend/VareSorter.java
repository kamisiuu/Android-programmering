package com.example.kamcio.drinkcheapmyfriend;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kamcio on 07.05.17.
 */

public class VareSorter {


    ArrayList<Vare> vareListe = new ArrayList<>();


   public VareSorter(ArrayList<Vare>vareliste){
       this.vareListe=vareliste;
   }

    public ArrayList<Vare> getSortedByName() {

        Collections.sort(vareListe, Vare.nameComparator);

        return vareListe;

    }

}
