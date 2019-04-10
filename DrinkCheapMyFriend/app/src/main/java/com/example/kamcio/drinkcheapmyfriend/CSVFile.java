package com.example.kamcio.drinkcheapmyfriend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by kamisiuu on 11.03.17. Denne klassen, hjelper meg med å lese CSV filer som er lagret internt. Konstruktøren tar imot en
 * inputstream og ved hjelp av metoden jeg laget kan det returneres en liste med avleste data som blir splittet under avlesingen og lagret en og en i lista.
 */

public class CSVFile {

    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public ArrayList<Vare> read(){
        ArrayList<Vare> resultList = new ArrayList<Vare>();

        BufferedReader reader = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
        }else {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] row = csvLine.split(";");
                String id= row[0];
                int Id= Integer.parseInt(id);
                String Pris=row[4].replace(",", ".");
                double pris = Double.parseDouble(Pris);
                String Volume=row[3].replace(",",".");
                double volume= Double.parseDouble(Volume);

                resultList.add(new Vare(Id,row[2],pris,row[5],volume,row[35],row[16],row[15],row[20],Integer.parseInt(row[26]),row[17],row[18],row[19],row[14]));

            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }

        return resultList;
    }
}
