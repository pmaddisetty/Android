package samplecalss.poorna.com.myapplication21;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private TextView tv2;
    private EditText edle;
    private EditText edlen;
    private EditText res;
    double val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        tv=(TextView)findViewById(R.id.selecttext);
         edle= (EditText)findViewById(R.id.lengthinp1);
         edlen= (EditText)findViewById(R.id.lengthinp2);
         tv2= (TextView)findViewById(R.id.length2);
        res=(EditText)findViewById(R.id.result);

        findViewById(R.id.triangle).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                tv.setText("Triangle");
                tv2.setVisibility(tv2.VISIBLE);
                edlen.setVisibility(edlen.VISIBLE);
                edlen.setText("");
                edle.setText("");
                res.setText("");

            }

        });

        findViewById(R.id.sqaure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("square");
                tv2.setVisibility(tv2.INVISIBLE);
                edlen.setVisibility(edlen.INVISIBLE);
                edle.setText("");
                res.setText("");


            }
        });

        findViewById(R.id.circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("circle");
                tv2.setVisibility(tv2.INVISIBLE);
                edle.setText("");
                edlen.setVisibility(edlen.INVISIBLE);
                res.setText("");

            }
        });

        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv.getText() == "Triangle" ||tv.getText()=="square" ||tv.getText()=="circle") {

                    if( (tv.getText()=="Triangle")&&(edle.getText().toString().length() == 0 || edlen.getText().toString().length() == 0)) {
                        Toast.makeText(MainActivity.this, "You did not enter all inputs", Toast.LENGTH_SHORT).show();
                    }
                    else if(((tv.getText()=="square")||(tv.getText()=="circle"))&& (edle.getText().toString().length() == 0))
                    {
                        Toast.makeText(MainActivity.this, "You did not enter all inputs", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (tv.getText() == "Triangle") {
                            val = 0.5 * (Integer.parseInt(edle.getText().toString()) * Integer.parseInt(edlen.getText().toString()));
                            res.setText(val + "");

                        } else {
                            if (tv.getText() == "square") {
                                val = Integer.parseInt(edle.getText().toString()) * Integer.parseInt(edle.getText().toString());
                                res.setText(val + "");
                            }
                            else if (tv.getText() == "circle") {
                                val = 3.146 * Integer.parseInt(edle.getText().toString()) * Integer.parseInt(edle.getText().toString());
                                res.setText(val + "");

                            }
                        }

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "You did not select shape", Toast.LENGTH_SHORT).show();
                }

            }

        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res.setText("");
                edle.setText("");
                edlen.setText("");
                tv.setText("Select Text");
            }
        });


        }

    }

