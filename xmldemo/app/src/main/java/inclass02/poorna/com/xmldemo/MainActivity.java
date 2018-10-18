package inclass02.poorna.com.xmldemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetAsyncImage.iImage {
    ProgressDialog pg;
    ArrayList<NewsItem> newslist;
    //ImageView imgarr;
    ArrayList<ImageView> imgarr;
    ArrayList<String> str;
    ArrayList<TextView> tvs;
    LinearLayout layout;
    ScrollView scroll;
    HorizontalScrollView hcscroll;
    ImageView img;
    TextView tv;
    int id=1000;
    int id2=7000;
    LinearLayout lin;
     LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
    ///  lin =new LinearLayout(MainActivity.this);
        //setContentView(lin);
        imgarr=new ArrayList<>();
        tvs=new ArrayList<>();
        str=new ArrayList<>();
        hcscroll=new HorizontalScrollView(this);

        linearLayout=(LinearLayout)findViewById(R.id.linlt);

        //layout=findViewById(R.id.linear);
        img=new ImageView(MainActivity.this);
      //  scroll=findViewById(R.id.scview);
        new GetAsyncImgList().execute("http://rss.cnn.com/rss/cnn_tech.rss");

    }

    @Override
    public void handleImage(Bitmap bitmap, int idto){
        int val=idto;

        Log.d("demo", "val in set="+val);
        Log.d("demo","arr size in handle="+imgarr.size());

        for(int i=0;i<newslist.size();i++)
        {

            LinearLayout l1= new LinearLayout(MainActivity.this);

            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setGravity(Gravity.LEFT);

            ImageView img2=new ImageView(MainActivity.this);
            //img2=imgarr.get(i);
            img2.setImageBitmap(bitmap);

            String title2=str.get(i);
            TextView tv2=new TextView(MainActivity.this);
            //tv2=tvs.get(i);
            tv2.setText(title2);
//            if (img2.getParent() != null) {
//                ((ViewGroup) img2.getParent()).removeView(img2);
//
//            }
//            if (tv2.getParent() != null) {
//                ((ViewGroup) tv2.getParent()).removeView(tv2);
//
//            }

           l1.addView(img2,new LinearLayout.LayoutParams(300, 200));
            l1.addView(tv2,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(l1);
                Log.d("demo", "img val str" + img.toString());
                Log.d("demo", "value in id after array=" + img.getId());

        }

    }
    public void imagedyn(ArrayList<NewsItem> news)
    {
        for(int i=0;i<newslist.size();i++)
                {

                    String imgurl=newslist.get(i).getMedia().getUrl();
                    img=new ImageView(MainActivity.this);
                    tv=new TextView(MainActivity.this);
                    img.setId(id);
                    imgarr.add(img);
                    String title=newslist.get(i).getTitle();
                    tv.setId(id2);
                    str.add(title);
                    Log.d("demo","size="+str.size());
                    Log.d("tit","title="+title);

                    tvs.add(tv);
                    Log.d("demo","value in call="+id);
                    Log.d("demo","arr size="+imgarr.size());

                    new GetAsyncImage(img,MainActivity.this,id).execute(imgurl);
                    id++;

                }

    }


    public class GetAsyncImgList extends AsyncTask<String, Integer, ArrayList<NewsItem>> {
        HttpURLConnection connection;
        String result;
        BufferedReader br;
        StringBuilder sb;
        ArrayList<NewsItem> alist;


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected ArrayList<NewsItem> doInBackground(String... strings) {
            try {

                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                Log.d("demo", "value url=" + url);
                alist = new ArrayList<>();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // alist= PersonParser.PersonSAXParser.parsePersons(connection.getInputStream());
                    alist = NewsParser.NewsPullParser.parseNews(connection.getInputStream());

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return alist;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> people) {
            if(people.size()>0)
            {
                newslist=people;
                Log.d("demo","result new="+newslist.toString());
                imagedyn(newslist);
//                for(int i=0;i<newslist.size();i++)
//                {
//                    imgarr=new ImageView[newslist.size()];
//                    ImageView img2= new ImageView(MainActivity.this);
//                    imgarr[i]=img2;
//                    imgurl=newslist.get(i).getMedia().getUrl();
//                    Log.d("demo","list ur="+imgurl);
//                    Log.d("demo","view created");
//                    new GetAsyncImage(imgarr[i],MainActivity.this).execute(imgurl);
//                }
            }
            else
            {
                Log.d("demo","result=0");
            }
        }

    }

}