package com.example.sokomo.client_example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sokomo on 13/03/17.
 */
public class CameraAsyncTask extends AsyncTask <Void ,Void ,Bitmap> {
    private ImageView image;
    private String ipAdress;
    private Context context;

    public CameraAsyncTask(Context activtyContext, ImageView src, String ip){
        image = src;
        ipAdress = ip;
        context = activtyContext;
    }


    @Override
    protected Bitmap doInBackground(Void... voids) {
        System.out.println(ipAdress);
        try {
            URL url = new URL(ipAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.getResponseMessage();

            return BitmapFactory.decodeStream(conn.getInputStream());

        } catch (IOException e) {
            return null;
        }
    }



    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        System.out.println(bitmap);
        image.setImageDrawable(new BitmapDrawable(context.getResources(),bitmap));
    }
}
