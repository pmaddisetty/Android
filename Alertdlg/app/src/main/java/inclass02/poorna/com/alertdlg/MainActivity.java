package inclass02.poorna.com.alertdlg;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE=100;
    public static final String VALUE_KEY="value";

    CharSequence[] items = {"red","blue","green","yellow"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

       final ProgressDialog prg= new ProgressDialog(this);
        prg.setMessage("Loading..");
        prg.setCancelable(false);

     /*   AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select one")
          .setCancelable(false)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                            Log.d("demo", items[i]+"was" + "checked");

                    }
                })
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("demo", "Ok clicked");
            }
        }); */



            /*    .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("demo","Selected "+ items[i]);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("demo", "Ok clicked");
                    }
                }); */



       //   .setMessage("Are you sre?")
       /*   .setItems(items, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  Log.d("demo","Selected "+ items[i]);
              }
          }); */
      /*    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  Log.d("demo", "Ok clicked");
              }
          })
          .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialogInterface, int i) {
                  Log.d("demo", "Cancel clicked");
              }
          })      ;*/


     //   final AlertDialog alert= builder.create();
        findViewById(R.id.button_alert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //alert.show();
             //   prg.show();
                Intent intent=new Intent(MainActivity.this, secondActivity.class);
                startActivityForResult(intent,REQ_CODE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("demo","enterd activity");
        Log.d("demo","value received is "+data.getExtras().getString(VALUE_KEY));
        if(requestCode==REQ_CODE)
        {
            if(resultCode==RESULT_OK)
            {
                String val=data.getExtras().getString(VALUE_KEY);
                Log.d("demo","value received is " +val);
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Log.d("demo","value received is none");
            }
        }
    }
}
