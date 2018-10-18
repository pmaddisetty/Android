package inclass02.poorna.com.okhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class SignupActivity extends AppCompatActivity {
EditText fname, lname,email,pwd;
    //EditText sname, lname,email,pwd;
    public static final String EMAIL_KEY = "email";
    public static final String FNAME_KEY = "fname";
    public static final String LNAME_KEY = "lname";
    public static final String PWD_KEY = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        pwd= findViewById(R.id.pwd);
        findViewById(R.id.sign_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callaftersign(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),pwd.getText().toString());

            }
        });

    }

    public void callaftersign(String name,String lastname,String metemail,String metpwd )
    {

        Log.d("demo", "methd success");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody= new FormBody.Builder()
                .add(FNAME_KEY, name)
                .add(LNAME_KEY, lastname)
                .add(PWD_KEY, metpwd)
                .add(EMAIL_KEY, metemail)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/signup")
                .post(requestBody)  // Use PUT on this line.
                .build();
        Log.d("demo", "Request success");

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.getMessage();
                Log.d("demo", "req fail1");
                SignupActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("demo", "req fail");
                        Toast.makeText(SignupActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    Log.d("demo", "signup success");
                    Log.d("demo", "response="+responseString);
                }
            }
        });

    }
}
