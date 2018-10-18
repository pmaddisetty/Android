package inclass02.poorna.com.testproj;

import android.content.Intent;
import android.support.v4.app.ServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;

public class ThirdActivity extends AppCompatActivity {
    public static final String RETURN_KEY = "ret";
TextView tn, tem,tprola,taccnt,tmo;
TextView lprog,laccount,lmood;
RadioGroup rg1;
RadioButton rb1,rb2,rb3;
String s1,s2,s3;
Switch sb1;
SeekBar sk;
int flag;
String val;
    Student st;

    @Override
    protected    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_third);
        setTitle("Edit Activity");
        if(getIntent()!=null && getIntent().getExtras()!=null) {

           flag=(Integer)getIntent().getExtras().getInt(SecondActivity.FLAG_KEY);
             st= (Student) getIntent().getExtras().getSerializable(SecondActivity.STUD_KEY);
            Log.d("demo" ,st+"");
            Log.d("demo","intent 3 " +flag);
           // Log.d("demo",st+"");
            //Log.d("demo",flag+"");
            tn=findViewById(R.id.name_enter);
            tem=(TextView) findViewById(R.id.email_enter);
            lprog=(TextView)findViewById(R.id.text_pglang);
            rg1=(RadioGroup) findViewById(R.id.radioGroup);
            laccount=(TextView)findViewById(R.id.text_account);
            sb1=(Switch) findViewById(R.id.toggle_switch);
            lmood=(TextView)findViewById(R.id.text_mood);
            sk=(SeekBar)findViewById(R.id.interest);
            rb1=(RadioButton) findViewById(R.id.java);
            rb2=(RadioButton) findViewById(R.id.cprog);
            rb3=(RadioButton) findViewById(R.id.cshap);

            switch(flag)
            {
                case 1:tn.setText(st.getName());
                    tn.setVisibility(View.VISIBLE);
                    tem.setVisibility(View.INVISIBLE);
                    lprog.setVisibility(View.INVISIBLE);
                    rg1.setVisibility(View.INVISIBLE);
                    laccount.setVisibility(View.INVISIBLE);
                    sb1.setVisibility(View.INVISIBLE);
                    lmood.setVisibility(View.INVISIBLE);
                    sk.setVisibility(View.INVISIBLE);
                    break;
                case 2:tem.setText(st.getEmail());
                    tn.setVisibility(View.INVISIBLE);
                    tem.setVisibility(View.VISIBLE);
                    lprog.setVisibility(View.INVISIBLE);
                    rg1.setVisibility(View.INVISIBLE);
                    laccount.setVisibility(View.INVISIBLE);
                    sb1.setVisibility(View.INVISIBLE);
                    lmood.setVisibility(View.INVISIBLE);
                    sk.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    tn.setVisibility(View.INVISIBLE);
                    tem.setVisibility(View.INVISIBLE);
                    lprog.setVisibility(View.VISIBLE);
                    rg1.setVisibility(View.VISIBLE);
                    String prg=st.getProgrammingLang();
                    Log.d("demo","prg="+prg);
                    s1=getResources().getString(R.string.java);
                    s2=getResources().getString(R.string.cprog);
                    s3=getResources().getString(R.string.cshap);
                    Log.d("demo","s1="+s1);
                    Log.d("demo","s2="+s2);
                    Log.d("demo","s3="+s3);
                    if (s1.equals(prg)){
                        Log.d("demo","if rb1 entered");
                        rb1.setChecked(true);
                    }
                    else if(s2.equals(prg)){
                        Log.d("demo","if rb2 entered");
                        rb2.setChecked(true);}
                    else if(s3.equals(prg)) { Log.d("demo","if rb3 entered");
                        rb3.setChecked(true);}
                    laccount.setVisibility(View.INVISIBLE);
                    sb1.setVisibility(View.INVISIBLE);
                    lmood.setVisibility(View.INVISIBLE);
                    sk.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    tn.setVisibility(View.INVISIBLE);
                    tem.setVisibility(View.INVISIBLE);
                    lprog.setVisibility(View.INVISIBLE);
                    rg1.setVisibility(View.INVISIBLE);
                    laccount.setVisibility(View.VISIBLE);
                    sb1.setVisibility(View.VISIBLE);
                    String sw=st.getSearch();
                    if(sb1.getTextOn().toString().equals(sw)){
                        sb1.setChecked(true);
                    }
                    if(sb1.getTextOff().toString().equals(sw)){
                        sb1.setChecked(false);
                    }
                    lmood.setVisibility(View.INVISIBLE);
                    sk.setVisibility(View.INVISIBLE);
                    break;
                case 5:
                    tn.setVisibility(View.INVISIBLE);
                    tem.setVisibility(View.INVISIBLE);
                    lprog.setVisibility(View.INVISIBLE);
                    rg1.setVisibility(View.INVISIBLE);
                    laccount.setVisibility(View.INVISIBLE);
                    sb1.setVisibility(View.INVISIBLE);
                    lmood.setVisibility(View.VISIBLE);
                    sk.setVisibility(View.VISIBLE);
                    sk.setProgress(st.getMood());
                    break;


                default: tem.setVisibility(View.VISIBLE);
            }
        findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(flag)
                {
                    case 1:
                        val=tn.getText().toString();
                    if(val==null||val.length()==0){
                        Toast.makeText(ThirdActivity.this,"enter value",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        st.setName(val);
                        Intent in = new Intent();
                        in.putExtra(SecondActivity.VALUE_KEY,st);
                        Log.d("demo","fla in casee 1 is"+flag);
                        in.putExtra(RETURN_KEY,flag);
                        setResult(RESULT_OK,in);
                    }
                    break;
                    case 2 :   val=tem.getText().toString();
                        if(val==null||val.length()==0){
                            Toast.makeText(ThirdActivity.this,"enter value",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            st.setEmail(val);
                            Intent in = new Intent();
                            in.putExtra(SecondActivity.VALUE_KEY,st);
                            Log.d("demo","flag in case 2 is"+flag);
                            in.putExtra(RETURN_KEY,flag);
                            setResult(RESULT_OK,in);
                        }
                        break;

                    case 3:
                        int id=rg1.getCheckedRadioButtonId();
                        if(id==R.id.java) {val="Java";}
                        if(id==R.id.cprog) {val="C";}
                        if(id==R.id.cshap) {val="C#";}
                        st.setProgrammingLang(val);
                        Intent in = new Intent();
                        in.putExtra(SecondActivity.VALUE_KEY,st);
                        Log.d("demo","flag in case 3 is"+flag);
                        setResult(RESULT_OK,in);
                        break;

                    case 4:
                        if(sb1.isChecked())
                        {
                            st.setSearch(sb1.getTextOn().toString());
                            sb1.setChecked(true);
                        }
                        else
                        {
                            sb1.setChecked(false);
                            st.setSearch(sb1.getTextOff().toString());
                        }
                        Log.d("demo","flag in case 3 is"+flag);
                        Intent inte=new Intent();
                        inte.putExtra(SecondActivity.VALUE_KEY,st);
                        Log.d("demo","flag in case 3 is"+flag);
                        setResult(RESULT_OK,inte);
                        break;


                    case 5: int prg=sk.getProgress();
                            st.setMood(prg);
                        Intent inten=new Intent();
                        inten.putExtra(SecondActivity.VALUE_KEY,st);
                        Log.d("demo","flag in case 3 is"+flag);
                        setResult(RESULT_OK,inten);
                        break;

                }
                finish();

            }
        });

        }

    }
}
