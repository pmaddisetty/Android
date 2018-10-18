package inclass02.poorna.com.group4_inclass04;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {
    SeekBar sb1,sb2;
    ProgressDialog pg;
    TextView tup1,tup2,tup3;
    Handler hand;
    ExecutorService threadpool;
    CharSequence[] arr1,arr2;
    int count,length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Inclass4");
        sb1=(SeekBar)findViewById(R.id.s1);
        sb2=(SeekBar)findViewById(R.id.s2);
        tup1=(TextView) findViewById(R.id.pwdtext);
        tup2=(TextView)findViewById(R.id.lengthtxt);
        tup3=(TextView) findViewById(R.id.finaltxt);
        //sb2.setProgress(8);

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i<1)
                {
                    seekBar.setProgress(1);
                    tup1.setText(1+"");
                }
                else
                {
                    tup1.setText(i+"");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i<8)
                {
                    seekBar.setProgress(8);
                    tup2.setText(8+"");
                }
                else
                {
                    tup2.setText(i+"");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hand=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                switch(message.what)
                {
                    case SecondActivity.Dowork.STATUS_START :
                        pg=new ProgressDialog(SecondActivity.this);
                        pg.setMessage("Displaying the passwords");
                        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pg.setMax(100);
                        pg.setCancelable(false);
                        pg.setProgress(0);
                        pg.show();
                        break;
                    case SecondActivity.Dowork.STATUS_PROGRESS:
                        pg.setProgress(message.getData().getInt(SecondActivity.Dowork.PROGRESS_KEY));
                        Log.d("demo","In progressp.."+message.getData().getInt(SecondActivity.Dowork.PROGRESS_KEY));
                        break;
                    case SecondActivity.Dowork.STATUS_STOP:
                        arr1=message.getData().getCharSequenceArray(SecondActivity.Dowork.VALUE_KEY);
                        AlertDialog.Builder builder= new AlertDialog.Builder(SecondActivity.this)
                                .setTitle("Select the password")
                                .setCancelable(false)
                                .setItems(arr1, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                tup3.setText(arr1[i]+"");
                                            }
                                        });
                        builder.create();
                        builder.show();
                        pg.dismiss();
                        Log.d("demo","Stop");
                        break;

                }
                return false;
            }
        });

        findViewById(R.id.thr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=sb1.getProgress();
                length=sb2.getProgress();
                if(count==0||length==0)
                {
                Toast.makeText(SecondActivity.this,"Please select values",Toast.LENGTH_SHORT).show();
                }
                else {
                    threadpool = Executors.newFixedThreadPool(2);
                    threadpool.execute(new SecondActivity.Dowork(count, length));
                }


            }
        });

        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count=sb1.getProgress();
                length=sb2.getProgress();
                if(count==0||length==0)
                {
                    Toast.makeText(SecondActivity.this,"Please select values",Toast.LENGTH_SHORT).show();
                }
            else {
                    new SecondActivity.DoworAsy().execute(count, length);
                }
            }
        });
    }

    class DoworAsy extends AsyncTask<Integer,Integer,CharSequence[]>
    {
        @Override
        protected void onPreExecute() {
            pg=new ProgressDialog(SecondActivity.this);
            pg.setMessage("Displaying the passwords");
            pg.setMax(100);
            pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pg.setCancelable(false);
            pg.show();
        }

        @Override
        protected void onPostExecute(final CharSequence[] charSequences) {
            AlertDialog.Builder builder= new AlertDialog.Builder(SecondActivity.this)
                    .setTitle("Select the password")
                    .setCancelable(false)
                    .setItems(charSequences, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            tup3.setText(charSequences[i]+"");
                        }
                    });
            builder.create();
            builder.show();
            pg.dismiss();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            pg.setProgress(values[0]);
        }

        @Override
        protected CharSequence[] doInBackground(Integer... integers) {

            int count=integers[0];
            int length=integers[1];
            CharSequence[] array=new CharSequence[count];
            String str;
            for(int i=0;i<count;i++)
            {
                str=Util.getPassword(length);
                array[i]=str;
                Log.d("demo","array="+array[i]);
                int val=(int) (((i+1)/ (float) count) * 100);
                publishProgress(val);
            }
            return array;
        }
    }

    class Dowork implements Runnable
    {
        int count,length;
        Dowork(int c,int l)
        {
            count=c;
            length=l;
        }
        static final int STATUS_START=0x00;
        static final int STATUS_PROGRESS=0x01;
        static final int STATUS_STOP=0x02;
        static final String PROGRESS_KEY="progress";
        static final String VALUE_KEY="value";

        String str;
        @Override
        public void run() {

            Message strtmsg=new Message();
            strtmsg.what=STATUS_START;
            hand.sendMessage(strtmsg);
            CharSequence[] array=new CharSequence[count];

            for(int i=0;i<count;i++) {

                str=Util.getPassword(length);
                array[i]=str;
                Log.d("demo","array value"+array[i]);
                int val=(int) (((i+1)/ (float) count) * 100);
                Message msg=new Message();
                msg.what=STATUS_PROGRESS;
                Bundle bd=new Bundle();
                bd.putInt(PROGRESS_KEY,val);
                msg.setData(bd);
                hand.sendMessage(msg);
            }

            Message stopmsg=new Message();
            stopmsg.what=STATUS_STOP;
            Bundle bd=new Bundle();
            bd.putCharSequenceArray(VALUE_KEY,array);
            stopmsg.setData(bd);
            hand.sendMessage(stopmsg);


        }
    }

}

