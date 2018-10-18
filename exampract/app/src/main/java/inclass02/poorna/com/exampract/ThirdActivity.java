package inclass02.poorna.com.exampract;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ThirdActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageView im3;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            iTunes itn= (iTunes) getIntent().getExtras().getSerializable(RecycleAdapter.MAIN_KEY);
            im3=findViewById(R.id.imgin);
            Picasso.with(ThirdActivity.this).load(itn.getImageurl()).into(im3);
        }
    }
}
