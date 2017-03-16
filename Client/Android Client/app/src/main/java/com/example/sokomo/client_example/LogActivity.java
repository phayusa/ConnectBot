package com.example.sokomo.client_example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by sokomo on 14/03/17.
 */
public class LogActivity extends Activity {

    public static final String prefName = "LogPreference" ;

    class LogRequest extends AsyncTask<String, Void, String> {

        private String username;
        private String password;

        public LogRequest(String user, String pwd){
            username = user;
            password = pwd;
        }

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

            /*
            if (!isOnline()){
                return "You do not have internet access";
            }
            */

            String responseString = "";
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type","application/json");


                JSONObject toSendData = new JSONObject();
                try {
                    toSendData.accumulate("username", username);
                    toSendData.accumulate("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Problem with the send of Json";
                }

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(toSendData.toString());
                writer.flush();

                InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                BufferedReader buff = new BufferedReader(reader);

                responseString = conn.getResponseMessage();

                if (conn.getResponseMessage().equals("Created")){
                    String token;
                    try {
                        JSONObject receiveJson = new JSONObject(buff.readLine());
                        token = receiveJson.getString("token");
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra(getString(R.string.key_token),token);
                        i.putExtra(getString(R.string.key_ip),params[1]);
                        startActivity(i);
                        responseString = " Welcome "+username;
                    }catch (JSONException e){
                        e.printStackTrace();
                        return "Problem with the receive json";
                    }


                }

            } catch (IOException e) {
                responseString = "Problem with internet connection";
                responseString = e.getMessage();
            }
            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("result "+result);
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_layout);

        SharedPreferences sharedPref = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String username = sharedPref.getString(getString(R.string.key_username),"");
        String password = sharedPref.getString(getString(R.string.Password),"");
        String ip = sharedPref.getString(getString(R.string.key_ip),"");

        ((EditText) findViewById(R.id.UserField)).setText(username);
        ((EditText) findViewById(R.id.PwdField)).setText(password);
        ((EditText) findViewById(R.id.ipAdress)).setText(ip);
    }

    public void connectionClient(View v){
        String ip = ((EditText) findViewById(R.id.ipAdress)).getText().toString();
        String url = "http://"+ip+"/api/users/login/";
        String username = ((TextView) findViewById(R.id.UserField)).getText().toString();
        String password = ((TextView) findViewById(R.id.PwdField)).getText().toString();

        new LogRequest(username,password).execute(url,ip);


    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();

        String username = ((TextView) findViewById(R.id.UserField)).getText().toString();
        CheckBox psw = ((CheckBox) findViewById(R.id.checkRemember));
        editor.putString(getString(R.string.key_username), username);
        // Only save password if it is wanted
        if (psw.isChecked()) {
            String password = ((TextView) findViewById(R.id.PwdField)).getText().toString();
            editor.putString(getString(R.string.Password), password);
        }

        String ip = ((EditText) findViewById(R.id.ipAdress)).getText().toString();
        editor.putString(getString(R.string.key_ip),ip);
        editor.commit();
    }
}


