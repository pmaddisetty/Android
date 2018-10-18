package inclass02.poorna.com.homework2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener  {
EditText edtitle,eddate,edtime;
RadioGroup rg;
Calendar cal;
Button btn;
Task ta;
RadioButton rbl,rbm,rbh;
int count=0;
Date datecls,timecls,datetimecls;
DateFormat dateformat,timeformat,datetimeformat;
String title,datel,timel,priority,finalstr;
String timefor,datefor;
public static final String TA_KEY="task";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Create Task");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.task);
        rbl=findViewById(R.id.low_sec);
        rbm=findViewById(R.id.medium_sec);
        rbh=findViewById(R.id.high_sec);
        edtitle = (EditText) findViewById(R.id.title_sec);
        eddate = (EditText) findViewById(R.id.date_sec);
        eddate.setKeyListener(null);
        eddate.setOnClickListener(this);
        edtime = (EditText) findViewById(R.id.time_sec);
        edtime.setKeyListener(null);
        edtime.setOnClickListener(this);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        cal = Calendar.getInstance();
        btn=(Button)findViewById(R.id.save_sec);
        btn.setOnClickListener(this);



    }
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
                datefor=(String)((month+1)+"/"+dayofmonth+"/"+year);
//                cal.set(Calendar.YEAR,year);
//                cal.set(Calendar.MONTH,month);
//                cal.set(Calendar.DAY_OF_MONTH,dayofmonth);
                 dateformat = new SimpleDateFormat("MM/dd/yyyy");
                eddate.setText(datefor);

            }
        };
        TimePickerDialog.OnTimeSetListener time= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timefor=(String)(i+":"+i1);
                 timeformat =new SimpleDateFormat("HH:mm");
                if(i>12) {
                    i = i - 12;
                    edtime.setText(i + ":" + i1 + " PM");

                }
                else
                {
                    edtime.setText(i + ":" + i1 + " AM");

                }

            }
        };

    @Override
    public void onClick(View view) {
        if(view.getId()==eddate.getId())
        {
            new DatePickerDialog(SecondActivity.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
        }
        if(view.getId()==edtime.getId())
        {
           TimePickerDialog tm= new TimePickerDialog(SecondActivity.this,time,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
                tm.setTitle("Select time");

                tm.show();
        }
        if(view.getId()==btn.getId())
        {
            if(edtitle.getText().toString().trim().length()==0||eddate.getText().toString().trim().length()==0||edtime.getText().toString().trim().length()==0||rg.getCheckedRadioButtonId()==-1)
            {
                Toast.makeText(SecondActivity.this,"Please enter the values",Toast.LENGTH_SHORT).show();

            }
            else {

                title = edtitle.getText().toString();
                datel = eddate.getText().toString();
                timel = edtime.getText().toString();
                finalstr = datefor + " " + timefor;
                datetimeformat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
                try {
                    datetimecls = datetimeformat.parse(finalstr);
                    datecls = dateformat.parse(datefor);
                    timecls = timeformat.parse(timefor);

                }
                catch (ParseException pe)
                {
                    pe.printStackTrace();
                }


                int id = rg.getCheckedRadioButtonId();
                if (id == R.id.low_sec) {
                    priority = rbl.getText().toString();
                }
                if (id == R.id.medium_sec) {
                    priority = rbm.getText().toString();
                }
                if (id == R.id.high_sec) {
                    priority = rbh.getText().toString();
                }
                ta = new Task(title, datecls, timecls, datetimecls, priority);
                Log.d("demo", "" + ta);
                Intent in = new Intent(SecondActivity.this, MainActivity.class);
                in.putExtra(TA_KEY, ta);
                setResult(RESULT_OK, in);
                finish();
            }

        }

    }


}
