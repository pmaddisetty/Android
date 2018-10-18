package inclass02.poorna.com.group4_inclass07;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class ThirdActivity extends AppCompatActivity {
    TextView ttitle,tdate,tdesc;
    ImageView imv;
    NewsItem news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Detail Activity");
        ttitle=findViewById(R.id.stitle);
        tdate=findViewById(R.id.sdate);
        tdesc=findViewById(R.id.sdescription);
        imv=findViewById(R.id.simage);
        if(getIntent()!=null ||getIntent().getExtras()!=null)
        {
            news= (NewsItem) getIntent().getExtras().getSerializable(SecondActivity.SECOND_KEY);
            ttitle.setText(news.getTitle().toString());
            tdate.setText(news.getPubdate().toString());
            tdesc.setText(news.getDescription().toString());
            Picasso.with(ThirdActivity.this).load(news.getImageurl()).into(imv);
        }

    }

}
