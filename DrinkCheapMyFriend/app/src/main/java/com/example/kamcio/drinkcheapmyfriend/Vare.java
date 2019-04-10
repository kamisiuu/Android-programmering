package com.example.kamcio.drinkcheapmyfriend;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by kamcio on 03.05.17. Denne klassen hjelper meg med å holde rede på produktene i listene , noe som også gjør det lettere å hente
 * bestemt data om hver produkt ved hjelp av gettere , denne klassen inneholder også metoder som hjelper meg med å sortere listene
 * disse metodene hjelper meg å finne mest alkohol for pengene
 */

class Vare extends ArrayList implements Comparable<Vare>{
    ArrayList<Vare> vareliste=new ArrayList<>();



    private String Varenavn, Image, Smak, Lukt, Land, Passertil01, Passertil02, Passertil03,Farge,Literpris;
    private double Pris, Volume;

    int Id,Alcohol;

    public int getproductId(){return Id;}
    public String getNavn(){
        return Varenavn;
    }

    public double getVolume() {
        return Volume;
    }

    public double getPris() {
        return Pris;
    }

    public String getImage() {return Image;}

    public String getSmak() {return Smak;}

    public String getLukt() {return Lukt;}

    public String getLand(){ return Land;}

    public int getAlcohol(){return Alcohol;}
    public String getPassertil01() {
        return Passertil01;
    }

    public String getPassertil02() {
        return Passertil02;
    }

    public String getPassertil03() {
        return Passertil03;
    }

    public String getFarge() {return Farge;}

    public String getLiterpris(){return Literpris;}

    public Vare(int Id,String Varenavn, double Pris,String Literpris, double Volume, String Image, String Smak, String Lukt, String Land, int Alcohol,String Passertil01,String Passertil02, String Passertil03,String Farge) {
        this.Id=Id;
        this.Varenavn = Varenavn;
        this.Pris = Pris;
        this.Volume = Volume;
        this.Image = Image;
        this.Smak = Smak;
        this.Lukt = Lukt;
        this.Land = Land;
        this.Alcohol = Alcohol;
        this.Passertil01=Passertil01;
        this.Passertil02=Passertil02;
        this.Passertil03=Passertil03;
        this.Farge=Farge;
        this.Literpris=Literpris;
    }









    public double index(){
        return Pris;
    }
    @Override
    public int compareTo(Vare o) {
        if(this.index() > o.index()){
            return 1;
        } else if(this.index() < o.index()){
            return -1;
        } else {
            return 0;
        }
    }
    public static Comparator<Vare> nameComparator = new Comparator<Vare>() {

        @Override

        public int compare(Vare jc1, Vare jc2) {

            return (int) (jc1.getNavn().compareTo(jc2.getNavn()));

        }

    };


}