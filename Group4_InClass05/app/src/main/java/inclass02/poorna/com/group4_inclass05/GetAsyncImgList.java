package inclass02.poorna.com.group4_inclass05;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by poorn on 2/12/2018.
 */

public class GetAsyncImgList extends AsyncTask<String,Integer,ArrayList<String>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<String> alist;
    iData idat;
    public GetAsyncImgList(iData idata)
    {
        this.idat=idata;
    }

    @Override
    protected void onPreExecute() {
     idat.initdict();
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        try {
            URL url=new URL(strings[0]);
            connection=(HttpURLConnection)url.openConnection();
//            connection.connect();
            br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb=new StringBuilder();
            alist=new ArrayList<>();
            String line="";
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
                alist.add(line);

            }
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                publishProgress();
                result=sb.toString();
                Log.d("demo","list size="+alist.size());
                Log.d("demo","final list is ="+alist);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(br!=null)
            {
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
    protected void onPostExecute(ArrayList<String> strings) {

       idat.handlelistimages(strings);
      idat.displaydict();
    }

    public static interface iData
    {
        public void handlelistimages(ArrayList<String> str);
        public void initdict();
        public void displaydict();

    }
}
