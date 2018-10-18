package com.snehal.inclass2a;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mActionBarToolbar;
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("BMI Calculator");
        mActionBarToolbar.setBackgroundColor(Color.parseColor("#000000"));

        // Access the UI elements
        Button btnCalculae = (Button) findViewById(R.id.buttonCalculateBMI);

        final  EditText editTextWeight=(EditText)findViewById(R.id.editTextWeight);
        final EditText editTextFeet=(EditText)findViewById(R.id.editTextFeet);
        final EditText editTextInches=(EditText)findViewById(R.id.editTextInches);

        final TextView yourBMI = (TextView)findViewById(R.id.textViewYourBMI);
        final TextView yourStatus = (TextView)findViewById(R.id.textViewStatus);

        btnCalculae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    RelativeLayout l1 = (RelativeLayout) findViewById(R.id.relativeLayout);
                    l1.setVisibility(View.VISIBLE);


                    //Check for all inputs should be given
                    if (TextUtils.isEmpty(editTextWeight.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Weight is required", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(editTextFeet.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Height is required", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(editTextInches.getText().toString())) {
                        Toast.makeText(MainActivity.this, "Height is required", Toast.LENGTH_LONG).show();
                    }
                    //All inputs are given
                    else
                    {

                        float bmiValue;

                        //Parsing the given inputs into required format
                        float weightInPounds = Float.parseFloat(editTextWeight.getText().toString());
                        float heightFett = Float.parseFloat(editTextFeet.getText().toString());
                        float heightInches = Float.parseFloat(editTextInches.getText().toString());
                        float finalHeight = heightFett * 12 + heightInches;
                        //Calculate BMI according to given formula
                        bmiValue = 703 * (weightInPounds) / (finalHeight * finalHeight);

                        //Set RelativeLayout to visible
                        RelativeLayout ll = (RelativeLayout) findViewById(R.id.relativeLayout);
                        l1.setVisibility(View.VISIBLE);

                        //Underweight validation
                        if (bmiValue < 18.5) {
                            Toast.makeText(MainActivity.this, "BMI Calculated", Toast.LENGTH_SHORT).show();
                            yourBMI.setText("Your BMI:" + Float.toString(bmiValue));
                            yourStatus.setText("You are Underweight");

                        }
                        //Normal Weight Validation
                        else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
                            Toast.makeText(MainActivity.this, "BMI Calculated", Toast.LENGTH_SHORT).show();
                            yourBMI.setText("Your BMI:" + Float.toString(bmiValue));
                            yourStatus.setText("You are Normal Weight");
                        }
                        //Overweight Validation
                        else if (bmiValue >= 25 && bmiValue <= 29.9) {
                            Toast.makeText(MainActivity.this, "BMI Calculated", Toast.LENGTH_SHORT).show();
                            yourBMI.setText("Yoir BMI:" + Float.toString(bmiValue));
                            yourStatus.setText("Your are Overweight");
                        }
                        //Obesity Validation
                        else if (bmiValue >= 30) {
                            Toast.makeText(MainActivity.this, "BMI Calculated", Toast.LENGTH_SHORT).show();
                            yourBMI.setText("Your BMI:" + Float.toString(bmiValue));
                            yourStatus.setText("You are Obese");
                        }


                    }

                }
                //Invalid Input exception handling
                catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this,"Invalid Input",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
