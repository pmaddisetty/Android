package inclass02.poorna.com.group4_inclass07;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
        idata.initProg();
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... strings) {
        try {

            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d("demo","url="+url);
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line = "";
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               // alist = NewsParser.NewsPullParser.parseNews(connection.getInputStream());

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    Log.d("demo","string builder"+sb.toString());
                }
                result = sb.toString();
                Log.d("demo","json val="+result);
                JSONObject root=new JSONObject(result);
                JSONArray newsar=root.getJSONArray("articles");
                for(int i=0;i<newsar.length();i++) {
                    JSONObject newsjson = newsar.getJSONObject(i);
                    NewsItem news = new NewsItem();
                    news.author = newsjson.getString("author");
                    news.title = newsjson.getString("title");
                    news.description = newsjson.getString("description");
                    news.url = newsjson.getString("url");
                    news.imageurl = newsjson.getString("urlToImage");
                    news.pubdate = newsjson.getString("publishedAt");
                    alist.add(news);
                }
                Log.d("demo","alist size="+alist.size());
                Log.d("demo","list="+alist.toString());

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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
    protected void onPostExecute(ArrayList<NewsItem> news) {
        idata.handlelist(news);
        idata.disprog();


    }

    public static interface iData {

        public void handlelist(ArrayList<NewsItem> newsItems);
        public void initProg();
        public void disprog();
    }

}
