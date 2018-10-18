package com.example.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TipcalculatorActivity extends AppCompatActivity {
    private static SeekBar seek_bar;
    private static TextView custom_seek_value, tipValue, totalValue;
    EditText bill_value;
    String billValue;
    private static double progress, final_tip, tip_percent, tip_value ;
    private  double ini_bill, final_bill;
    //double bv;

    RadioGroup radioGroup2;
    RadioButton rbutton1, rbutton2, rbutton3;
    int rb;
    Button exit;
    TextWatcher textWatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipcalculator);
        setTitle("Tip Calculator");
        bill_value = (EditText) findViewById(R.id.billValue);
        tipValue = (TextView) findViewById(R.id.tipValue);
        totalValue = (TextView) findViewById(R.id.totalValue);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);
        rbutton1 = (RadioButton)findViewById(R.id.rButton1);
        rbutton2 = (RadioButton)findViewById(R.id.rButton2);
        rbutton3 = (RadioButton)findViewById(R.id.rButton3);
        exit = (Button) findViewById(R.id.exitButton);
        seek_bar = (SeekBar) findViewById(R.id.seekBar3);
        custom_seek_value = (TextView) findViewById(R.id.cs);
        custom_seek_value.setText(String.valueOf(seek_bar.getProgress()));
        tip_percent=10;

        bill_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (bill_value.getText().toString().equals("")) {
                    bill_value.setError("Enter bill amount");
                    tipValue.setText("0.00");
                    totalValue.setText("0.00");
                }
                else
                {
                    ini_bill=Double.parseDouble(bill_value.getText().toString());
                    final_tip =  Math.round(ini_bill * tip_percent / 100);
                    final_bill = Math.round(ini_bill + final_tip);
//                    final_bill=Math.round(final_bill);
//                    final_tip = Math.round(final_tip);
                    tipValue.setText(String.valueOf(final_tip));
                    totalValue.setText(String.valueOf(final_bill));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        //RADIO GROUP
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rb=i;
                Log.d("demo", "rb1:" + radioGroup.getCheckedRadioButtonId() );
                if(i==R.id.rButton1){
                    tip_percent = 10;
                }
                else if(i == R.id.rButton2){
                    tip_percent = 15;
                }
                else if(i == R.id.rButton3){
                    tip_percent = 18;
                }
                else if(i==R.id.rButton4) {
                    tip_percent = seek_bar.getProgress();
                }

                if(bill_value.getText().length()==0)
                {
                    Toast.makeText(TipcalculatorActivity.this, "Please enter the bill amount", Toast.LENGTH_SHORT).show();

                }
                else {
                    final_tip =  Math.round(ini_bill * tip_percent / 100);
                    final_bill = Math.round(ini_bill + final_tip);
//                    final_bill=Math.round(final_bill);
//                    final_tip = Math.round(final_tip);
                    tipValue.setText(String.valueOf(final_tip));
                    totalValue.setText(String.valueOf(final_bill));
                }
            }
        });


        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        tip_percent = i;
                        custom_seek_value.setText(String.valueOf(i)+"%");

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        if(rb== R.id.rButton4){
                            if(bill_value.getText().length()==0)
                            {
                                Toast.makeText(TipcalculatorActivity.this, "Please enter the bill amount", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                final_tip =  Math.round(ini_bill * tip_percent / 100);
                                final_bill = Math.round(ini_bill + final_tip);
//                                final_bill=Math.round(final_bill);
//                                final_tip = Math.round(final_tip);
                                tipValue.setText(String.valueOf(final_tip));
                                totalValue.setText(String.valueOf(final_bill));
                            }
                        }

                    }
                });
        findViewById(R.id.exitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}

