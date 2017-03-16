package com.example.sokomo.client_example;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public final int PERMISSION_REQUEST_KEY = 1102;
    private CameraThread cam;
    private Timer camTimer;

    public void checkPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        PERMISSION_REQUEST_KEY);
            }
        }

    }



    class RequestTask extends AsyncTask<String, Void, String> {

        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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
                conn.setRequestMethod("POST");
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
            System.out.println("result "+result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT);
            //((TextView) findViewById(R.id.informationText)).setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //camAsync.cancel(true);
    }

    public void buttonListener(View view){
        checkPermission();
        int value = ((ImageButton) view).getId();
        String characterToSend = "";
        switch (value){
            case R.id.up:
                characterToSend = "o";
                break;
            case R.id.down:
                characterToSend = "l";
                break;
            case R.id.left:
                characterToSend = "k";
                break;
            case R.id.right:
                characterToSend = "m";
                break;
            case R.id.rot_left:
                characterToSend = "a";
                break;
            case R.id.rot_right:
                characterToSend = "e";
                break;
            case R.id.stop:
                characterToSend = "STOP";
                break;
        }

        String ipAdress = ((EditText) findViewById(R.id.ip_text)).getText().toString();
        new RequestTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"http://"+ipAdress+"/Api/command/",characterToSend);
    }

    public void cameraListener(View view){
        CheckBox toggle = ((CheckBox) view);
        String ip = ((EditText) findViewById(R.id.ip_text)).getText().toString();
        if (toggle.isChecked()){
            camTimer = new Timer();
            camTimer.schedule(new CameraThread(getApplicationContext(), ip, ((ImageView) findViewById(R.id.cameraContainer))),0,500);
            //new CameraAsyncTask(getApplicationContext(), ((ImageView) findViewById(R.id.cameraContainer)),"http://"+ip+"/Api/image/get/").execute();

        }else{
            camTimer.cancel();
            camTimer.purge();
            ((ImageView) findViewById(R.id.cameraContainer)).setImageResource(R.drawable.nocam);
        }
    }
}
