package inclass02.poorna.com.listdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetAsyncList.iData,GetJsonList.iData2 {
ArrayList<NewsItem> newslist;
ArrayList<NewsItem> newssort;
    ArrayList<NewsItem> remsort;
    ListView mylist;
public static String NEWS_KEY="news";
EditText edsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
         mylist=findViewById(R.id.mylistview);
        newssort=new ArrayList<>();
        remsort=new ArrayList<>();

        new GetJsonList(MainActivity.this).execute("https://api.themoviedb.org/3/search/movie?query=batman&api_key=7b74e5dedae33f4f6d6469aa35af3a24&page=1");
        edsearch=findViewById(R.id.srchkey);

    }

    @Override
    public void handlelist(ArrayList<NewsItem> newsItems) {
        newslist=newsItems;
        if(newslist.size()>0)
        {
            Log.d("demo","list="+newsItems.toString());
            Log.d("demo","size="+newsItems.size());
            Collections.sort(newslist,new MainActivity.Sortlinklist());
            NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.layout_news,newslist);
            mylist.setAdapter(adapter);

            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent in=new Intent(MainActivity.this,SecondActivity.class);
                    in.putExtra(NEWS_KEY,newslist.get(i));
                    startActivity(in);

                }
            });
            findViewById(R.id.gobtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<NewsItem> finallist= new ArrayList<>();
                    newssort.clear();
                    remsort.clear();
                    String srchtxt=edsearch.getText().toString();
                    for(int i=0;i<newslist.size();i++)
                    {
                        if(newslist.get(i).getTitle().toLowerCase().contains(srchtxt.toLowerCase()))
                        {
                            newssort.add(newslist.get(i));
                        }
                        else
                        {
                            remsort.add(newslist.get(i));
                        }
                    }
                    finallist.addAll(newssort);
                    finallist.addAll(remsort);
                    NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.layout_news,finallist);
                    mylist.setAdapter(adapter);
//                    View element=mylist.getChildAt(adapter.getPosition(newslist.get(1)));
//                    //   View element = mylist.getAdapter().getView(adapter.getPosition(newslist.get(1)), null, null);
//                    //View element = mylist.getAdapter().getView(adapter.getPosition(newslist.get(1)), null,null);
//                    if(element!=null)
//                    {
//                        Log.d("demo","entered elemennt");
//                        Log.d("demo","element="+element.toString());
//                        element.setBackgroundColor(Color.GREEN);
//                    }
                    //mylist.setBackgroundColor(Color.GREEN);


                }
            });
            findViewById(R.id.clrbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NewsAdapter adapter=new NewsAdapter(MainActivity.this,R.layout.layout_news,newslist);
                    mylist.setAdapter(adapter);
                }
            });


        }
    }

    @Override
    public void handlejson(ArrayList<MovieItem> movieItems) {

    }

    class Sortlinklist implements Comparator<NewsItem> {

        @Override
        public int compare(NewsItem t1, NewsItem t2) {
            if (t1.getDatetime() == null || t2.getDatetime() == null)
                return 0;
            return t1.getDatetime().compareTo(t2.getDatetime());
        }
    }
}
