package inclass02.poorna.com.threadinterfacing;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements GetTweetAsync.iData {
LinkedList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new GetTweetAsync(MainActivity.this).execute();
               // new GetTweetAsync()
            }
        });
    }
    public void handledata(LinkedList<String> st)
    {
       data=st;
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Tweet");
        builder.setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }

    @Override
    public void handlelistDatainInterfaces(LinkedList<String> strings) {
        data=strings;
        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choose Tweet");
        builder.setItems(data.toArray(new CharSequence[data.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
}
