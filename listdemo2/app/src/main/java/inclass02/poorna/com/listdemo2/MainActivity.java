package inclass02.poorna.com.listdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements GetAsyncList.iData{
ListView mylist;
ArrayList<String> list;
ArrayList<NewsItem> newslist;
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mylist=findViewById(R.id.mylist_view);
        list=new ArrayList<>();
        list.add("Top Stories");
        list.add("World");
        list.add("UK");
        list.add("Business");
        list.add("Politics");
        list.add("Health");
        list.add("Education & Family");
        list.add("Science & Environment");
        list.add("Technology");
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,list);
        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item= list.get(i);
                Log.d("demo","url while  sendinng="+item);
                if(item.equals("Top Stories"))
                {
                    url="http://feeds.bbci.co.uk/news/rss";
                }
                if(item.equals("World"))
                {
                    url="http://feeds.bbci.co.uk/news/world/rss";
                }

                Log.d("demo","url while  sendinng="+url);
                new GetAsyncList(MainActivity.this).execute(url);

            }
        });
    }

    @Override
    public void handlelist(ArrayList<NewsItem> newsItems) {
        newslist=newsItems;
        if(newslist!=null) {
            Log.d("demo", "list=" + newsItems.toString());
            Log.d("demo", "size=" + newsItems.size());
           // Collections.sort(newslist, new MainActivity.Sortlinklist());
            NewsAdapter adapter = new NewsAdapter(MainActivity.this, R.layout.layout_news, newslist);
            mylist.setAdapter(adapter);
        }

    }
}
