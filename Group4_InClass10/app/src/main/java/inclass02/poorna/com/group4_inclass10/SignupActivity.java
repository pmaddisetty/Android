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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignupActivity extends AppCompatActivity {
    EditText fname, lname,email,pwd,pwd2;
   // public static final String Token = "tokenKey";
    String token,finalname;
    int userid;
    Button signup;
    String signupemail,signuppassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        pwd= findViewById(R.id.passwd);
        pwd2=findViewById(R.id.repeatpasswd);
        signup=findViewById(R.id.sign2_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (lname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                }
                else if(fname.getText().toString().equals(lname.getText().toString())){
                    Toast.makeText(SignupActivity.this, "First Name and Last Name should not be the same", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (pwd.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please choose password", Toast.LENGTH_SHORT).show();
                } else if (pwd2.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please Re-enter the password again to verify", Toast.LENGTH_SHORT).show();
                } else if (!(pwd.getText().toString().equals(pwd2.getText().toString()))) {
                    Toast.makeText(SignupActivity.this, "Please enter matching passwords", Toast.LENGTH_SHORT).show();
                } else if (pwd.getText().toString().length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password should be of minimum six characters", Toast.LENGTH_SHORT).show();
                }  else if( !(email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    Log.d("demo","entered validation");
                    Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }
                else {

                    callaftersign(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), pwd.getText().toString());
                }
            }
        });
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });


    }
    public void callaftersign(String name, String lastname, String metemail, final String metpwd )
    {
        if(isConnected()) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("email", metemail)
                    .add("password", metpwd)
                    .add("fname", name)
                    .add("lname", lastname)
                    .build();
            final Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/signup")
                    .post(requestBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.getMessage();
                    SignupActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("demo", "failure");
                            signup.setError("Sign up failed");
                           // Toast.makeText(SignupActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {

                        String responseString = response.body().string();
                        Log.d("demo", "response=" + responseString);
                        Gson gson = new Gson();
                        TokenResponse tokenResponse = gson.fromJson(responseString, TokenResponse.class);
                        token = tokenResponse.getToken();
                        userid = tokenResponse.getUser_id();
                        finalname = tokenResponse.getUser_fname() + " " + tokenResponse.getUser_lname();
                        signupemail = tokenResponse.getUser_email();
                        signuppassword = metpwd;
                        Log.d("demo", "met password in signup=" + signuppassword);
                        saveToken("Email", signupemail);
                        saveToken("Password", signuppassword);
                        saveToken("FinalName", finalname);
                        saveToken("Token", token);
                        saveToken("UserId", String.valueOf(userid));
                        Intent in = new Intent(SignupActivity.this, ThreadActivity.class);
                        //in.putExtra(MainActivity.SECOND_KEY,tokenResponse);
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this, "User has been created", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(in);
                        finishAffinity();
                    }
                    else
                    {
                        SignupActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this, "User Email already exists", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
        else{
            Toast.makeText(SignupActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }
    public void saveToken(String key,String val)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        SharedPreferences.Editor e=sharedPreferences.edit();
        e.putString(key,val);
        e.commit();
    }

    public String getToken(String key)
    {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        String val=sharedPreferences.getString(key,"");
        Log.d("demo","retunred val"+val);
        return val;
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
