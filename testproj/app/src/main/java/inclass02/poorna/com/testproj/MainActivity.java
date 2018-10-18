package inclass02.poorna.com.testproj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    public static final String STU_KEY="student";

    EditText edname;
EditText edemail;
ToggleButton tg;
Switch sw;
SeekBar sb;
    Student stu;
RadioGroup rg;
String nameact,emailact,progact,togact;
int seekact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Main Activity");
        edname=(EditText) findViewById(R.id.name_enter);
        edemail=(EditText) findViewById(R.id.email_enter);
            rg=(RadioGroup) findViewById(R.id.radioGroup);
            sw=(Switch)findViewById(R.id.toggle_switch);
            sb=(SeekBar)findViewById(R.id.interest);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekact=i;
                Log.d("demoseek",""+i);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edname.getText().toString().length()==0||edemail.getText().toString().length()==0)
                {
                    Toast.makeText(MainActivity.this,"Please enter the values",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email = edemail.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (email.matches(emailPattern)) {
                        nameact=edname.getText().toString();
                        emailact=edemail.getText().toString();

                        int id=rg.getCheckedRadioButtonId();
                        if(id==R.id.java) {progact="Java";}
                        if(id==R.id.cprog) {progact="C";}
                        if(id==R.id.cshap) {progact="C#";}
                        if(sw.isChecked())
                        {togact=sw.getTextOn().toString();}
                        else
                        { togact=sw.getTextOff().toString(); }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                    }


                }
                Log.d("demo","value out is "+ nameact+emailact+progact+togact+seekact);
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                 stu=new Student(nameact,emailact,progact,togact,seekact);
                Log.d("student",stu +"");
                intent.putExtra(STU_KEY,stu);
                startActivity(intent);
            }

        });



    }
}
