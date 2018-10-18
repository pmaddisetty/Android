package inclass02.poorna.com.group4_inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ThreadActivity extends SharedClass {
String tokenval;
TokenResponse tokenrespobj;
SharedPreferences sharedpreferences;
TextView tname;
ArrayList<ThreadResponse.MessageThread> threadlist;
ArrayList<ThreadResponse.MessageThread> threadfinallist;
ArrayList<String> titlelist;
ListView mylist;
EditText edThreadTitle;
ThreadAdapter adapter;
int userid;
String tokenOriginal, fnameorg,lnameorg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        setTitle("Message Threads");
        mylist=findViewById(R.id.mylistview);
        edThreadTitle=findViewById(R.id.add_text);
        //tokenval=getIntent().getExtras().getString(MainActivity.FIRST_KEY);
        sharedpreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        //tokenrespobj= (TokenResponse) getIntent().getExtras().getSerializable(MainActivity.SECOND_KEY);
        tokenOriginal=getToken("Token");
        userid=Integer.valueOf(getToken("UserId"));
            tname=findViewById(R.id.name_text);
            tname.setText(getToken("FinalName"));
            performThreadLoad(tokenOriginal);
            Log.d("demo","token in thread after shared="+tokenOriginal);



        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteToken(tokenval);
                //finish();
                Intent i=new Intent(ThreadActivity.this,MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected())
                {
                    if(edThreadTitle.getText().length() != 0){
                        String title= edThreadTitle.getText().toString();
                        Log.d("demo","title="+title);
                        Log.d("demo","token="+tokenOriginal);
                        addThread(title,tokenOriginal);
                        edThreadTitle.setText("");

                    }
                    else{
                        Toast.makeText(ThreadActivity.this, "Please enter Thread name", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(ThreadActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                }


            }
        });

    }
    public void addThread(String title, String tokenobj) {
        if (isConnected()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("title", title)
                    .build();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/add")
                    .header("Authorization", "BEARER " + tokenobj)
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    ThreadActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            Toast.makeText(ThreadActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.d("demo", "onResponse:token value=  " + tokenOriginal);
                        performThreadLoad(tokenOriginal);
                    } else {
                        Log.d("demo", "onResponse:token value= failure result");
                    }

                }
            });

        } else {
            Toast.makeText(ThreadActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }


    public void performThreadLoad(String tokenobj)
    {
        if(isConnected()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread")
                    .header("Authorization", "BEARER " + tokenobj)
                    .build();
            Log.d("demo", "thread load value=" + request);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(ThreadActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {

                        String responsestring = response.body().string();
                        Log.d("demo", "messageThreads=" + responsestring);
                        Gson gson = new Gson();
                        ThreadResponse threadResponse = gson.fromJson(responsestring, ThreadResponse.class);
                        threadfinallist = threadResponse.getThreads();
                        setAdapter(threadfinallist);
                        Log.d("demo", "MessageThread list=" + threadlist);
                        Log.d("demo", "onResponse: userid= " + userid);

                    } else {
                        Log.d("demo", "request failure poccurred");
                    }

                }
            });
        }
        else{
            Toast.makeText(ThreadActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    public void setAdapter(ArrayList<ThreadResponse.MessageThread> ttlist)
    {
        threadlist=ttlist;
        Log.d("demo","thread size="+threadlist.size());
        ThreadActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
               //
                adapter = new ThreadAdapter(ThreadActivity.this, userid, threadlist);
                adapter.notifyDataSetChanged();
                mylist.setAdapter(adapter);

            }
        });

    }

    public void removeThread(ThreadResponse.MessageThread thr)
    {

        ThreadResponse.MessageThread threadval=thr;
        if(isConnected()) {

            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/thread/delete/" + threadval.getId())
                    .header("Authorization", "BEARER " + tokenOriginal)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ThreadActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            Toast.makeText(ThreadActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        Log.d("demo", "token val=" + tokenOriginal);
                        performThreadLoad(tokenOriginal);
                        Log.d("demo", "success ");
                    } else {
                        Log.d("demo", "else ");
                    }
                }
            });
        }
        else{
            Toast.makeText(ThreadActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }

    public void getMessages(ThreadResponse.MessageThread thr)
    {
        saveToken("ThreadId",String.valueOf(thr.getId()));
        saveToken("ThreadName",thr.getTitle());
        Intent in=new Intent(ThreadActivity.this,MessageActivity.class);
        startActivity(in);
    }
//    public void saveToken(String key,String val)
//    {
//        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
//        SharedPreferences.Editor e=sharedPreferences.edit();
//        e.putString(key,val);
//        e.commit();
//    }
//
//    public String getToken(String key)
//    {
//        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
//        String val=sharedPreferences.getString(key,"");
//        Log.d("demo","retunred val"+val);
//        return val;
//    }
//    public void deleteToken(String tokenval)
//    {
//        SharedPreferences.Editor e=sharedpreferences.edit();
//        //e.remove(MainActivity.Token);
//        e.clear();
//        e.commit();
//    }
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

    @Override
    public void onBackPressed() {
      finishAffinity();
    }
}
