package inclass02.poorna.com.group4_inclass06;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by poorn on 2/19/2018.
 */

public class GetJsonObj extends AsyncTask<String, Integer, ArrayList<NewsItem>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<NewsItem> alist;
    iData idat;

GetJsonObj(iData idata)
{
    idat=idata;
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
            connection.connect();
            Log.d("demo","url="+url);
            alist = new ArrayList<>();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line = "";

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    Log.d("demo","string builder"+sb.toString());
                }
                result = sb.toString();
                Log.d("demo","json val="+result);
                JSONObject root=new JSONObject(result);
                JSONArray newsar=root.getJSONArray("articles");
                for(int i=0;i<newsar.length();i++)
                {
                    JSONObject newsjson= newsar.getJSONObject(i);
                    NewsItem news=new NewsItem();
                    Source src= new Source();
                    JSONObject srcjson= newsjson.getJSONObject("source");
                    src.id=srcjson.getString("id");
                    src.name=srcjson.getString("name");
                    news.source=src;
                    news.author=newsjson.getString("author");
                    news.title=newsjson.getString("title");
                    news.description=newsjson.getString("description");
                    news.url=newsjson.getString("url");
                    news.imageurl=newsjson.getString("urlToImage");
                    news.pubdate=newsjson.getString("publishedAt");
                    alist.add(news);

//                                Person per=new Person();
//                                per.name=personjson.getString("name");
//                                per.id=personjson.getInt("id");
//                                per.age=personjson.getInt("age");
//                                Address adr=new Address();
//                                JSONObject adressjson= personjson.getJSONObject("address");
//                                adr.line1=adressjson.getString("line1");
//                                adr.city=adressjson.getString("line1");
//                                adr.state=adressjson.getString("line1");
//                                adr.zip=adressjson.getString("line1");
//                                per.address=adr;
//                            alist.add(per);
                }


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
    protected void onPostExecute(ArrayList<NewsItem> people) {
        if(people!=null)
        {
            Log.d("demo","result="+people.toString());
            idat.displayprog();
            idat.handleList(people);
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
