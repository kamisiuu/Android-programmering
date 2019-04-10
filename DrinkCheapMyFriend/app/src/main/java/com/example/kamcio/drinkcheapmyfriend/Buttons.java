package com.example.kamcio.drinkcheapmyfriend;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by kamcio on 12.02.17.
 */
public class Buttons extends Fragment implements View.OnClickListener  {
    private static final String TAG_FRAGMENT = "a" ;
    private static final String TAG_FRAGMENT2 = "b" ;
    private View mView;
    private EditText edttxt_projectname;
    private ImageButton button_submit;
    private ImageButton button_submit2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.buttons_activity, container,false);
        edttxt_projectname=(EditText)mView.findViewById(R.id.text);
        button_submit=(ImageButton)mView.findViewById(R.id.button1);
        button_submit.setOnClickListener(this);
        button_submit2=(ImageButton)mView.findViewById(R.id.button2);
        button_submit2.setOnClickListener(this);
        return mView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.button2:
                Finnbilligstdrikke nextFrag2= new Finnbilligstdrikke();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container,nextFrag2,TAG_FRAGMENT)
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.button1:
                AlcoholList nextFrag = new AlcoholList();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag,TAG_FRAGMENT2)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;

        }

    }
}
