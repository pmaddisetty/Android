package inclass02.poorna.com.internetconn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        findViewById(R.id.chk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked())
                {
                    //new GetDataAsync().execute("http://api.theappsdr.com/simple.php");
                    Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_SHORT).show();
//                    RequestParams reqpar=new RequestParams();
//                    reqpar.addParams("name","Bob Smith")
//                            .addParams("age","21")
//                            .addParams("email","bob@test.edu")
//                            .addParams("password","ajjnd;k");
//                   // new GetDataParamsUsingGetAync(reqpar).execute("http://api.theappsdr.com/params.php");
//                    new GetDataParamsUsingPostAync(reqpar).execute("http://api.theappsdr.com/params.php");
                    new GetImageAync((ImageView) findViewById(R.id.imageView)).execute("https://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png");
                }
                else {
                    Toast.makeText(MainActivity.this, "Not connected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isChecked(){
        ConnectivityManager cn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cn.getActiveNetworkInfo();
        if(net==null || !net.isConnected()||
        (net.getType()!=cn.TYPE_WIFI && net.getType()!=cn.TYPE_MOBILE))
        {
            return false;
        }
        return true;
    }
    private class GetDataAsync extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection=null;
            StringBuilder sb=new StringBuilder();
            InputStream inputstream;
            BufferedReader br=null;
            String result=null;
            try {
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection)url.openConnection();
              //  inputstream=connection.getInputStream();
           /*      br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                while ((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }*/
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    //result=  sb.toString();
                    result= IOUtils.toString(connection.getInputStream(),"UTF-8");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(br!=null)
                {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("demo",s);
        }
    }
    private class GetDataParamsUsingGetAync extends AsyncTask<String,Void,String>
    {
        RequestParams mparams;
        public GetDataParamsUsingGetAync(RequestParams rp)
        {
            mparams=rp;
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection=null;
            StringBuilder sb=new StringBuilder();;
            BufferedReader br=null;
            String result=null;
            try {
                URL url=new URL(mparams.getEncodedUrl(strings[0]));
                Log.d("demo",url.toString());
                connection=(HttpURLConnection)url.openConnection();
                String line="";
                br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {result=  sb.toString();}
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(br!=null)
                {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
        protected void onPostExecute(String s) {
            Log.d("demo",s);
        }
    }

    private class GetDataParamsUsingPostAync extends AsyncTask<String,Void,String>
    {
        RequestParams mparams;
        public GetDataParamsUsingPostAync(RequestParams rp)
        {
            mparams=rp;
        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection=null;
            StringBuilder sb=new StringBuilder();;
            BufferedReader br=null;
            String result=null;
            try {
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                mparams.encodebody(connection);
                String line="";
                br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine())!=null)
                {
                    sb.append(line+"\n");
                }
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {result=  sb.toString();}
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
                if(br!=null)
                {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        }
        protected void onPostExecute(String s) {
            Log.d("demo",s);
        }
    }

    private class GetImageAync extends AsyncTask<String,Void,Void> {
        ImageView imgv;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        public GetImageAync(ImageView imageView)
        {
            imgv=imageView;
        }

        @Override
        protected Void doInBackground(String... strings) {

            try {
                URL url=new URL(strings[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
                {
                    bitmap= BitmapFactory.decodeStream(connection.getInputStream());
                }

            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(connection!=null)
                {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(bitmap!=null && imgv!=null )
            {
                imgv.setImageBitmap(bitmap);
            }
        }
    }
}
