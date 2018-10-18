package inclass02.poorna.com.group4_inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAsyncImage.iImage,GetAsyncImgList.iData{
   // static int j=0;
    String selecteditem;
    TextView tsearch;
    ImageView iv;
    ProgressDialog pg;
    ArrayList<String> finallist;
    ProgressDialog pg1;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        tsearch=findViewById(R.id.searchtext);
        iv=findViewById(R.id.mainimg);
        findViewById(R.id.searchbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    new GetAsyncUrl().execute("http://dev.theappsdr.com/apis/photos/keywords.php");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(finallist!=null) {
                    if(isConnected()) {
                        if (index <= finallist.size()) {
                            Log.d("demo", "list size=" + finallist.size());
                            Log.d("demo", "index value is=" + index);
                            index = index + 1;
                            if (index == finallist.size()) {
                                index = 0;
                            }
                            new GetAsyncImage(iv, MainActivity.this).execute(finallist.get(index));

                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please select a key word",Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.prev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(finallist!=null) {
                    if(isConnected()) {
                        if (index >= 0) {
                            if (index == 0) {
                                index = finallist.size();
                                //   s = data.get(index);
                            }
                            index = index - 1;
                            new GetAsyncImage(iv, MainActivity.this).execute(finallist.get(index));
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please select a key word",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void handleImage(Bitmap bitmap) {
        iv.setImageBitmap(bitmap);
    }

    @Override
    public void initprog() {

        pg=new ProgressDialog(MainActivity.this);
        pg.setTitle("Loading Photo");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    public void displaprog() {
        pg.dismiss();
    }
    @Override
    public void initdict() {
        pg1=new ProgressDialog(MainActivity.this);
        pg1.setTitle("Loading Dictionary");
        pg1.setCancelable(false);
        pg1.show();
    }

    @Override
    public void displaydict() {
        pg1.dismiss();
    }

    @Override
    public void handlelistimages(ArrayList<String> str) {
        finallist=str;
        if(finallist!=null) {
            index = 0;
            if(isConnected()) {
                new GetAsyncImage(iv, MainActivity.this).execute(finallist.get(index));
            }
            else
            {
                Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

            }
        }
        else
        {
            Toast.makeText(MainActivity.this,"No Images Found",Toast.LENGTH_SHORT).show();
        }
    }

    public class GetAsyncUrl extends AsyncTask<String,Integer,ArrayList<String>>{
    HttpURLConnection connection;
    String result;
    BufferedReader br;
    StringBuilder sb;
    ArrayList<String> akeys;
    ArrayList<String > aresult;

        @Override
    protected ArrayList<String> doInBackground(String... strings) {
        try {
            index=0;
            URL url = new URL(strings[0]);
            connection=(HttpURLConnection)url.openConnection();
            br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb=new StringBuilder();
            String line="";
            akeys=new ArrayList<String>();
            while((line=br.readLine())!=null)
            {

                sb.append(line);
            }
            String s2=null;
            int j=0;
            for(int i=0;i<sb.length();i++) {
                if (sb.charAt(i) == ';')
                {
                    s2 = sb.substring(j, i);
                    j=i+1;
                    akeys.add(s2);

                }
            }
            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                publishProgress();
               aresult=akeys;
               Log.d("demo","Result sizze"+aresult.size());
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
     return  akeys;
    }

        @Override
    protected void onPostExecute( ArrayList<String> keylist) {
        final ArrayList<String> alertlist;
        alertlist=keylist;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose a Keyword");
        builder.setItems(alertlist.toArray(new CharSequence[alertlist.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selecteditem = alertlist.get(i);
                tsearch.setText(selecteditem);
                //url = ParamsUrl.getUrl("http://dev.theappsdr.com/apis/photos/index.php", selecteditem);
               //url="http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt";
                //index=0;
                new GetAsyncImgList(MainActivity.this).execute("http://dev.theappsdr.com/apis/photos/index.php?keyword="+selecteditem);
                //new GetAsyncUrl()
                Log.d("demo", "selected item" + selecteditem);
            }
        });
        builder.create();
        builder.show();

    }
}
    private boolean isConnected(){
        ConnectivityManager cn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cn.getActiveNetworkInfo();
        if(net==null || !net.isConnected()||
                (net.getType()!=cn.TYPE_WIFI && net.getType()!=cn.TYPE_MOBILE))
        {
            return false;
        }
        return true;
    }

}
