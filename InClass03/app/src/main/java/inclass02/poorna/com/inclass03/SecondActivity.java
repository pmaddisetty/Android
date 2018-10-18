package inclass02.poorna.com.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
Student su,stu,stu1,stu2,stu3;
TextView sname,semail,sdept,smood;
ImageView imname,imemail,imdept,immood;
public static String SEC_KEY="second";
public static String CHECK_KEY="check";
public static String VAL_KEY="value";
    public static String VAL1_KEY="value1";
    public static String VAL2_KEY="value2";
    public static String VAL3_KEY="value3";
    public static String FLG_KEY="fl";

public static final int REQ_CODE = 100;

int checkval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Display Activity");
        if(getIntent()!=null&& getIntent().getExtras()!=null)
        {
            su=(Student)getIntent().getExtras().getSerializable(MainActivity.STU_KEY);
            sname=(TextView) findViewById(R.id.name_val);
            semail=(TextView)findViewById(R.id.email_val);
            sdept=(TextView)findViewById(R.id.dept_val);
            smood=(TextView)findViewById(R.id.mood_val);
            sname.setText(su.getName());
            semail.setText(su.getEmail());
            sdept.setText(su.getDepartment());
            smood.setText(su.getMood()+"% Positive");
            imname=(ImageView) findViewById(R.id.name_img);
            imemail=(ImageView) findViewById(R.id.email_img);
            immood=(ImageView) findViewById(R.id.mood_img);
            imdept=(ImageView) findViewById(R.id.dept_img);
            imname.setOnClickListener(this);
            imemail.setOnClickListener(this);
            imdept.setOnClickListener(this);
            immood.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==imname.getId())
        {
            checkval=1;
        }
        if(view.getId()==imemail.getId())
        {
            checkval=2;
        }
        if(view.getId()==imdept.getId())
        {
            checkval=3;
        }
        if(view.getId()==immood.getId())
        {
            checkval=4;
        }
        Intent inten=new Intent("inclass02.poorna.com.inclass03.intent.action.VIEW");
        inten.addCategory(Intent.CATEGORY_DEFAULT);
        inten.putExtra(SEC_KEY,su);
        inten.putExtra(CHECK_KEY,checkval);
        startActivityForResult(inten,REQ_CODE);



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if(resultCode==RESULT_OK){

            int flg=(Integer)data.getExtras().getInt(FLG_KEY);
                stu=(Student)data.getExtras().getSerializable(VAL_KEY);
                stu1=(Student)data.getExtras().getSerializable(VAL1_KEY);
                stu2=(Student)data.getExtras().getSerializable(VAL2_KEY);
                stu3=(Student)data.getExtras().getSerializable(VAL3_KEY);
                Log.d("demo","result");
                sname.setText(stu.getName());
                semail.setText(stu1.getEmail());
                sdept.setText(stu2.getDepartment());
                smood.setText(stu3.getMood()+"% Positive");

            }

        }

    }
}
