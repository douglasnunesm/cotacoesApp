package com.dougl.cotacao;



import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by dougl on 31/10/2017.
 */
public class Network {

    public static String getCotacao(String url) {
        InputStream inputStream;
        String resultado = null;
        try{
            HttpURLConnection httpURLConnection;
            URL endPoint = new URL(url);
            httpURLConnection = (HttpURLConnection) endPoint.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);

            inputStream = httpURLConnection.getInputStream();
            resultado = parseToString(inputStream);
        }catch (Exception e){
            Log.e("Network",e.getMessage());
        }
        return resultado;
    }

    public static String parseToString(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String linha = null;

        try {
            while ((linha = bufferedReader.readLine()) != null) {
                stringBuilder.append(linha);
            }
        }catch (IOException e) {
            Log.e("Network (IOException)",e.getMessage());
        }catch (Exception e) {
            Log.e("Network",e.getMessage());
        }
        return stringBuilder.toString();
    }
}