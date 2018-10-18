package inclass02.poorna.com.homework2;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
TextView ttitle,tdate,ttime,tpriority,ttask;
ImageView imadd,imedit,imrem,imnext,imprev,imfirst,imlast;
Task tas;
String datepr,timepr,timehr,timemin;
static int end=0;
static int start=0;
int index,i1,i2;
public static int REQ_CODE=100;
public static String THIRD_KEY="third";
public static LinkedList<Task> taskmain;
Task ta;
Calendar mycal;
DateFormat sdf1,sdf2,sdf3,sdf4;
Task tanext;
int countm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("View Tasks");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.task);
        imedit=(ImageView) findViewById(R.id.edit);
        taskmain=new LinkedList<Task>();
        imadd=(ImageView) findViewById(R.id.add);
        imnext=(ImageView) findViewById(R.id.next) ;
        imprev=(ImageView) findViewById(R.id.previous) ;
        imfirst=(ImageView) findViewById(R.id.first) ;
        imlast=(ImageView) findViewById(R.id.last) ;
        imrem=(ImageView) findViewById(R.id.remove) ;
        imedit=(ImageView)findViewById(R.id.edit);
        imadd.setOnClickListener(this);
        imnext.setOnClickListener(this);
        imprev.setOnClickListener(this);
        imfirst.setOnClickListener(this);
        imlast.setOnClickListener(this);
        imedit.setOnClickListener(this);
        imrem.setOnClickListener(this);
        ttitle=findViewById(R.id.task_title);
        tdate=findViewById(R.id.task_date);
        ttime=findViewById(R.id.task_time);
        tpriority=findViewById(R.id.task_priority);
        ttask=(TextView) findViewById(R.id.task);
        ttask.setText("Task 0 of 0");
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.add)
        {
            Intent in=new Intent(MainActivity.this,SecondActivity.class);
            startActivityForResult(in,REQ_CODE);
        }
        if(view.getId()==R.id.next)
        {

            if(start==end || taskmain.size()==0)
            {
                Toast.makeText(MainActivity.this,"There are no tasks to display",Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                tanext=taskmain.get(start);
                start=start+1;
                displayout(tanext);
                end=taskmain.size();
                ttask.setText("Task "+start+" Of "+end);
            }
        }
        if(view.getId()==R.id.previous)
        {
            if(start==1 || taskmain.size() == 0)
            {

                Toast.makeText(MainActivity.this,"There are no tasks to display",Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {

                tanext=taskmain.get(start-2);
                start=start-1;
                displayout(tanext);
                end=taskmain.size();
                ttask.setText("Task "+start+" Of "+end);
            }
        }
        if(view.getId()==R.id.first)
        {
            if(start==0||end==0||start==1)
            {
                Toast.makeText(MainActivity.this,"There are no tasks to display",Toast.LENGTH_SHORT).show();
            }
            else
            {
                tanext=taskmain.getFirst();
                displayout(tanext);
                end=taskmain.size();
                start=1;
                ttask.setText("Task "+start+" Of "+end);
            }
        }
        if(view.getId()==R.id.last)
        {
            if(start==0||end==0||start==end)
            {
                Toast.makeText(MainActivity.this,"There are no tasks to display",Toast.LENGTH_SHORT).show();
            }
            else
            {
                tanext=taskmain.getLast();
                displayout(tanext);
                end=taskmain.size();
                start=end;
                ttask.setText("Task "+start+" Of "+end);
            }
        }
        if(view.getId()==R.id.remove)
        {

            if(start==0 && end==0)
            {
                Toast.makeText(this, "No tasks to delete", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                taskmain.remove(start - 1);
                start=start-1;
                end = end - 1;
                if (taskmain.size() >= 1) {
                    start = 0;
                    tanext = taskmain.get(start);
                    Collections.sort(taskmain, new Sortlinklist());
                    displayout(tanext);
                    start = start + 1;
                    ttask.setText("Task " + start + " Of " + end);
                } else {
                    ttask.setText("Task 0 of 0");
                    ttitle.setText("Task Title");
                    tdate.setText("Task Date");
                    ttime.setText("Task Time");
                    tpriority.setText("Task Priority");
                }
            }
        }

        if(view.getId()==R.id.edit){

            Intent in=new Intent(MainActivity.this,ThirdActivity.class);


            if(taskmain.size()==0)
            {
                Toast.makeText(this, "No tasks to Edit", Toast.LENGTH_SHORT).show();
            }
            else
            {
                tanext=taskmain.get(start-1);
                index=start-1;
                in.putExtra(THIRD_KEY,tanext);
                startActivityForResult(in,REQ_CODE);
            }

        }



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {

            if(resultCode==RESULT_OK){


                if(data.getExtras().getSerializable(THIRD_KEY)!=null) {
                    Task tas2 = (Task) data.getExtras().getSerializable(THIRD_KEY);
                    Log.d("demo","updated task= "+tas2);
                    taskmain.set(index,tas2);
                    Log.d("demo","before staart"+start+""+index);
                    Collections.sort(taskmain,new Sortlinklist());
                    displayout(tas2);
                    start=taskmain.indexOf(tas2)+1;
                    end=taskmain.size();
                    ttask.setText("Task "+start+" Of "+end);

                    Log.d("demo","before staart"+start+""+index);
                }
                if(data.getExtras().getSerializable(SecondActivity.TA_KEY) !=null){
                    tas= (Task)data.getExtras().getSerializable(SecondActivity.TA_KEY);
                    taskmain.add(tas);
                    Log.d("demo","befor sort"+taskmain);
                    Collections.sort(taskmain,new Sortlinklist());
                    Log.d("demo","after sort"+taskmain);
                    Log.d("demo","list size is "+ taskmain.size());
                    displayout(tas);
                    end=taskmain.size();
                    start=taskmain.indexOf(tas)+1;
                    ttask.setText("Task "+start+" Of "+end);

                }


            }
        }
    }

    class Sortlinklist implements Comparator<Task>{

        @Override
        public int compare(Task t1, Task t2) {
            if (t1.getDateandtime() == null || t2.getDateandtime() == null)
                return 0;
            return t1.getDateandtime().compareTo(t2.getDateandtime());
        }
    }
    private void displayout(Task ta)
    {
        ttitle.setText(ta.getTitle());
        sdf1=new SimpleDateFormat("MM/dd/yyyy");
        tdate.setText(sdf1.format(ta.getDate()));

        sdf2=new SimpleDateFormat("HH:mm");
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
            ttime.setText(i1 + ":" + i2 + " PM");
        }
        else
        {
            ttime.setText(i1 + ":" + i2 + " AM");
        }

       // ttime.setText(sdf2.format(ta.getTime()));
        tpriority.setText(ta.getPriority());

    }
}
