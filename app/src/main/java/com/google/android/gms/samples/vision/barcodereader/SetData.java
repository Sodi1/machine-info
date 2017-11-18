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
    TextView textView;
    String data = "asdasd";
    Boolean execute = false;
    Context context;

    public SetData(TextView textView, Context context){
        this.textView = textView;
        this.context = context;
//        set();
    }
    public void set(){
        Server server = new Server(this.context, textView);
        data =  server.getData();
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
