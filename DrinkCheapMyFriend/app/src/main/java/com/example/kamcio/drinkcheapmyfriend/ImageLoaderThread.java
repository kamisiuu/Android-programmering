package com.example.kamcio.drinkcheapmyfriend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by kamcio on 18.04.17. Denne tråden laster ned bilde for produktet og brukes
 */

public class ImageLoaderThread extends Thread {
    ImageView imageView;
    public ImageLoaderThread(ImageView imageView) {
        this.imageView = imageView;
    }
    public void run(String imageURL){

        Bitmap bimage = null;
        try {
            InputStream in = new java.net.URL(imageURL).openStream();
            bimage = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Failed Loading Image", e.getMessage());

            e.printStackTrace();
        }
        final Bitmap finalBimage = bimage;
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(finalBimage);
            }
        });

    }
}
