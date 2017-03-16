package com.example.sokomo.client_example;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sokomo on 16/03/17.
 */
class RequestTaskAsync extends AsyncTask<String, Void, String> {

    Context AppliContext;
    ConnectivityManager cm;
    String RequestType;

    public RequestTaskAsync(Context context, ConnectivityManager appliCm, String type){
        AppliContext = context;
        cm = appliCm;
        RequestType = type;
    }

    public boolean isOnline() {
        /*
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        */
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        System.out.println("to send "+params[1]);

        if (!isOnline()){
            return "You do not have internet access";
        }

        String responseString;
        try {
            URL url = new URL(params[0]);
            System.out.println("Before call");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setRequestMethod("POST");
            conn.setRequestMethod(RequestType);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type","application/json");
            System.out.println("after call");


            JSONObject toSendData = new JSONObject();
            try {
                toSendData.accumulate("characters", params[1]);
                toSendData.accumulate("value", "1");
            } catch (JSONException e) {
                e.printStackTrace();
                return "Problem with the send of Json";
            }

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(toSendData.toString());
            writer.flush();

            if(conn.getResponseCode() == HttpsURLConnection.HTTP_CREATED){
                responseString = params[1] + " Send";
            }
            else {
                responseString = "Problem with the data send";
            }
        } catch (IOException e) {
            responseString = "Problem with internet connection";
        }
        return responseString;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(AppliContext,result,Toast.LENGTH_SHORT);
    }
}
