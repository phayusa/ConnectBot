package com.example.sokomo.client_example;

import android.content.Context;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sokomo on 13/03/17.
 */
public class CameraThread extends TimerTask  {
    private boolean active;
    private String ip;
    private Context activyContext;
    private ImageView image;
    private Timer captureCam;

    public CameraThread(Context activyContext, String ip, ImageView viewImage){
        active = true;
        this.ip = ip;
        image = viewImage;
        this.activyContext = activyContext;
    }

    @Override
    public void run() {
        new CameraAsyncTask(activyContext,image,"http://"+ip+"/Api/image/get/").execute();

    }

    @Override
    public boolean cancel() {
        captureCam.cancel();
        return super.cancel();
    }

}
