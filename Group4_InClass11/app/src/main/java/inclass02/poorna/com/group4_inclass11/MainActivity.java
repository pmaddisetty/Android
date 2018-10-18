package inclass02.poorna.com.group4_inclass11;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    ArrayList<GalleryResponse.Galleryimage> imagelist = new ArrayList<>();
    ArrayList<ArrayList<GalleryResponse.Displaysize>> displaysizeArrayList = new ArrayList<ArrayList<GalleryResponse.Displaysize>>();
    ArrayList<String> urilist = new ArrayList<>();
    ImageView imv;
    TextView imgTitle;
    EditText edsearch;
    Integer index = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        edsearch = findViewById(R.id.title);
        imv = findViewById(R.id.image);
        imgTitle = findViewById(R.id.imagetitle);

        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edsearch.getText().toString().trim().length() != 0) {
                     urilist.clear();
                    index = 0;
                    imv.setVisibility(View.VISIBLE);
                    generateImage(edsearch.getText().toString().trim());
                    edsearch.setText("");
                    imgTitle.setText("");



                } else {
                    edsearch.setText("");
                    imgTitle.setText("");
                    Toast.makeText(MainActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                    imv.setVisibility(View.INVISIBLE);
                }
            }
        });


    }


    public void generateImage(final String keyword) {
        if (isConnected()) {

            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .url("https://api.gettyimages.com/v3/search/images?phrase=" + keyword)
                    .header("Api-Key", "hu4fp76qa53mqctpgm65vph6")
                    .build();

            Log.d("demo", "url=https://api.gettyimages.com/v3/search/images?phrase=" + keyword);
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responsestring = response.body().string();
                        Log.d("demo", "resp=" + responsestring);
                        Gson gson = new Gson();
                        GalleryResponse galleryResponse = gson.fromJson(responsestring, GalleryResponse.class);
                        imagelist = galleryResponse.getImages();
                        Log.d("demo", "imaglist size=" + imagelist.size());
                        ArrayList<GalleryResponse.Displaysize> disp = new ArrayList<>();
                        urilist = new ArrayList<>();
                        if (imagelist.size() != 0) {
                            for (int i = 0; i < imagelist.size(); i++) {
                                disp = imagelist.get(i).getDisplay_sizes();
                                Log.d("demo", "disp=" + disp.size() + "disp= " + disp);
                                String urlval = disp.get(0).getUri();
                                urilist.add(urlval);
                                Log.d("demo", "string vale=" + urilist);
                                Log.d("demo", "urilist=" + urilist);
                                disp.clear();
                            }
                        }

                        Log.d("demo", "url value=" + urilist + " " + urilist.size());
                        MainActivity.this.runOnUiThread(new Runnable() {

                            @SuppressLint("ClickableViewAccessibility")
                            @Override
                            public void run() {

                                if (urilist.size() == 0) {
                                    edsearch.setText("");
                                    Picasso.with(MainActivity.this).load(R.drawable.noimage).into(imv);
                                    Toast.makeText(MainActivity.this, "No images found", Toast.LENGTH_SHORT).show();
                                    urilist.clear();
                                    index = -1;


                                } else {
                                    imgTitle.setText("Results for : "+ keyword);
                                    Picasso.with(MainActivity.this).load(urilist.get(index)).placeholder(R.drawable.loading).into(imv);
                                    imv.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {

                                        public void onSwipeRight() {
                                            if (index == -1) {
                                                Toast.makeText(MainActivity.this, "No Images", Toast.LENGTH_SHORT).show();

                                            } else if (urilist.size() == 1) {
                                                Toast.makeText(MainActivity.this, "No further Images", Toast.LENGTH_SHORT).show();

                                            } else if (index == urilist.size() - 1) {
                                                index = 0;
                                                Picasso.with(MainActivity.this).load(urilist.get(index)).placeholder(R.drawable.loading).into(imv);

                                            } else {
                                                index = index + 1;
                                                Picasso.with(MainActivity.this).load(urilist.get(index)).placeholder(R.drawable.loading).into(imv);
                                            }
                                        }

                                        public void onSwipeLeft() {
                                            if (index == -1) {
                                                Toast.makeText(MainActivity.this, "No Images", Toast.LENGTH_SHORT).show();

                                            } else if (urilist.size() == 1) {
                                                Toast.makeText(MainActivity.this, "No further Images", Toast.LENGTH_SHORT).show();

                                            } else if (index == 0) {
                                                index = urilist.size() - 1;
                                                Picasso.with(MainActivity.this).load(urilist.get(index)).placeholder(R.drawable.loading).into(imv);
                                            } else {
                                                index = index - 1;
                                                Picasso.with(MainActivity.this).load(urilist.get(index)).placeholder(R.drawable.loading).into(imv);

                                            }
                                        }

                                    });
                                }

                            }
                        });

                    } else {
                        Toast.makeText(MainActivity.this, "No Images Found", Toast.LENGTH_SHORT).show();
                        edsearch.setText("");
                        Picasso.with(MainActivity.this).load(R.drawable.noimage).into(imv);
                    }
                }
            });
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem homeItem = (MenuItem) menu.findItem(R.id.home);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        edsearch.setText("");
        imgTitle.setText("");
        Picasso.with(MainActivity.this).load(R.drawable.square).placeholder(R.drawable.square).into(imv);
        urilist.clear();
        imv.setVisibility(View.INVISIBLE);
        return true;
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
