package inclass02.poorna.com.homework2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static inclass02.poorna.com.homework2.SecondActivity.TA_KEY;

public class ThirdActivity extends AppCompatActivity {

   Task ta;
   Button savebtn;
    public static int REQ_CODE = 100;
    EditText edtitle,eddate,edtime,edpriority;
    DateFormat sdf1,sdf2,sdf3,sdf4,dateformat,timeformat,datetimeformat,sdf5,sdf6,sdf7;
    String timehr,datestr,prstr,pr1,pr2,pr3,timemin,timestr,datefor,timefor,dateandtimefor,month,date1,year;
    String ttitle,tpriority;
    RadioButton rb1,rb2,rb3;
    RadioGroup rg;
    int i1,i2,i3,i4,i5;
    Calendar cal;
    Task ta2;
    int rbid;
    Date tdate,ttime,tdateandtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Task");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.task);
        setContentView(R.layout.layout_third);
        savebtn = (Button) findViewById(R.id.save_sec);
        if(getIntent()!=null&&getIntent().getExtras()!=null)
        {
            ta=(Task)getIntent().getExtras().getSerializable(MainActivity.THIRD_KEY);

            Log.d("demo","third activity"+ta);
            cal = Calendar.getInstance();
            edtitle=(EditText)findViewById(R.id.title_sec);
            eddate=(EditText)findViewById(R.id.date_sec);
            edtime=(EditText)findViewById(R.id.time_sec);
            rg=(RadioGroup)findViewById(R.id.radioGroup);
            rb1=(RadioButton)findViewById(R.id.high_sec);
            rb2=(RadioButton)findViewById(R.id.medium_sec);
            rb3=(RadioButton)findViewById(R.id.low_sec);
            eddate.setKeyListener(null);
            edtime.setKeyListener(null);
            edtitle.setText(ta.getTitle());
            sdf1= new SimpleDateFormat("MM/dd/yyyy") ;
            datestr=sdf1.format(ta.getDate());
            eddate.setText(datestr);
            sdf2= new SimpleDateFormat("HH:mm");
            timestr=sdf2.format(ta.getTime());
            sdf3= new SimpleDateFormat("HH");
            timehr= sdf3.format(ta.getTime());
             i1=Integer.parseInt(timehr);
             Log.d("demo","hr val is"+timehr+" i1 "+i1);
            sdf4= new SimpleDateFormat("mm");
            timemin= sdf4.format(ta.getTime());
            i2=Integer.parseInt(timemin);
            Log.d("demo", "min time is"+timemin+" i2 "+i2);
            if(i1>12)
            {
                i1=i1-12;
                edtime.setText(i1 + ":" + i2 + " PM");
            }
             else
              {
                  edtime.setText(i1 + ":" + i2 + " AM");
              }
           // edtime.setText(timestr);

//            sdf5= new SimpleDateFormat("MM");
//            month=sdf5.format(ta.getDate());
//            i3=Integer.parseInt(month);
//            sdf6= new SimpleDateFormat("DD");
//            date1=sdf6.format(ta.getDate());
//            i4=Integer.parseInt(date1);
//            sdf7= new SimpleDateFormat("YYYY");
//            year= sdf7.format(ta.getDate());
//            i5=Integer.parseInt(year);




            prstr=ta.getPriority();
            pr1=getResources().getString(R.string.high_sec);
            pr2=getResources().getString(R.string.medium_sec);
            pr3=getResources().getString(R.string.low_sec);
            if(prstr.equals(pr1)) {rb1.setChecked(true);}
            if(prstr.equals(pr2)) {rb2.setChecked(true);}
            if(prstr.equals(pr3)) {rb3.setChecked(true);}

            ttitle=edtitle.getText().toString();
            tdate=ta.getDate();
            ttime=ta.getTime();
            tpriority=ta.getPriority();

            eddate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerDialog(ThirdActivity.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();

                }
            });
            edtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TimePickerDialog tm= new TimePickerDialog(ThirdActivity.this,time,cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
                    tm.setTitle("Select time");
                    tm.show();
                }
            });
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(edtitle.getText().toString().trim().length()==0||eddate.getText().toString().trim().length()==0||edtime.getText().toString().trim().length()==0||rg.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(ThirdActivity.this,"Please enter the values",Toast.LENGTH_SHORT).show();

                    }
                    int id = rg.getCheckedRadioButtonId();
                    if (id == R.id.low_sec) {
                        tpriority = rb1.getText().toString();
                    }
                    if (id == R.id.medium_sec) {
                        tpriority = rb2.getText().toString();
                    }
                    if (id == R.id.high_sec) {
                        tpriority = rb3.getText().toString();
                    }
                    ttitle=edtitle.getText().toString();
                    ta2=new Task(ttitle,tdate,ttime,tdateandtime,tpriority);
                    Log.d("demo","time is"+ta2);
                    Intent in = new Intent(ThirdActivity.this, MainActivity.class);
                    in.putExtra(MainActivity.THIRD_KEY, ta2);
                    setResult(RESULT_OK,in);
                    finish();
                }
            });
        }


    }
    DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
        @Override
       // DatePicker dp;
        //dp.init(i5,a i4, i3, null);
        public void onDateSet(DatePicker datePicker, int year, int month, int dayofmonth) {
            //datePicker.init(i5, i4, i3, null);
            //datePicker.updateDate(i5,i4,i3);
            datestr=(String)((month+1)+"/"+dayofmonth+"/"+year);
            dateformat = new SimpleDateFormat("MM/dd/yyyy");

            datetimeformat=new SimpleDateFormat("MM/dd/yyy HH:mm");
            dateandtimefor=datestr+" "+timestr;
            eddate.setText(datestr);
            try {
                tdate = dateformat.parse(datestr);
                tdateandtime=datetimeformat.parse(dateandtimefor);
            }
            catch (ParseException pe)
            {
                pe.printStackTrace();
            }

        }
    };
    TimePickerDialog.OnTimeSetListener time= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            timestr=(String)(i+":"+i1);
            timePicker.setHour(i1);
            timePicker.setMinute(i2);
            timeformat =new SimpleDateFormat("HH:mm");
            if(i>12) {
                i = i - 12;
                edtime.setText(i + ":" + i1 + " pm");
            }
            else
            {
                edtime.setText(i + ":" + i1 + " am");
            }
            datetimeformat=new SimpleDateFormat("MM/dd/yyy HH:mm");
            dateandtimefor=datestr+" "+timestr;
            try {
                ttime=timeformat.parse(timestr);
                tdateandtime=datetimeformat.parse(dateandtimefor);
            }
            catch (ParseException pe)
            {
                pe.printStackTrace();
            }
        }
    };

}
