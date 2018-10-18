package inclass02.poorna.com.testproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tname, temail, tproglang, taccount, tseek;
    public static final int REQ_CODE = 100;
    public static final String STUD_KEY = "second";
    public static String FLAG_KEY = "flag";
    public static final String VALUE_KEY = "value";

    String val;

    Student s,stu;
    int flag;
    ImageView im1, im2, im3, im4, im5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Display Activity");
        if (getIntent() != null && getIntent().getExtras() != null) {
            s = (Student) getIntent().getExtras().getSerializable(MainActivity.STU_KEY);
            tname = findViewById(R.id.name_text);
            temail = findViewById(R.id.email_enter);
            tproglang = findViewById(R.id.proglang_text);
            taccount = findViewById(R.id.account_text);
            tseek = findViewById(R.id.text_mood);
            tname.setText(s.getName());
            temail.setText(s.getEmail());
            tproglang.setText(s.getProgrammingLang());
            taccount.setText(s.getSearch());
            tseek.setText(s.getMood() + "" + "% Positive");
            im1 = (ImageView) findViewById(R.id.name_edit);
            im1.setOnClickListener(this);
            im2 = findViewById(R.id.email_edit);
            im2.setOnClickListener(this);
            im3 = findViewById(R.id.proglang_edit);
            im3.setOnClickListener(this);
            im4 = findViewById(R.id.account_edit);
            im4.setOnClickListener(this);
            im5 = findViewById(R.id.mood_edit);
            im5.setOnClickListener(this);


        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == im1.getId()) {
            flag = 1;
            Log.d("demo", "image 1 clicked");
        }
        if (view.getId() == im2.getId()) {
            flag = 2;
        }
        if (view.getId() == im3.getId()) {
            flag = 3;
        }
        if (view.getId() == im4.getId()) {
            flag = 4;
        }
        if (view.getId() == im5.getId()) {
            flag = 5;
        }

        Intent in = new Intent("inclass02.poorna.com.testproj.intent.action.VIEW");
        in.addCategory(Intent.CATEGORY_DEFAULT);
        in.putExtra(STUD_KEY, s);
        in.putExtra(FLAG_KEY, flag);
        //Log.d("demo","student"+s);
        startActivityForResult(in, REQ_CODE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            //int flag = (Integer) data.getExtras().getInt(ThirdActivity.RETURN_KEY);
            stu=(Student)data.getExtras().getSerializable(VALUE_KEY);
            if(resultCode==RESULT_OK){
                tname.setText(stu.getName());
                temail.setText(stu.getEmail());
                tproglang.setText(stu.getProgrammingLang());
                taccount.setText(stu.getSearch());
                tseek.setText(stu.getMood()+"% Positive");

            }


        }

    }
}
