package inclass02.poorna.com.threaddemo2;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by poorn on 2/12/2018.
 */

public class GetAsynUrl extends AsyncTask<String,Void,HashMap<String,ArrayList<String>>> {
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb,sb1;
    ArrayList<String> alist;
    private HashMap<String,ArrayList<String>> resmap;
    StringTokenizer tokenizer;
    String key,value;
    ArrayList<String> sl1,sl2,sl3,sl4,sl5;
    static int j=0;
    iData idat;
    HashMap<String,ArrayList<String>> resultmp;

    public GetAsynUrl(iData idat) {
        this.idat = idat;
    }

    //iData idat;
    @Override
    protected HashMap<String, ArrayList<String>> doInBackground(String... strings) {

        try {
            resmap=new HashMap<String,ArrayList<String>>();
            resultmp=new HashMap<String,ArrayList<String>>();
            URL url=new URL(strings[0]);
            connection=(HttpURLConnection)url.openConnection();
//            connection.connect();
            br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
         //   sb=new StringBuilder();
            sb=new StringBuilder();
            sl2=new ArrayList<String>();
            sl3=new ArrayList<String>();
            sl4=new ArrayList<String>();
            sl5=new ArrayList<String>();
            sl1=new ArrayList<String>();
            String line="";
            while((line=br.readLine())!=null)
            {

                sb.append(line);
            }
            String s2=null;
            for(int i=0;i<sb.length();i++)
            {

                if(sb.charAt(i)==';')

                {
                  s2= sb.substring(j,i);
                  Log.d("demo","substring value="+s2);
                  Log.d("demo","found semicolon at"+i);
                    String val[]= s2.split(",");
                   key=val[0];
                   value=val[1];
                    j=i+1;
                   Log.d("demo","key is"+key);
                   Log.d("demo","val is"+value);
                    Log.d("demo","i is"+i);
                    Log.d("demo","j is"+j);

                    if(key.equals("UNCC"))
                    {
                        Log.d("demo","Uncc entered");
                        sl1.add(value);
                      resmap.put(key,sl1);
                    }
                    if(key.equals("Android"))
                    {
                        Log.d("demo","and entered");
                        sl2.add(value);
                        resmap.put(key,sl2);
                    }
                    if(key.equals("winter"))
                    {
                        Log.d("wint","winter entered");
                        sl3.add(value);
                        resmap.put(key,sl3);
                    }
                    if(key.equals("aurora"))
                    {
                        Log.d("demo","aurora entered");
                        sl4.add(value);
                        resmap.put(key,sl4);
                    }
                    if(key.equals("wonders"))
                    {
                        Log.d("demo","won entered");
                        sl5.add(value);
                        resmap.put(key,sl5);
                    }

                }
                Log.d("demo","list szie="+resmap.size());
            }

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                resultmp= resmap;
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

        return resultmp;
    }

    @Override
    protected void onPostExecute(HashMap<String, ArrayList<String>> restmp) {
        idat.handleurl(restmp);
    }

    public  static interface iData
    {
            public void handleurl(HashMap<String,ArrayList<String>> hmap);
    }

}
