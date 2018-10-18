package inclass02.poorna.com.threaddemo;

import android.os.AsyncTask;
import android.util.Log;

//import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by poorn on 2/10/2018.
 */

public class GetAsynUrl extends AsyncTask<String,Void,ArrayList<String>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<String> alist;
    iData idat;
    public GetAsynUrl(iData idata)
    {
        this.idat=idata;
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
        if(connection!=null)
        {
            connection.disconnect();
        }

        return alist;
    }


    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        idat.handlelistDatainInterfaces(alist);
    }



    public static interface iData{
        public void handlelistDatainInterfaces(ArrayList<String> strings);

    }
}
