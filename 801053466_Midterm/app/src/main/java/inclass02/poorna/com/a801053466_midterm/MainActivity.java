package inclass02.poorna.com.a801053466_midterm;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements GetAsyncList.iData {
    ProgressDialog pg;
    ArrayList<Apps> applist;
    ArrayList<Apps> appsort;
    ArrayList<Apps> remsort;
    ArrayList<Apps> finallist;

    ListView mylist;
    ArrayList<String> gentlist;
    public static String MAIN_KEY="main";
    TextView talert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        gentlist=new ArrayList<>();
        appsort=new ArrayList<>();
        remsort=new ArrayList<>();
        finallist=new ArrayList<>();
        pg=new ProgressDialog(MainActivity.this);
        talert=findViewById(R.id.alertval);

        mylist=findViewById(R.id.mylistview);
        setTitle("Apps");
        if(isConnected())
        {
            new GetAsyncList(MainActivity.this).execute("https://rss.itunes.apple.com/api/v1/us/ios-apps/top-grossing/all/50/explicit.json");
        }
        else
        {
            Toast.makeText(this,"No Internet",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void handlelist(final ArrayList<Apps> apps) {
        pg.dismiss();
        applist=apps;
       // finallist=applist;
        gentlist=apps.get(49).getForlist();
        Log.d("demo","All  values="+gentlist);

        for(int i = 0; i < gentlist.size(); i++) {
            for(int j = i + 1; j < gentlist.size(); j++) {
                if(gentlist.get(i).equalsIgnoreCase(gentlist.get(j))){
                    gentlist.remove(j);
                    j--;
                }
            }
        }
        Collections.sort(gentlist);
        findViewById(R.id.filterbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Genre")
                        .setCancelable(false)
                       .setItems(gentlist.toArray(new CharSequence[gentlist.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("demo","Selected "+ gentlist.get(i));
                        appsort.clear();
                        remsort.clear();
                        finallist.clear();
                        String srchtxt=gentlist.get(i);
                        talert.setText(srchtxt);

                        for(int k=0;k<applist.size();k++)
                        {
                            if(applist.get(k).getGenlist().toString().toLowerCase().contains(srchtxt.toLowerCase()))
                            {
                                appsort.add(applist.get(k));
                            }
                            else
                            {
                                remsort.add(applist.get(k));
                            }
                        }
                        finallist.addAll(appsort);
                        finallist.addAll(remsort);
                        ListAdapter adapter=new ListAdapter(MainActivity.this,R.layout.layout_apps,finallist);
                        mylist.setAdapter(adapter);
                        //adapter.notifyDataSetChanged();
                    }
                });
                builder.create();
                builder.show();
            }
        });

        Log.d("demo","sort valuesvalues="+gentlist);
          ListAdapter adapter=new ListAdapter(MainActivity.this,R.layout.layout_apps,apps);
        mylist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=new Intent(MainActivity.this,SecondActivity.class);
                Apps aps=applist.get(i);
                in.putExtra(MAIN_KEY,aps);
                startActivity(in);
            }
        });

    }

    @Override
    public void initprog() {
        pg.setTitle("Loading Apps..");
        pg.setCancelable(false);
        pg.show();
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
