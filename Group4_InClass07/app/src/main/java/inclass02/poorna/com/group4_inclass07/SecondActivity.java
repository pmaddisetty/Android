package inclass02.poorna.com.group4_inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements GetAsyncList.iData {
String category;
ArrayList<NewsItem> newslist;
ProgressDialog pg;
public static String SECOND_KEY="second";
ListView mylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        mylist=findViewById(R.id.mylistnew);
        if(getIntent()!=null ||getIntent().getExtras()!=null)
        {
            category=getIntent().getExtras().getString(MainActivity.FIRST_KEY);
            setTitle(category);
            if(isConnected()) {
                String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=a1a5ddf072ad4b7991fc48399093ccc6&category=" + category;
                new GetAsyncList(SecondActivity.this).execute(url);
            }
            else
            {
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void handlelist(ArrayList<NewsItem> newsItems) {
        newslist=newsItems;
        if(newsItems!=null)
        {
            NewsAdapter adapter=new NewsAdapter(SecondActivity.this,R.layout.layout_news,newslist);
            mylist.setAdapter(adapter);
            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent in=new Intent(SecondActivity.this,ThirdActivity.class);
                    in.putExtra(SECOND_KEY,newslist.get(i));
                    startActivity(in);

                }
            });

        }
        else
        {
            Toast.makeText(this,"No News Found",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initProg() {
        pg=new ProgressDialog(this);
        pg.setCancelable(false);
        pg.setTitle("Loading News...");
        pg.show();
    }

    @Override
    public void disprog() {
        pg.dismiss();
    }


    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
