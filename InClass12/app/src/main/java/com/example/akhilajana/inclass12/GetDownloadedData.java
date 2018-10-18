package com.example.akhilajana.inclass12;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by akhilajana on 11/27/17.
 */

 class GetDownloadedData extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {

        String data = "";

        try {
            data = getLatLngDetailsFromUrl(url[0]);
        } catch (Exception e) {
            Log.d("demo", "doInBackground: " + e.toString());
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        MapsActivity.ParseGoogleLocations parserTask = new MapsActivity.ParseGoogleLocations();
        parserTask.execute(result);

    }

    private String getLatLngDetailsFromUrl(String latLngUrl) throws IOException {

        String s = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(latLngUrl);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            s = stringBuffer.toString();
            bufferedReader.close();

        } catch (Exception e) {
            Log.d("demo", "getLatLngDetailsFromUrl: " + e.toString());
        } finally {
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return s;
    }
}


