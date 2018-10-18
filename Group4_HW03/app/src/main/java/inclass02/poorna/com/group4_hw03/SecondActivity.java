package inclass02.poorna.com.group4_hw03;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

public class SecondActivity extends AppCompatActivity implements GetAsyncImage.iImage{
int queindex;
String question;
String url;
TextView tques,tqindex;
Button btnnext,btnquit;
ArrayList<String> optionlist;
RadioGroup rg;
RadioButton rb[];
ProgressDialog pg;
int radioanswer=-1;
static int score=0;
int intesco;
int answer,answertochek;
static String SECOND_KEY="second";
static String THIRD_KEY="third";
ImageView im;
    ArrayList<Question> qtypelist;
    RadioGroup rg2;
    RelativeLayout layout;
    int percent;
    static int prindex,secindex;
    CountDownTimer tm;
    TextView ta2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Trivia Activity");
        optionlist= new ArrayList<String>();

        im=findViewById(R.id.questnimg);
         qtypelist=new ArrayList<Question>();
        tques=findViewById(R.id.questn);
        tqindex=findViewById(R.id.qno);
        rg=findViewById(R.id.rgroup);
        rg.setOrientation(RadioGroup.VERTICAL);
        ta2=findViewById(R.id.timetxt);

        qtypelist=(ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.MAIN_KEY);
        Log.d("demo","qtypelist="+qtypelist.toString());
     //   answer=qtypelist.get(prindex).getAnswer();
        if(getIntent().getExtras()!=null && getIntent()!=null) {
            startTimer(120000,1000);
            prindex=secindex=0;
            score=0;
            setLayout(qtypelist);

                findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isConnected()) {
                            //  layout.removeView(rg2);

                            //rg.clearCheck();
                            //rg.clearFocus();
                            prindex++;
                            if (prindex < qtypelist.size()) {
                                rg.removeAllViews();
                                intesco = setLayout(qtypelist);
                                answertochek = qtypelist.get(secindex).getAnswer();
                                Log.d("demo", "primary index=" + prindex);
                                Log.d("demo", "secondary index=" + secindex);
                                Log.d("demo", "corect answer=" + answertochek);
                                Log.d("demo", "selected answer=" + intesco);

                                if (answertochek == intesco) {
                                    score = score + 1;
                                    Log.d("demo", "score value is=" + score);
                                    Log.d("demo", "percent=" + percent);
                                }
                                percent = ((score * 100) / qtypelist.size());
                                Log.d("demo", "percent val=" + percent);
                                secindex++;


                            } else {

                                Log.d("demo", "third act score=" + score);
                                tm.cancel();
                                Intent in = new Intent(SecondActivity.this, ThirdActivity.class);
                                in.putExtra(SECOND_KEY, percent);
                                in.putExtra(MainActivity.MAIN_KEY, qtypelist);
                                startActivity(in);
                            }

                            radioanswer = -1;
                        }
                        else
                        {
                            Toast.makeText(SecondActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            findViewById(R.id.secondquit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    tm.cancel();
                    Intent in=new Intent(SecondActivity.this,MainActivity.class);
                    startActivity(in);
                    finish();
                }
            });


        }

    }

    @Override
    public void handleImage(Bitmap bitmap) {
    pg.dismiss();
    im.setImageBitmap(bitmap);
    }

    @Override
    public void initprogimg() {
        pg=new ProgressDialog(SecondActivity.this);
        pg.setTitle("Loading Image");
        pg.setCancelable(false);
        pg.show();
    }

    @Override
    public void displaprogimg() {

    }
    public int setLayout(ArrayList<Question> newqtlist)
    {
        if(prindex<newqtlist.size()) {

            queindex = newqtlist.get(prindex).getIndex();
            question = newqtlist.get(prindex).getQuestion();
            url = newqtlist.get(prindex).getUrl();
            optionlist = newqtlist.get(prindex).getOptlist();
            Log.d("demo", "option list=" + optionlist.toString());
            answer = newqtlist.get(prindex).getAnswer();
            Log.d("demo", " answer value is" + answer);

            int qnum = queindex + 1;
            tqindex.setText("Q" + qnum);
            tques.setText(question);
            if (url == null) {
                im.setImageResource(R.mipmap.noimg);
            } else {
                if(isConnected()) {
                    new GetAsyncImage(im, SecondActivity.this).execute(url);
                }
                else
                {
                    Toast.makeText(SecondActivity.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                }
            }
            rb = new RadioButton[optionlist.size()];
            for (int i = 0; i < optionlist.size(); i++) {
                rb[i] = new RadioButton(this);
                rg.addView(rb[i]);
                rb[i].setText(optionlist.get(i));

            }
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    for (int j = 0; j < optionlist.size(); j++) {
                        if (rb[j].getId() == i) {
                            radioanswer = j;
                            break;
                        }
                    }


                }
            });

            Log.d("demo", "radio answer=" + radioanswer);
        }
                  return radioanswer;

    }
    public void startTimer(final long finish, long tick) {
        tm = new CountDownTimer(finish, tick) {

            public void onTick(long millisUntilFinished) {
                long leftsecs = millisUntilFinished / 1000;
                ta2.setText("Time Left:" + leftsecs + " seconds");
            }

            @Override
            public void onFinish() {
            ta2.setText("Time Left:" + 0 + " seconds");
            tm.cancel();
                Intent in = new Intent(SecondActivity.this, ThirdActivity.class);
                in.putExtra(SECOND_KEY, percent);
                in.putExtra(MainActivity.MAIN_KEY,qtypelist);
                startActivity(in);

            }


        }.start();
    }
    private boolean isConnected(){
        ConnectivityManager cn= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net=cn.getActiveNetworkInfo();
        if(net==null || !net.isConnected()||
                (net.getType()!=cn.TYPE_WIFI && net.getType()!=cn.TYPE_MOBILE))
        {
            return false;
        }
        return true;
    }

    }
