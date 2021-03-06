package com.weatherApp.weatherapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String excuteGet(String targetURL1, String targetURL2) {
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(targetURL1);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream is;
            int status = connection.getResponseCode();
            if(status == 404){
                url = new URL(targetURL2);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                status = connection.getResponseCode();
            }
            if (status != HttpURLConnection.HTTP_OK)
                is = connection.getErrorStream();
            else
                is = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
