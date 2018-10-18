package inclass02.poorna.com.exampract;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

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

public class GetAsyncList extends AsyncTask<String, Integer, ArrayList<iTunes>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<iTunes> alist;
    iData iDat;
    GetAsyncList(iData idata)
    {
        this.iDat=idata;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected ArrayList<iTunes> doInBackground(String... strings) {
        try {

            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            Log.d("demo", "value url=" + url);
            alist = new ArrayList<>();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                        sb.append(line);
                        }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = sb.toString();
                JSONObject root=new JSONObject(result);
                JSONObject listfeed=root.getJSONObject("feed");
                JSONArray tunes=listfeed.getJSONArray("entry");
                Log.d("demo","tunes ="+tunes);
                for(int i=0;i<listfeed.length();i++)
                {
                    JSONObject entryobj=tunes.getJSONObject(i);
                    iTunes ituneobj=new iTunes();
                    String name=entryobj.getJSONObject("im:name").getString("label");
                    String imageurl=entryobj.getJSONArray("im:image").getJSONObject(0).getString("label");
                    double price=Double.parseDouble(entryobj.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
                    ituneobj.setAppname(name);
                    ituneobj.setPrice(price);
                    ituneobj.setImageurl(imageurl);
                    ituneobj.setThumbnail(0);
                    alist.add(ituneobj);

//                    JSONObject personjson= persons.getJSONObject(i);
//                    Person per=new Person();
//                    per.name=personjson.getString("name");
//                    per.id=personjson.getInt("id");
//                    per.age=personjson.getInt("age");
//                    Address adr=new Address();
//                    JSONObject adressjson= personjson.getJSONObject("address");
//                    adr.line1=adressjson.getString("line1");
//                    adr.city=adressjson.getString("line1");
//                    adr.state=adressjson.getString("line1");
//                    adr.zip=adressjson.getString("line1");
//                    per.address=adr;
//                    alist.add(per);
                }


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alist;
    }

    @Override
    protected void onPostExecute(ArrayList<iTunes> tunes) {
        Collections.sort(tunes,new SortlinklistAsec());
        iDat.handlelist(tunes);
    }

    public static interface iData
    {
        public void handlelist(ArrayList<iTunes> tunelist);
    }
}
