package inclass02.poorna.com.group4_inclass04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Launcher Activity");
         Handler handler= new Handler();
         handler.postDelayed(new Runnable() {
             @Override
             public void run() {
                 Intent i=new Intent(MainActivity.this,SecondActivity.class);
                 startActivity(i);
             }
         },3000);

    }
}