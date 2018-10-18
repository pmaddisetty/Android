package inclass02.poorna.com.group4_inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageActivity extends SharedClass {
ListView mylist;
TextView tvtitle;
String token,threadname;
MessageAdapter adapter;

    RecyclerView rview;
    MessageAdapterRecycler radapter;
ArrayList<MessageResponse.Messages> msgList,mlist2;
int thread_id;
int userid;
TextView uname, tname;
EditText edcontent;
ImageView imhome,imsend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Chatroom");
        rview=findViewById(R.id.myrecycler_view);
        LinearLayoutManager rlayoutmanag=new LinearLayoutManager(MessageActivity.this,LinearLayoutManager.VERTICAL,false);
        rview.setLayoutManager(rlayoutmanag);
        rview.setHasFixedSize(true);

       // mylist=findViewById(R.id.mymsglist);
        tname=findViewById(R.id.thrtitle_message);
        imhome=findViewById(R.id.home_message);
        imsend=findViewById(R.id.send_message);
        edcontent=findViewById(R.id.content_message);

        token=getToken("Token");
        threadname=getToken("ThreadName");
        thread_id=Integer.valueOf(getToken("ThreadId").trim());
        userid=Integer.valueOf(getToken("UserId").trim());
        tname.setText(threadname);
        Log.d("demo","token="+token);
        Log.d("demo","thr id="+thread_id);
        Log.d("demo","title="+threadname);
        Log.d("demo","userid="+userid);

        LoadMessages();
        imhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MessageActivity.this,ThreadActivity.class);
                startActivity(in);
            }
        });
        imsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                    if(edcontent.getText().length() != 0){
                        String msg= edcontent.getText().toString();
                        addMessage(msg,thread_id,token);
                        edcontent.setText("");

                    }
                    else{
                        Toast.makeText(MessageActivity.this, "Please enter Your Message", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MessageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
//    public String getToken(String key)
//    {
//        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
//        String val=sharedPreferences.getString(key,"");
//        Log.d("demo","retunred val"+val);
//        return val;
//    }
    public void LoadMessages()
    {
        if(isConnected()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/messages/" + thread_id)
                    .header("Authorization", "BEARER " + token)
                    .build();
            Log.d("demo", "thread load value=" + request);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    MessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            Toast.makeText(MessageActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String msgresponsestring = response.body().string();
                        Log.d("demo", "messages=" + msgresponsestring);
                        Gson gson = new Gson();
                        MessageResponse msgResponse = gson.fromJson(msgresponsestring, MessageResponse.class);
                        msgList = msgResponse.getMessages();
                        // Collections.sort(msgList,new StringDateComparator());
                        Collections.reverse(msgList);
                        setAdapter(msgList);

                    }
                }
            });
        }
        else{
            Toast.makeText(MessageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }
    public void setAdapter(ArrayList<MessageResponse.Messages> mmlist)
    {
        mlist2=mmlist;
        Log.d("demo","thread size="+mlist2.size());
        MessageActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //adapter = new MessageAdapter(MessageActivity.this, userid, mlist2);
                radapter=new MessageAdapterRecycler(MessageActivity.this, userid, mlist2);
                //adapter.notifyDataSetChanged();
                rview.setAdapter(radapter);


            }
        });

    }
    public  void addMessage(String message, int thrid,String tokenval)
    {
        if(isConnected()) {


            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("message", message)
                    .add("thread_id", String.valueOf(thrid).trim())
                    .build();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/add")
                    .header("Authorization", "BEARER " + tokenval)
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    MessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            Toast.makeText(MessageActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        LoadMessages();
                    } else {
                        Log.d("demo", "onResponse:token value= failure result");
                    }
                }
            });
        }
        else {
            Toast.makeText(MessageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }
    public void removeMessage(MessageResponse.Messages msg)
    {
        if(isConnected()) {
            MessageResponse.Messages mesg = msg;
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/message/delete/" + mesg.getId())
                    .header("Authorization", "BEARER " + token)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    MessageActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            Toast.makeText(MessageActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        LoadMessages();
                        Log.d("demo", "success ");
                    } else {
                        Log.d("demo", "else ");
                    }
                }
            });
        }
        else {
            Toast.makeText(MessageActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    class StringDateComparator implements Comparator<MessageResponse.Messages>
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date d1,d2;
        int val;
        public int compare(MessageResponse.Messages m1, MessageResponse.Messages m2) {

            try {
                 val =(dateFormat.parse(m1.getCreated_at())).compareTo(dateFormat.parse(m2.getCreated_at()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            try {
//                 d1=dateFormat.parse(m1.getCreated_at());
//                 Log.d("demo","date d1="+d1);
//                 d2=dateFormat.parse(m2.getCreated_at());
//                Log.d("demo","date d1="+d2);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            if (d1 == null || d2 == null)
//                return 0;

            return val;
        }
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }


}
