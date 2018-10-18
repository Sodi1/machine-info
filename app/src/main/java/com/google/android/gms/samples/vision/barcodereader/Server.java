package com.google.android.gms.samples.vision.barcodereader;

import android.os.Environment;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
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

    private static final int  MEGABYTE = 1024 * 1024;

    private  String sPathToPdf;

    /**
     * Получение pdf по URL
     * @param fileUrl
     */
    public  Boolean getPdfFile(final String fileUrl){

        Boolean resultFlag = false;

        try {

                 File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                if (!root.exists()) {
                    root.mkdirs();
                }
                FileWriter writer = new FileWriter(root);
                writer.close();

            final URL url = new URL(fileUrl);
            final HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
          //  urlConnection.setRequestMethod("GET");
           // urlConnection.setDoOutput(true);
            urlConnection.connect();

            final InputStream inputStream = urlConnection.getInputStream();
            System.out.println("asd");
            final FileOutputStream fileOutputStream = new FileOutputStream(root); //Путь до pdf

            byte[] buffer = new byte[MEGABYTE];

            int bufferLength = 0;


            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }

            if(bufferLength > 0) {
                resultFlag = true;
            }

            fileOutputStream.close();

        } catch (FileNotFoundException e) {
e.getStackTrace();
        } catch (MalformedURLException e) {
e.getStackTrace();
        } catch (IOException e) {
e.getStackTrace();        }

        return resultFlag;
    }

    /**
     *
     * @return
     */
    public String getpPathToPdf(){
        return sPathToPdf;
    }

    /**
     *
     * @param pathToPdf
     */
    public  void setsPathToPdf(final String pathToPdf){
        this.sPathToPdf = sPathToPdf;
    }

    /**
     *
     * @param sPath
     * @return
     */
    public Boolean removeTempPdf(final String sPath){
        return true;
    }

    /**
     *
     * @param str
     * @return
     * @throws JSONException
     */
    public JSONObject jsonParse(String str) throws JSONException {
            JSONObject obj = new JSONObject(str);
            int i =0;
            return  obj;
    }
}
