package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by Виктор on 14.11.2017.
 */
//
public class SetData extends AsyncTask<Void, Void, Void>  {
    TextView speed,position,tool,plc;
    String data = "asdasd";
    Boolean execute = false;
    Context context;

    public SetData(TextView speed,TextView position,TextView tool,TextView plc, Context context){
        this.speed = speed;
        this.position = position;
        this.tool = tool;
        this.plc = plc;


        this.context = context;
//        set();
    }
    public void set(){
       // Server server = new Server(this.context, speed,position,tool,plc);
       // data =  server.getData();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true)
        {
            set();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress();
        }

    }
}
