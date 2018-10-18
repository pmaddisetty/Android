package inclass02.poorna.com.listdemo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by poorn on 3/12/2018.
 */

public class GetJsonList extends AsyncTask<String, Integer, ArrayList<MovieItem>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<MovieItem> alist;
   iData2 idata;

    GetJsonList(iData2 idta)
    {

        idata=idta;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<MovieItem> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try {
                    alist = JsonParsing.JsonParser.items(sb.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return alist;
    }

    @Override
    protected void onPostExecute(ArrayList<MovieItem> people) {
        if(people!=null)
        {
            Log.d("demo val","size="+people);
            Log.d("demo","list size="+people.size());


        }
        else
        {
            Log.d("demo","null");
        }

    }

    public static interface iData2 {

        public void handlejson(ArrayList<MovieItem> movieItems);
    }

}
