package inclass02.poorna.com.exampract;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EmptyStackException;

public class MainActivity extends AppCompatActivity implements GetAsyncList.iData {
ArrayList<iTunes> tlist;
    ListView mylist;
    RecyclerView rview;
    RecycleAdapter radapter;
    Switch st;
    ArrayList<iTunes> addlist;
    Boolean enabled;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("1");
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.three);
        mylist=findViewById(R.id.mylistview);
        addlist=new ArrayList<iTunes>();
        rview=findViewById(R.id.myrecycler_view);
        LinearLayoutManager rlayoutmanag=new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        rview.setLayoutManager(rlayoutmanag);
        rview.setHasFixedSize(false);

        st=findViewById(R.id.switchon);

        st.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    enabled=true;
                    Collections.sort(tlist,new SortlinklistAsec());
                    handlelist(tlist);
                    st.setText("Ascending");
                }
                else
                {
                    enabled=false;
                    Collections.sort(tlist,new SortlinklistDesc());
                    handlelist(tlist);
                    st.setText("Descending");
                }
            }
        });
        new GetAsyncList(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");

    }

    @Override
    public void handlelist(ArrayList<iTunes> tunelist) {
        tlist=tunelist;
        if(tlist!=null)
        {
            Log.d("demo","tunes in main="+tlist);
            final ListAdapter adapter=new ListAdapter(MainActivity.this,R.layout.layout_tunes,tlist);
            mylist.setAdapter(adapter);
            mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    iTunes it=tlist.get(i);
                    addlist.add(it);
                    Log.d("demo","addlit="+addlist);
                    Log.d("demo","adlist size="+addlist.size());
                    radapter=new RecycleAdapter(MainActivity.this,R.layout.layout_modified,addlist);
                    rview.setAdapter(radapter);
                }
            });
        }
        else
        {
            Toast.makeText(this,"No News Found",Toast.LENGTH_SHORT).show();
        }

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

    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menubar,menu);
        return true;
    }
    public void sortfunasec (MenuItem item)
    {
        Collections.sort(tlist,new SortlinklistAsec());
        handlelist(tlist);
    }
    public void sortfundesc (MenuItem item)
    {
        Collections.sort(tlist,new SortlinklistDesc());
        handlelist(tlist);
    }

}
