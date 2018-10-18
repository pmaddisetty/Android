package inclass02.poorna.com.group4_hw04;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by poorn on 2/22/2018.
 */

public class GetXmlOject extends AsyncTask<String,Integer,ArrayList<NewsItem>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<NewsItem> alist;
    iData idat;

    GetXmlOject(iData ida)
    {
     idat=ida;
    }
    @Override
    protected void onPreExecute() {
        idat.initprog();
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... strings) {
        try {

            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // alist= PersonParser.PersonSAXParser.parsePersons(connection.getInputStream());
                alist= NewsParser.NewsPullParser.parseNews(connection.getInputStream());
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
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
    protected void onPostExecute(ArrayList<NewsItem> newsItems) {
        if(newsItems!=null)
        {
            Log.d("demo","result="+newsItems.toString());
            idat.displayprog();
            idat.handleList(newsItems);
        }
        else
        {
            Log.d("demo","result=0");
        }
    }

    public static interface iData{
        void initprog();
        void displayprog();
        public ArrayList<NewsItem> handleList(ArrayList<NewsItem> newsItems);

    }

}
