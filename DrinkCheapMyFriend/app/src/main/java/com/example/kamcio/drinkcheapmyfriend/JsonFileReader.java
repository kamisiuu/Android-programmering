package com.example.kamcio.drinkcheapmyfriend;

/**
 * Created by kamcio on 03.05.17. Denne klassen er ansvarlig for å lese av data fra en ekstern database og til slutt returnere
 * dataene i form av en liste, her brukes også eksterne bibliotek for tolking av JSON som blir sendt av phpfilen.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class JsonFileReader extends Thread{

    public ArrayList<Vare> read() {
        ArrayList<Vare> resultList = new ArrayList<>();
        String result = null;

        // convert response to string
        try {
            String username = "drinkcheap";
            String password = "drinkcheap";
            String link = "http://78.31.104.87/alcohollist.php";
            URL url = new URL(link);
            String data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            result = sb.toString();
            reader.close();
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading file: " + ex);
        }

        try {
            JSONArray jArray = new JSONArray(result);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                int Id=Integer.parseInt(String.valueOf(json_data.getString("Id")));
                String Varenavn = String.valueOf(json_data.getString("Varenavn"));
                double Pris = Double.parseDouble(String.valueOf(json_data.getString("Pris")).replace(",", "."));
                String Literpris = String.valueOf(json_data.getString("Literpris"));
                double Volume = Double.parseDouble(String.valueOf(json_data.getString("Volum")).replace(",", "."));
                int Alcohol = Integer.parseInt(String.valueOf(json_data.getString("Alkohol")).replace(",", "."));
                String Farge = String.valueOf(json_data.getString("Farge"));
                String Smak = String.valueOf(json_data.getString("Smak"));
                String Lukt = String.valueOf(json_data.get("Lukt"));
                String Land = String.valueOf(json_data.get("Land"));
                String Image = String.valueOf(json_data.getString("Img"));
                String Passertil01 = String.valueOf(json_data.getString("Passertil01"));
                String Passertil02 = String.valueOf(json_data.getString("Passertil02"));
                String Passertil03 = String.valueOf(json_data.getString("Passertil03"));
                //String [] row = new String[]{Varenavn,Pris,Volume,Image,Smak,Lukt,Land,Alcohol};

                resultList.add(new Vare(Id, Varenavn, Pris,Literpris, Volume, Image, Smak, Lukt, Land, Alcohol,Passertil01,Passertil02,Passertil03,Farge));
            }

        } catch (JSONException e) {
            throw new RuntimeException("Error in parsing data: " + e);
        }

        // try parse json
        return resultList;
    }
}
