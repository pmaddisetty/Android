package inclass02.poorna.com.threaddemo;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAsynUrl.iData,GetAsyncImage.iImage{
    CharSequence[] keylist= {"UNCC","Android","Winter","Aurora","Wonders"};
    String selecteditem,url;
    ArrayList<String> data;
    ImageView iv;
    String s;
    int index=0;
    ProgressDialog pg;
    TextView tsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        iv=(ImageView) findViewById(R.id.mainimg);
       tsearch =(TextView)findViewById(R.id.searchtext);
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
                            url = ParamsUrl.getUrl("http://dev.theappsdr.com/apis/photos/index.php", selecteditem);
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
            if(data!=null) {
                if (index <= data.size()) {
                    Log.d("demo", "list size=" + data.size());
                    Log.d("demo", "index value is=" + index);
                    index = index + 1;
                    if (index == data.size()) {
                        index = 0;
                    }
                    new GetAsyncImage(iv, MainActivity.this).execute(data.get(index));

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
            if(data!=null) {
                if (index >= 0) {
                    if (index == 0) {
                        index = data.size();
                        //   s = data.get(index);
                    }
                    index = index - 1;
                    new GetAsyncImage(iv, MainActivity.this).execute(data.get(index));
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
    public void handlelistDatainInterfaces(ArrayList<String> st) {
    data=st;
    index=0;
    Log.d("demo","url in main="+s);
    new GetAsyncImage(iv,MainActivity.this).execute(data.get(index));

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

}
