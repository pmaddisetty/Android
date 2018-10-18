package inclass02.poorna.com.group4_hw03;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
ProgressBar pb;
int prog;
TextView ta1;
ArrayList<Question> qnewlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Stats Activity");
        pb=findViewById(R.id.progressBar);
        ta1=findViewById(R.id.correctPercentage);
        if(getIntent()!=null&& getIntent().getExtras()!=null)
        {
            qnewlist=(ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.MAIN_KEY);
            Log.d("demo","qnewlist="+qnewlist.toString());
            prog=getIntent().getExtras().getInt(SecondActivity.SECOND_KEY);
            Log.d("demo","progress="+prog);
            pb.setProgress(prog);
            ta1.setText(prog+"%");

           // textView1.setTextColor(Color.parseColor("#F5DC49"));
        }
        findViewById(R.id.statQuit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(ThirdActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
        findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    Intent inten = new Intent(ThirdActivity.this, SecondActivity.class);
                    inten.putExtra(MainActivity.MAIN_KEY, qnewlist);
                    startActivity(inten);
                }
                else{
                    Toast.makeText(ThirdActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();

                }

            }
        });
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
