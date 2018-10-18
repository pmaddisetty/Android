package inclass02.poorna.com.testthread;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
ExecutorService threadpool;
Handler han;
ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
//        pg=new ProgressDialog(this);
//        pg.setMessage("updating progress");
//        pg.setMax(100);
//        pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        pg.setCancelable(false);
//        han=new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message message) {
//               // Log.d("demo","message received"+message.obj);
//                switch(message.what)
//                {
//                    case Dowork.STATUS_START :
//                        pg.setProgress(0);
//                        pg.show();
//                        Log.d("demo","starting..");
//                        break;
//                    case Dowork.STATUS_PROGRESS:
//                        pg.setProgress(message.getData().getInt(Dowork.PROGRESS_KEY));
//                        Log.d("demo","In progressp.."+message.getData().getInt(Dowork.PROGRESS_KEY));
//                        break;
//                    case Dowork.STATUS_STOP:
//                        pg.dismiss();
//                        Log.d("demo","Stop");
//                        break;
//
//                }
//                return false;
//            }
//        });
       // threadpool= Executors.newFixedThreadPool(4);

//        findViewById(R.id.bth).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Thread tr=new Thread(new Dowork(),"Worker 1");
//               tr.start();
//               threadpool.execute(new Dowork());
//
//            }
//        });
//        new Thread(new Dowork(),"thread 1").start();
        new DoworAsy().execute(1000000);
    }

    class DoworAsy extends AsyncTask<Integer,Integer,Double>
    {

        @Override
        protected void onPreExecute() {
            pg=new ProgressDialog(MainActivity.this);
            pg.setMessage("Updating progress");
            pg.setMax(100);
            pg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pg.setCancelable(false);
            pg.show();
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            Log.d("demo","out value is"+aDouble);
            pg.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        pg.setProgress(values[0]);
        }

        @Override
        protected Double doInBackground(Integer... integers) {
            double count=0;
            Random r = new Random();
            double avg=0;
            for(int i=0;i<100;i++)
            {
                for(int j=0;j<integers[0];j++) {
                    count=count+1;
                    avg = r.nextDouble() + avg;
                }
                publishProgress(i+1);
            }
            return avg/count;
        }
    }




    class Dowork implements Runnable
    {
        static final int STATUS_START=0x00;
        static final int STATUS_PROGRESS=0x01;
        static final int STATUS_STOP=0x02;
        static final String PROGRESS_KEY="progress";

        @Override
        public void run() {
           // Log.d("demo","worker started...");
            Message startmsg=new Message();
            startmsg.what=STATUS_START;
            han.sendMessage(startmsg);
            for(int i=0;i<100;i++)
            {
                for(int j=0;j<10000000;j++)
                {

                }
                Message msg=new Message();
                msg.what=STATUS_PROGRESS;
                Bundle bd=new Bundle();
                bd.putInt(PROGRESS_KEY,(Integer)i);
                msg.setData(bd);
               // msg.obj=(Integer)i;
                han.sendMessage(msg);

               // Message mes=new Message();
               // mes.what=i;
                //mes.obj=(Integer)i;
               // han.sendMessage(mes);
                //han.sendEmptyMessage(100);
            }
            Message stopmsg=new Message();
            stopmsg.what=STATUS_STOP;
            han.sendMessage(stopmsg);
         //   Log.d("demo","worker ended..");
        }
    }
}
