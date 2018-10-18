package inclass02.poorna.com.threaddemo2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements GetAsynUrl.iData, GetAsyncImage.iImage{
TextView tsearch;
ImageView iv;
    CharSequence[] keylist= {"UNCC","Android","winter","aurora","wonders"};
    String selecteditem,url;
    HashMap<String,ArrayList<String>> hresmap;
    ArrayList<String> alist;
    ProgressDialog pg;
     int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        iv=(ImageView) findViewById(R.id.mainimg);
        tsearch =(TextView)findViewById(R.id.searchtext);
        alist=new ArrayList<String>();
        findViewById(R.id.searchbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Choose a Keyword");
                    builder.setItems(keylist, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            selecteditem = keylist[i].toString();
                            tsearch.setText(selecteditem);
                            //url = ParamsUrl.getUrl("http://dev.theappsdr.com/apis/photos/index.php", selecteditem);
                            url="http://dev.theappsdr.com/apis/spring_2016/inclass5/URLs.txt";
                            index=0;
                            new GetAsynUrl(MainActivity.this).execute(url);
                            Log.d("demo", "selected item" + selecteditem);
                        }
                    });
                    builder.create();
                    builder.show();
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
                if(alist!=null) {
                    if (index <= alist.size()) {
                        Log.d("demo", "list size=" + alist.size());
                        Log.d("demo", "index value is=" + index);
                        index = index + 1;
                        if (index == alist.size()) {
                            index = 0;
                        }
                      //  new GetAsyncImage(iv, MainActivity.this).execute(data.get(index));
                        new GetAsyncImage(iv,MainActivity.this).execute(alist.get(index));


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
                if(alist!=null) {
                    if (index >= 0) {
                        if (index == 0) {
                            index = alist.size();
                            //   s = data.get(index);
                        }
                        index = index - 1;
                       // new GetAsyncImage(iv, MainActivity.this).execute(data.get(index));
                        new GetAsyncImage(iv,MainActivity.this).execute(alist.get(index));

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
    public void handleurl(HashMap<String, ArrayList<String>> hmap) {
        hresmap=hmap;
        for(String key:hresmap.keySet())
        {
            Log.d("demo","key eah time"+key);
            if(key.equalsIgnoreCase(selecteditem.trim()))
            {
                alist=hmap.get(key.trim());
                Log.d("demo","key value="+key);
            }
        }
       // String s=alist.get(1);
        for(int i=0;i<alist.size();i++)
        {
            Log.d("demo","val is "+alist.get(i));

        }

        new GetAsyncImage(iv,MainActivity.this).execute(alist.get(0));

    }
    @Override
    public void initprog() {
        pg=new ProgressDialog(MainActivity.this);
        pg.setTitle("Loading image");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    public void displaprog() {

        pg.dismiss();
    }

    @Override
    public void handleImage(Bitmap bitmap) {

            iv.setImageBitmap(bitmap);
    }
}
