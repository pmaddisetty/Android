package inclass02.poorna.com.listdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by poorn on 2/26/2018.
 */

public class GetAsyncList extends AsyncTask<String, Integer, ArrayList<NewsItem>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<NewsItem> alist;
    iData idata;

    GetAsyncList(iData idta)
    {
        idata=idta;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                alist = NewsParser.NewsPullParser.parseNews(connection.getInputStream());

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
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
    protected void onPostExecute(ArrayList<NewsItem> people) {
        idata.handlelist(people);

    }

    public static interface iData {

        public void handlelist(ArrayList<NewsItem> newsItems);
    }

}
