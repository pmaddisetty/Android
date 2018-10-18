package inclass02.poorna.com.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
int check;
EditText edname,edemail;
RadioGroup rg1;
TextView tdept,tmood;
SeekBar sb1;
Student st;
RadioButton rb1,rb2,rb3,rb4;
String dep1,dep2,dep3,dep4;
String val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_third);
        setTitle("Edit Activity");
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            check=(Integer)getIntent().getExtras().getInt(SecondActivity.CHECK_KEY);
            st=(Student)getIntent().getExtras().getSerializable(SecondActivity.SEC_KEY);
            edname=(EditText) findViewById(R.id.name_edit);
            edemail=(EditText)findViewById(R.id.email_edit);
            rg1=(RadioGroup) findViewById(R.id.radioGroup);
            tdept=(TextView) findViewById(R.id.department);
            tmood=(TextView) findViewById(R.id.current_mood);
            sb1=(SeekBar)findViewById(R.id.mood_edit);
            rb1=findViewById(R.id.sis_edit);
            rb2=findViewById(R.id.cs_edit);
            rb3=findViewById(R.id.bio_edit);
            rb4=findViewById(R.id.others_edit);

            if(check==1)
            {
                edname.setText(st.getName());
                edemail.setVisibility(View.INVISIBLE);
                rg1.setVisibility(View.INVISIBLE);
                tdept.setVisibility(View.INVISIBLE);
                tmood.setVisibility(View.INVISIBLE);
                sb1.setVisibility(View.INVISIBLE);

            }
            if(check==2)
            {
                edname.setVisibility(View.INVISIBLE);
                edemail.setVisibility(View.VISIBLE);
                edemail.setText(st.getEmail());
                rg1.setVisibility(View.INVISIBLE);
                tdept.setVisibility(View.INVISIBLE);
                tmood.setVisibility(View.INVISIBLE);
                sb1.setVisibility(View.INVISIBLE);
            }
            if(check==3)
            {
                edname.setVisibility(View.INVISIBLE);
                edemail.setVisibility(View.INVISIBLE);
                rg1.setVisibility(View.VISIBLE);
                tdept.setVisibility(View.VISIBLE);
                String dept=st.getDepartment();
                Log.d("demo",dept);
                 dep1=getResources().getString(R.string.sis_edit);
                 dep2=getResources().getString(R.string.cs_edit);
                 dep3=getResources().getString(R.string.bio_edit);
                 dep4=getResources().getString(R.string.others_edit);
                 Log.d("demo","depat2"+dep4);
                if(dept.equals(dep1))
                {
                    rb1.setChecked(true);
                }
                if(dept.equals(dep2)){ rb2.setChecked(true);}
                if(dept.equals(dep3)){ rb3.setChecked(true);}
                if(dept.equals(dep4)){
                    Log.d("demo","depat3"+dept);
                    rb4.setChecked(true);
                }
                else{
                    Log.d("demo","not equal"+dept+dep4);
                }
                tmood.setVisibility(View.INVISIBLE);
                sb1.setVisibility(View.INVISIBLE);
            }
            if(check==4)
            {
                edname.setVisibility(View.INVISIBLE);
                edemail.setVisibility(View.INVISIBLE);
                rg1.setVisibility(View.INVISIBLE);
                tdept.setVisibility(View.INVISIBLE);
                tmood.setVisibility(View.VISIBLE);
                sb1.setVisibility(View.VISIBLE);
                sb1.setProgress(st.getMood());

            }
            findViewById(R.id.submit_main).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent();
                switch(check) {

                    case 1:
                        val = edname.getText().toString();
                        Log.d("demo","string"+val);
                        if (val == null || val.trim().length() == 0) {
                            Toast.makeText(ThirdActivity.this, "enter value", Toast.LENGTH_SHORT).show();

                        } else {

                            st.setName(val);
                           // Intent in = new Intent();
                            in.putExtra(SecondActivity.VAL_KEY, st);
                            Log.d("demo","secon"+st);
                            in.putExtra(SecondActivity.FLG_KEY,check);
                            setResult(RESULT_OK, in);
                        }
                       // finish();
                        break;
                    case 2 :

                       String val1=edemail.getText().toString();
                        if (val1 == null||val1.trim().length()==0)
                        {
                            Toast.makeText(ThirdActivity.this,"enter value",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String email = edemail.getText().toString().trim();
                            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                            if (email.matches(emailPattern)) {
                                st.setEmail(val1);
                                //Intent in = new Intent();
                                in.putExtra(SecondActivity.VAL1_KEY, st);
                                in.putExtra(SecondActivity.FLG_KEY,check);
                                setResult(RESULT_OK, in);

                            }
                            else
                            {
                                Toast.makeText(ThirdActivity.this,"Invalid email",Toast.LENGTH_SHORT).show();
                            }
                        }
                       // finish();
                        break;
                    case 3 :     int id=rg1.getCheckedRadioButtonId();
                        if(id==R.id.sis_edit){val="SIS";}
                        if(id==R.id.cs_edit){val="CS";}
                        if(id==R.id.bio_edit){val="BIO";}
                        if(id==R.id.others_edit){val="Others";}
                        st.setDepartment(val);
                        //Intent inten=new Intent();
                        in.putExtra(SecondActivity.VAL2_KEY,st);
                        in.putExtra(SecondActivity.FLG_KEY,check);
                        setResult(RESULT_OK,in);
                       // finish();
                      break;

                    case 4:
                        int prg=sb1.getProgress();
                        st.setMood(prg);
                       // Intent intent=new Intent();
                        in.putExtra(SecondActivity.VAL3_KEY,st);
                        in.putExtra(SecondActivity.FLG_KEY,check);
                        setResult(RESULT_OK,in);
                        //finish();
                        break;

                }
                finish();


                }
            });


        }

    }
}
