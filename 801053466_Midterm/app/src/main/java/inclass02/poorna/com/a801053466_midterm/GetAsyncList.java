package inclass02.poorna.com.a801053466_midterm;

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
import java.util.Collections;

/**
 * Created by poorn on 3/12/2018.
 */

public class GetAsyncList extends AsyncTask<String, Integer, ArrayList<Apps>> {

    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<Apps> alist;
    ArrayList<String> genlist;
    ArrayList<String> listforalert;
    iData iDat;

    GetAsyncList(iData idata)
    {
        this.iDat=idata;
    }


    @Override
    protected void onPreExecute() {
        iDat.initprog();
    }

    @Override
    protected ArrayList<Apps> doInBackground(String... strings) {
        try{
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();

            listforalert=new ArrayList<>();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            Log.d("demo","string="+sb.toString());
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = sb.toString();
                JSONObject root = new JSONObject(result);
                JSONObject listfeed=root.getJSONObject("feed");
                JSONArray apps=listfeed.getJSONArray("results");
                for(int i=0;i<apps.length();i++)
                {
                    JSONObject appobj=apps.getJSONObject(i);
                    Apps app=new Apps();
                    genlist=new ArrayList<>();
                    String artist=appobj.getString("artistName");
                    Log.d("demo","artist="+artist);
                    String releaseDate=appobj.getString("releaseDate");
                    Log.d("demo","rel="+releaseDate);
                    String appnam=appobj.getString("name");
                    Log.d("demo","name="+appnam);
                    String imgurl=appobj.getString("artworkUrl100");
                    String copy=appobj.getString("copyright");
                    Log.d("demo","url="+url);
                    JSONArray genre=appobj.getJSONArray("genres");
                    for(int j=0;j<genre.length();j++)
                    {
                        JSONObject genobj=genre.getJSONObject(j);
                        String gen=genobj.getString("name");
                        genlist.add(gen);
                        listforalert.add(gen);
                    }
                    Collections.sort(genlist);
                    Log.d("demo","genlisy="+genlist);
                    Log.d("demo","listfor="+listforalert);
                    app.setAppname(appnam);
                    app.setArtist(artist);
                    app.setReleasedate(releaseDate);
                    app.setImgurl(imgurl);
                    app.setGenlist(genlist);
                    app.setCopyright(copy);
                    app.setForlist(listforalert);
                    alist.add(app);
                    Log.d("demo","list size="+alist.size());
                }
            }
        }
        catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return alist;
    }
    @Override
    protected void onPostExecute(ArrayList<Apps> apps) {


        if(alist!=null)
        {
            iDat.handlelist(apps);
            Log.d("demo","list="+alist);
        }
        else
        {
            Log.d("demo","null");
        }
    }

    public static interface iData{
        public void handlelist(ArrayList<Apps> apps);
        public void initprog();
      // public void alertlist(ArrayList<Apps> listfor);

    }
}

