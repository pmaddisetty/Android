package inclass02.poorna.com.a801053466_midterm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {
TextView tart,tapp,trel,tgen,tcopy;
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("App Details");
        if(getIntent()!=null&& getIntent().getExtras()!=null)
        {
            Apps ap=(Apps)getIntent().getExtras().getSerializable(MainActivity.MAIN_KEY);
            tart=findViewById(R.id.artist);
            tapp=findViewById(R.id.appname);
            tcopy=findViewById(R.id.copyright);
            trel=findViewById(R.id.rdate);
            tgen=findViewById(R.id.appgenres);
            img=findViewById(R.id.rimg);

            tart.setText(ap.getArtist());
            tapp.setText(ap.getAppname());
            trel.setText(ap.getReleasedate());
            tcopy.setText(ap.getCopyright());
            tgen.setText(ap.getGenlist().toString());
            Picasso.with(SecondActivity.this).load(ap.getImgurl()).into(img);
        }
    }
}
