package com.google.android.gms.samples.vision.barcodereader;

import android.content.Context;
import android.widget.TextView;

import java.util.Random;

import com.google.android.gms.vision.text.Text;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Виктор on 14.11.2017.
 */

public class Server {
    Random random = new Random();
    Context context;
    TextView textView;
    public Server(Context context, TextView textView) {
        this.context = context;
        this.textView = textView;
    }

    public String getData(){
        getProject();
        return String.valueOf(random.nextInt(6)) ;
    }
    public void getProject() {
        Ion.with(context)
                .load("http://192.168.150.108:8080/WebApplicationMachine/NewServlet")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        textView.setText(result);
                    }
                });
    }


}
