package inclass02.poorna.com.group4_inclass06;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetJsonObj.iData{
    TextView ta;
    CharSequence[] ch={"Business","Entertainment","General","Health","Science","Sports","Technology"};
    ProgressDialog pg;
    TextView ttitle,tdate,tdesc,tindex;
    ImageView imv,next,prev;
    static int index=0;
    ScrollView sc;
    ArrayList<NewsItem> newslist;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Main Activity");
        ta=findViewById(R.id.showcateg);
        ttitle=findViewById(R.id.ntitle);
        tdate=findViewById(R.id.ndate);
        tdesc=findViewById(R.id.ndesc);
        tindex=findViewById(R.id.nindex);
        imv=findViewById(R.id.nimg);
        sc=findViewById(R.id.scview);
        sc.setVisibility(View.INVISIBLE);
        tdesc.setVisibility(View.INVISIBLE);
        next=findViewById(R.id.next);
        prev=findViewById(R.id.prev);
        next.setEnabled(false);
        prev.setEnabled(false);

        findViewById(R.id.btngo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    index = 0;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choose Category");
                    builder.setCancelable(false);
                    builder.setItems(ch, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Log.d("demo", "clicked item" + i);
                            ta.setText(ch[i].toString());
                            String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=a1a5ddf072ad4b7991fc48399093ccc6&category=" + ch[i].toString();

                            new GetJsonObj(MainActivity.this).execute(url);
                            sc.setVisibility(View.VISIBLE);
                            tdesc.setVisibility(View.VISIBLE);
                            next.setEnabled(true);
                            prev.setEnabled(true);


                        }
                    });
                    builder.create();
                    builder.show();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newslist!=null) {
                    if(isConnected()) {
                        if (index <= newslist.size()) {
                            Log.d("demo", "list size=" + newslist.size());
                            Log.d("demo", "index value is=" + index);
                            index = index + 1;
                            if (index == newslist.size()) {
                                index = 0;
                            }
                            String url=newslist.get(index).getImageurl();
                            Log.d("demo","url"+url);
                            ttitle.setText(newslist.get(index).getTitle());
                            tdate.setText(newslist.get(index).getPubdate());
                            tdesc.setText(newslist.get(index).description);
                            if(url.equals(null))
                            {
                                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.noimg).into(imv);

                            }
                            else {
                                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);
                            }
                        }
                        tindex.setText((index+1)+" out of "+newslist.size());
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

                if(newslist!=null) {
                    if(isConnected()) {
                        if (index >= 0) {
                            if (index == 0) {
                                index = newslist.size();
                                //   s = data.get(index);
                            }
                            index = index - 1;
                            String url=newslist.get(index).getImageurl();
                            Log.d("demo","url"+url);
                            ttitle.setText(newslist.get(index).getTitle());
                            tdate.setText(newslist.get(index).getPubdate());
                            tdesc.setText(newslist.get(index).description);
                            if(url.equals(null))
                            {
                                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.noimg).into(imv);

                            }
                            else {
                                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);
                            }
                        }
                        tindex.setText((index+1)+" out of "+newslist.size());
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
    public boolean isConnected()
    {
        ConnectivityManager cn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cn.getActiveNetworkInfo();
        if(net==null || !net.isConnected()||
                (net.getType()!=cn.TYPE_WIFI && net.getType()!=cn.TYPE_MOBILE))
        {
            return false;
        }
        return true;

    }


    @Override
    public void initprog() {
        pg= new ProgressDialog(MainActivity.this);
        pg.setTitle("Loading News");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    public void displayprog() {
        pg.dismiss();
    }

    @Override
    public ArrayList<NewsItem> handleList(ArrayList<NewsItem> newsItems) {
        newslist=newsItems;
        if(newslist.size()==0||newslist.size()==1)
        {
            next.setEnabled(false);
            prev.setEnabled(false);
        }
        if(newslist!=null) {
            String url = newslist.get(0).getImageurl();
            Log.d("demo", "url" + url);
            ttitle.setText(newslist.get(0).getTitle());
            tdate.setText(newslist.get(0).getPubdate());
            tdesc.setText(newsItems.get(0).description);
            if(url.equals(null))
            {
                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.noimg).into(imv);

            }
            else {
                Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);
            }
            tindex.setText((index + 1) + " out of " + newslist.size());
        }
        else
        {
            Toast.makeText(MainActivity.this,"No News Found",Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
