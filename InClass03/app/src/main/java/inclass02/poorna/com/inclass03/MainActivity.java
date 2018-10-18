package inclass02.poorna.com.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText edname,edemail;
RadioGroup rg;
RadioButton rbsis,rbcs,rbbio,rbothers;
SeekBar sb;
String namest,emailst,deptst;
    Student stu;
public static String STU_KEY="stu";
int moodst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Main Activity");
        edname=(EditText)findViewById(R.id.name_edit);
        edemail=(EditText)findViewById(R.id.email_edit);
        rg=(RadioGroup) findViewById(R.id.radioGroup);
        sb=(SeekBar)findViewById(R.id.mood_edit);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                moodst=i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.submit_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edname.getText().toString().trim().length()==0||edemail.getText().toString().trim().length()==0)
                {
                    Toast.makeText(MainActivity.this,"Please enter the values",Toast.LENGTH_SHORT).show();
                }
                else {
                    String email = edemail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (email.matches(emailPattern)) {
                            emailst = edemail.getText().toString();
                            namest = edname.getText().toString();
                            int id = rg.getCheckedRadioButtonId();
                            if (id == R.id.sis_edit) {
                                deptst = "SIS";
                            }
                            if (id == R.id.cs_edit) {
                                deptst = "CS";
                            }
                            if (id == R.id.bio_edit) {
                                deptst = "BIO";
                            }
                            if (id == R.id.others_edit) {
                                deptst = "Others";
                            }

                            Intent in = new Intent(MainActivity.this, SecondActivity.class);
                             stu = new Student(namest, emailst, deptst, moodst);
                            in.putExtra(STU_KEY, stu);
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });


    }
}
