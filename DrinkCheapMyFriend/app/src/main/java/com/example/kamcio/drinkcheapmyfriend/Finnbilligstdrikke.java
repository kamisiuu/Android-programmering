package com.example.kamcio.drinkcheapmyfriend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


/**
 * Created by kamcio on 02.05.17. Denne klassen fyller ut produktlisten , den tar seg av søkingen i listen og sende parametere til neste klasse Alcohol2
 */

public class Finnbilligstdrikke extends Fragment implements AdapterView.OnItemClickListener,SearchView.OnQueryTextListener{
    OnAlcoholSelecteddL mCallback;

    public static ArrayList<Vare> productList=new ArrayList<Vare>();
    public static ArrayList<Vare> getProductList() {
        return productList;
    }
    public static ArrayList<Vare>unfiltered=new ArrayList<Vare>();

    public static ArrayList<Vare>NameUnSortedList;
    public static ArrayList<Vare> getUnfiltered() {
        return unfiltered;
    }
    public static ArrayList<Vare> filtered= new ArrayList<Vare>();
    public static ArrayList<Vare> getFiltered() {
        return filtered;
    }

    private SearchView mSearchView;
    private VareAdapter vareAdapter;

    ListView listView;

    FragmentActivity listener2;

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
         /*her kaller jeg på min egen konstuerte filter for tekstsøking */
        String text = mSearchView.getQuery().toString().toLowerCase(Locale.getDefault());
        filter(text);
        vareAdapter.notifyDataSetChanged();

        return true;

    }
    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setImeOptions( EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Søk her");
    }

    public interface OnAlcoholSelecteddL {
        // Dette interfacet er kalt av FinnBilligstdrikke når list item er valgt

        public void SelectedPosition(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // dette sørger for at container activity har impementert callback interface, hvis ikke kaster den et unntak

        try {
            mCallback = (Finnbilligstdrikke.OnAlcoholSelecteddL) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " Du må implementere OnAlcoholSelecteddL");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // definerer xml filen for fragment
        View view = inflater.inflate(R.layout.alcohol_list, parent, false);
        //filtered.clear() er viktig for å unngå duplikater etter søking i listen ved trykk tilbake knapp
        filtered.clear();
        return view;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.android_list);
        mSearchView = (SearchView) getView().findViewById(R.id.search_view);

        View mHeader = LayoutInflater.from(getContext()).inflate(R.layout.header, null);
        listView.addHeaderView(mHeader);/* her legger jeg til en header med meny som jeg selv har konstruert */
if(isNetworkAvailable()) {
    try { /* her forsøker jeg å koble meg til databasen som ligger på serveren min,
        JsonFileReader kobler seg til en php fil og php filen sender et JSON formatert array
        Collections.sort funksjonen sorterer listen med billigst alkohol per
        */

        JsonFileReader r = new JsonFileReader();
        productList = r.read();
        unfiltered.addAll(productList);
        Collections.sort(productList);
        Collections.sort(productList);
        filtered.addAll(productList);

    } catch (Exception e) { /* i denne catchen så fanges det unntak som for eks manglende internettilkobling,
         det vil skrives ut i loggen men også i form av en toast, og i dette untakket brukes det internt
          liggende data i appen*/
        Log.e("Ingen nettverk", e.getMessage());

        String text = "Ingen nettverk, koble til Internett ";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), text, duration);
        toast.show();
        e.printStackTrace();
        InputStream inputStream = getResources().openRawResource(R.raw.products);

        CSVFile sqldb = new CSVFile(inputStream);
        productList = sqldb.read();
        unfiltered.addAll(productList);
        Collections.sort(productList);
        NameUnSortedList = productList;
        filtered.addAll(productList);
    }
}else{
    String text = "Ingen nettverk, koble til Internett ";
    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(getContext(), text, duration);
    toast.show();

    InputStream inputStream = getResources().openRawResource(R.raw.products);

    CSVFile sqldb = new CSVFile(inputStream);
    productList = sqldb.read();
    unfiltered.addAll(productList);
    Collections.sort(productList);
    NameUnSortedList = productList;
    filtered.addAll(productList);
}



        vareAdapter= new VareAdapter(getActivity().getApplicationContext(),productList);
        Parcelable state = listView.onSaveInstanceState();





        listView.setAdapter(vareAdapter);
        listView.setTextFilterEnabled(true);
        setupSearchView();
        listView.onRestoreInstanceState(state);
        vareAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(this);




    }


    public void filter(String charText) {
        /*filter metoden hjelper meg å filtere teksten fra søkefeltet , den konverter den først til den lokale teksten ellers så kaster
 * den untakk om feil lokale verdier og tekstformat, så kjøres det et test som sjekker om det er noe tekst som har kommet opp
  * hvis tekstlengden er 0 så vil hele listen legges til filtrerte og ellers hvis den er større enn eller what so ever så
  * vil i forløkka det gås gjennom hver vare i filtrerte lista og hvis denne varen man ser på aktuelt har et navn som tilsvarer
  * verdiene i søkefeltet vil denne legges til i produktlista*/
        charText = charText.toLowerCase(Locale.getDefault());
        productList.clear();
        if (charText.length() == 0) {
            productList.addAll(filtered);
        } else {
            for (Vare wp : filtered) {
                if (wp.getNavn().toLowerCase(Locale.getDefault()).contains(charText)) {
                    productList.add(wp);
                }
            }
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener2 = null;
    }
    int clicked=0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*her har jeg løst på en lur måte et problem jeg sleit med i en stund, og det var nemlig etter at jeg filtrerte varene i søket
        * så fikk jeg jo en posisjon til produktene og den var jo ikke den samme som indeksene i arraylisten etter søket, derfor så
        * implementerte jeg Id inn i view som er usynlig for brukeren men jeg kunne lese den av for det og referere med den i arraylisten
        * for å finne riktig produkt jeg trykker på og få opp dataene til det riktige produktet man trykker på*/



        if(position>0) {
            String selected = ((TextView) view.findViewById(R.id.id)).getText().toString();
            int posisjon= Integer.parseInt(selected);
            mCallback.SelectedPosition(posisjon);
            //mSearchView.onActionViewCollapsed();

        }
    }




    private boolean isNetworkAvailable() {
           /*her har jeg en ubrukt metode som jeg forsøkte å bruke for åsjekke om det er internettilkobling før jeg
    * prøvde å koble meg til eksterne databasen men det viste seg at det var bedre å bruke try and catch enn dette for min del*/
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}