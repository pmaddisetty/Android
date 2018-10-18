package com.example.poorn.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
       // String s= "ok_button";
       String s= getResources().getString(R.string.ok_button);
       Log.d("demo",s);
       String [] colors=getResources().getStringArray(R.array.colors_array);

       for(String str : colors)
       {
           Log.d("demo",str);
       }
      //  Log.d("demo", s);
        RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb= (RadioButton)findViewById(checkedId);
                Log.d("demo","checked id is "+rb.getText().toString());
            }
        });
        findViewById(R.id.button_check).setOnClickListener(new View.OnClickListener() {
            RadioGroup rg=(RadioGroup)findViewById(R.id.radioGroup);
            @Override
            public void onClick(View view) {
                int id=rg.getCheckedRadioButtonId();
                if(id==R.id.radio_red)
                { Log.d("demo","checked id is red");}
                else if(id==R.id.radio_blue)
                {  Log.d("demo","checked id is blue");}
                else if(id==R.id.radio_green)
                { Log.d("demo","checked id is green");}
            }
        });

        SeekBar sb= (SeekBar)findViewById(R.id.seekBar_progress);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Log.d("demo","value is "+progress);
                TextView t= (TextView)findViewById(R.id.progress_val);
                t.setText(progress+ "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.image_View).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","image clicked");
            }
        });



        Button bt= (Button) findViewById(R.id.button_ok);
        Log.d("demo", "button text is "+bt.getText().toString());
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("demo","ok button clicked");
            }
        });
        findViewById(R.id.button_cancel).setOnClickListener(this);
        findViewById(R.id.button_othercancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()== R.id.button_cancel)
        {
            Log.d("demo","Cancel button clicked");
        }
        else
        {
            Log.d("demo","Other Cancel button clicked");
        }
    }
    public void otherButtonClicked(View view)
    {
        Log.d("demo","Other button clicked");
    }
}
