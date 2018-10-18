package inclass02.poorna.com.group4_inclass10;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends SharedClass {

    EditText edemail, edpwd;
    String token;
    int userid;
    String finalname;
    public static final String mypreference = "mypref";
    public static final String FIRST_KEY = "first";
    public static final String SECOND_KEY = "second";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Chat Room");


        sharedPreferences = getSharedPreferences(getString(R.string.pref), MODE_PRIVATE);
        if (sharedPreferences.getAll().size() != 0) {
            String emailparam = getToken("Email");
            String passwordparam = getToken("Password");
            performLogin(emailparam, passwordparam);
        }

        setContentView(R.layout.layout_login);
        edemail = findViewById(R.id.logemail);
        edpwd = findViewById(R.id.logpwd);

        findViewById(R.id.sign1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(in);
            }
        });

        findViewById(R.id.log1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edemail.getText().toString().length() != 0 && edpwd.getText().toString().length() != 0) {
                    saveToken("Email", edemail.getText().toString());
                    saveToken("Password", edpwd.getText().toString());
                    performLogin(edemail.getText().toString(), edpwd.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "Please Enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void performLogin(final String username, final String password) {
        if (isConnected()) {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", username)
                    .add("password", password)
                    .build();
            final Request request = new Request.Builder()
                    .url("http://ec2-54-91-96-147.compute-1.amazonaws.com/api/login")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String responsestr = response.body().string();
                    Gson gson = new Gson();
                    TokenResponse tokenResponse = gson.fromJson(responsestr, TokenResponse.class);
                    token = tokenResponse.getToken();
                    if (token == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                deleteToken(token);
                                Toast.makeText(getBaseContext(), "Incorrect Credentials", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } else {
                        userid = tokenResponse.getUser_id();
                        finalname = tokenResponse.getUser_fname() + " " + tokenResponse.getUser_lname();
                        saveToken("FinalName", finalname);
                        saveToken("UserId", String.valueOf(userid));
                        saveToken("Token", token);
                        Intent in = new Intent(getBaseContext(), ThreadActivity.class);
                        startActivity(in);
                        finishAffinity();
                    }
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

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
