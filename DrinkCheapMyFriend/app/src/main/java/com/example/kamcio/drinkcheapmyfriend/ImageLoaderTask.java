package com.example.kamcio.drinkcheapmyfriend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;


/**
 * Created by kamisiuu on 16.04.17. Denne Async Tasken bruker jeg ikke men den lagde jeg bare for å vise at jeg kan lage , jeg brukte Tråden til å laste opp bilde siden den brukes heller
 * til mer krevende arbeid
 */

class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public ImageLoaderTask(ImageView imageView) {
        this.imageView = imageView;

    }

    protected Bitmap doInBackground(String... urls) {
        String imageURL = urls[0];
        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Bildeopplasting feilet", e.getMessage());
            e.printStackTrace();
        }
        return bimage;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}


