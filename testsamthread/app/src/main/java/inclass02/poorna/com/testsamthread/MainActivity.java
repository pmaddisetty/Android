package inclass02.poorna.com.testsamthread;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
ProgressDialog pg;
int progress;
ExecutorService threadpool;
TextView tup,tup1;
    SeekBar sb;
    Handler hand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
         sb=(SeekBar) findViewById(R.id.seekload);

        pg=new ProgressDialog(MainActivity.this);
        pg.setMessage("Retrieving the value");
        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pg.setMax(100);
        pg.setCancelable(false);

        tup=(TextView)findViewById(R.id.workload);
        tup1=(TextView)findViewById(R.id.finalval);


        hand=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                switch(message.what)
                {
                    case Dowork.STATUS_START :
                        pg.setProgress(0);
                        pg.show();
                        Log.d("demo","starting..");
                        break;
                    case Dowork.STATUS_PROGRESS:
                        Log.d("demo","Entered status..");
                        pg.setProgress(message.getData().getInt(Dowork.PROGRESS_KEY));
                        Log.d("demo","In progressp.."+message.getData().getInt(Dowork.PROGRESS_KEY));
                        break;
                    case Dowork.STATUS_STOP:
                        double d=message.getData().getDouble(Dowork.VALUE_KEY);
                        tup1.setText(d+"");
                        pg.dismiss();
                        Log.d("demo","Stop");
                        break;

                }
                return false;
            }
        });


        findViewById(R.id.asynctask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress=sb.getProgress();
                new DoworAsy().execute(progress);
            }
        });
        findViewById(R.id.numthr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress=sb.getProgress();
                threadpool= Executors.newFixedThreadPool(2);
                threadpool.execute(new Dowork(progress));

            }
        });


    }

    class DoworAsy extends AsyncTask<Integer,Integer,Double>
    {

        @Override
        protected void onPreExecute() {
            pg=new ProgressDialog(MainActivity.this);
            pg.setMessage("Retreving the number..");
            pg.setMax(100);
            pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pg.setCancelable(false);
            pg.show();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Log.d("demo","out value is"+aDouble);
            tup1.setText(aDouble+"");
            pg.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pg.setProgress(values[0]);
        }

        @Override
        protected Double doInBackground(Integer... integers) {
            int n=integers[0];
            double sum=0;
            Log.d("demo","progress"+n);
            int count=0;

                for(int j=1;j<=n;j++) {
                    count++;
                    sum = HeavyWork.getNumber() + sum;
                    Log.d("demo","sum="+sum);
                    int val=(int) ((j / (float) n) * 100);
                    Log.d("demo","val="+val);
                    publishProgress(val);
                }

            return sum/count;
        }
    }

    class Dowork implements Runnable
    {
        int progress;
        int count=0,val;
        double sum=0.0,avg=0.0;
        Dowork(int prg)
        {
            Log.d("demo","val is"+prg);
             progress=prg;
        }
        static final int STATUS_START=0x00;
        static final int STATUS_PROGRESS=0x01;
        static final int STATUS_STOP=0x02;
        static final String PROGRESS_KEY="progress";
        static final String VALUE_KEY="value";
        @Override
        public void run() {

            Log.d("demo","worker started");
            Message strtmsg=new Message();
            strtmsg.what=STATUS_START;
            hand.sendMessage(strtmsg);

            for(int j=1;j<=progress;j++) {
                Log.d("demo","in for loop..");
                count++;
                sum = HeavyWork.getNumber() + sum;
                Log.d("demo","progress in loop..="+progress);
                val=(int) ((j / (float) progress) * 100);
                Message msg=new Message();
                msg.what=STATUS_PROGRESS;
                Bundle bd=new Bundle();
                bd.putInt(PROGRESS_KEY,val);
                msg.setData(bd);
                hand.sendMessage(msg);
            }
            avg=sum/count;
            Message stopmsg=new Message();
            stopmsg.what=STATUS_STOP;
            Bundle bd=new Bundle();
            bd.putDouble(VALUE_KEY,avg);
            stopmsg.setData(bd);
            hand.sendMessage(stopmsg);


        }
    }

}
