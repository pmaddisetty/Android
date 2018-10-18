package inclass02.poorna.com.listdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SecondActivity extends AppCompatActivity {
TextView ttitle,tdate,tdesc;
ImageView imv;
NewsItem news;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        ttitle=findViewById(R.id.stitle);
        tdate=findViewById(R.id.sdate);
        tdesc=findViewById(R.id.sdescription);
        imv=findViewById(R.id.simage);
        if(getIntent()!=null ||getIntent().getExtras()!=null)
        {
            news= (NewsItem) getIntent().getExtras().getSerializable(MainActivity.NEWS_KEY);
            ttitle.setText(news.getTitle().toString());
            tdate.setText(news.getDate().toString());
            tdesc.setText(news.getDescription().toString());
            Picasso.with(SecondActivity.this).load(news.getImgurl()).into(imv);
        }
    }
}
