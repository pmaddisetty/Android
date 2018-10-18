package inclass02.poorna.com.group4_hw04;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetXmlOject.iData {
    TextView ta;
    CharSequence[] ch={"Top Stories","World","U.S.","Business","Politics","Technology","Health","Entertainment","Travel","Living","Most Recent"};
    ProgressDialog pg;
    TextView ttitle,tdate,tdesc,tindex;
    ImageView imv,next,prev;
    static int index=0;
    ScrollView sc;
    ArrayList<NewsItem> newslist;
    String cat;
    @Override
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
       // sc.setVisibility(View.INVISIBLE);
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

                            Log.d("demo", "clicked item=" + i);
                            ta.setText(ch[i].toString());
                            cat="cnn_"+ch[i].toString();
                            if(ch[i].equals("Top Stories"))
                            {
                                cat="cnn_topstories";
                                Log.d("demo","entered if="+cat);
                            }
                           else if(ch[i].equals("U.S."))
                            {
                                cat="cnn_us";
                            }
                            else if(ch[i].equals("Business"))
                            {
                                cat="money_latest";
                            }
                           else if(ch[i].equals("Politics"))
                            {
                                cat="cnn_allpolitics";
                            }
                            else if(ch[i].equals("Technology"))
                            {
                                cat="cnn_tech";
                            }
                            else  if(ch[i].equals("Entertainment"))
                            {
                                cat="cnn_showbiz";
                            }
                           else  if(ch[i].equals("Most Recent"))
                            {
                                cat="cnn_latest";
                            }
                            String url = "http://rss.cnn.com/rss/"+cat+".rss";
                            Log.d("demo","url="+url);
                            new GetXmlOject(MainActivity.this).execute(url);
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
                            String url=newslist.get(index).getImgurl();
                            sc.setVisibility(View.VISIBLE);
                            Log.d("demo","url"+url);
                            ttitle.setText(newslist.get(index).getTitle());
                            tdate.setText(newslist.get(index).getPubdate());
                            tdesc.setText(newslist.get(index).description);
                            Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);

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
                            String url=newslist.get(index).getImgurl();
                            Log.d("demo","url"+url);
                            sc.setVisibility(View.VISIBLE);
                            ttitle.setText(newslist.get(index).getTitle());
                            tdate.setText(newslist.get(index).getPubdate());
                            tdesc.setText(newslist.get(index).description);
                            Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);

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
    public ArrayList<NewsItem> handleList(final ArrayList<NewsItem> newsItems) {
        Log.d("demo","in main ="+newsItems.toString());
        newslist=newsItems;
        if(newslist.size()==0||newslist.size()==1)
        {
            next.setEnabled(false);
            prev.setEnabled(false);
        }
        if(newslist.size()>0) {
            index=0;
            sc.setVisibility(View.VISIBLE);
            String url = newslist.get(index).getImgurl();
            Log.d("demo", "url" + url);
            ttitle.setText(newslist.get(index).getTitle());
            tdate.setText(newslist.get(index).getPubdate());
            tdesc.setText(newsItems.get(index).description);
            Picasso.with(MainActivity.this).load(url).placeholder(R.drawable.prog).into(imv);
            tindex.setText((index + 1) + " out of " + newslist.size());

            imv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isConnected()) {
                        Intent in = new Intent("android.intent.action.VIEW", Uri.parse(newslist.get(index).getLinkurl()));
                        startActivity(in);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            });

            ttitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isConnected()) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(newslist.get(index).getLinkurl()));
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
        else
        {
            Toast.makeText(MainActivity.this,"No News Found",Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
