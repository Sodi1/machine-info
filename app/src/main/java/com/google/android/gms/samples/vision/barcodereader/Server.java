package com.google.android.gms.samples.vision.barcodereader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Iterator;


/**
 * Created by Виктор on 14.11.2017.
 * Делает запрос к серверу
 * конструктор принимает id оборудования
 */
public class Server {
    static String ipServer = "";
    private final String protocol = "http://";
    private final String portServer = ":8080";
    private final String apiServerGet = "/get/";
    private String url = "";
    public Server(String id) {
        this.url = protocol + ipServer+ portServer + apiServerGet + id ;
    }

    public JSONObject GetData() throws JSONException {
        String url = this.url;
        StringBuffer response = new StringBuffer();
        JSONObject responseJson;

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        }catch(IOException e){
            e.printStackTrace();
        }
        responseJson = jsonParse(response.toString());
        return responseJson ;
    }

    public JSONObject jsonParse(String str) throws JSONException {
            JSONObject obj = new JSONObject(str);
            int i =0;
            return  obj;
    }
}
