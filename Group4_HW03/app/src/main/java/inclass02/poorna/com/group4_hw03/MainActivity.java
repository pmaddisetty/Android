package inclass02.poorna.com.group4_hw03;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAyncQue.iUrl  {
ImageView imgv;
Button btriv;
TextView ta;
ProgressDialog pg;
ArrayList<Question> questionlist;
static String MAIN_KEY="main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Main Activity");
        questionlist=new ArrayList<Question>();
       imgv= findViewById(R.id.imgtrivia);
       imgv.setVisibility(View.INVISIBLE);
       btriv=findViewById(R.id.strtButtn);
       ta=findViewById(R.id.readyText);
       btriv.setEnabled(false);
       if(isConnected()) {

           new GetAyncQue(MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");


               findViewById(R.id.strtButtn).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       if(isConnected()) {
                           Intent in = new Intent(MainActivity.this, SecondActivity.class);
                           in.putExtra(MAIN_KEY, questionlist);
                           startActivity(in);
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
            Toast.makeText(MainActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
        }
       findViewById(R.id.exitbtn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finishAffinity();
           }
       });

    }

    @Override
    public void initprog() {
        pg=new ProgressDialog(MainActivity.this);
        pg.setTitle("Loading Trivia");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    public void displprog() {

    }

    @Override
    public void handlelist(ArrayList<Question> qstlist) {
        if(qstlist!=null)
        {
            questionlist=qstlist;
            pg.dismiss();
//            Log.d("demo","List received in main");
//            Log.d("demo","list items in 1 obj="+questionlist.get(1).getIndex());
//            Log.d("demo","list items in 1 answer="+questionlist.get(1).getAnswer());
//            Log.d("demo","list items in 1 que="+questionlist.get(1).getQuestion());
//            Log.d("demo","list items in 1 url="+questionlist.get(1).getUrl());
//            Log.d("demo","list items in 1 opt="+questionlist.get(1).getOptlist());

            imgv.setVisibility(View.VISIBLE);
            btriv.setEnabled(true);
            ta.setText("Trivia Ready ");

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
