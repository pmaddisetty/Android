package inclass02.poorna.com.inclass02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
     private EditText eda;
     private EditText edw;
     private EditText edhf;
     private EditText edhi;
     int age;
     double bmi;
    double height;
    double weight;
    double finalbmi;
    private TextView result;
    double finalwg,finalwl;
    int ageval,wegval,heival,hehval;
    int hf,hi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        eda= (EditText) findViewById(R.id.age_val);
        edw= (EditText) findViewById(R.id.weight_val);
        edhf= (EditText) findViewById(R.id.height_feet);
        edhi=(EditText) findViewById(R.id.height_inches);
        result=(TextView) findViewById(R.id.result);

        findViewById(R.id.calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ageval=eda.getText().toString().length();
                    wegval=edw.getText().toString().length();
                    heival=edhi.getText().toString().length();
                    hehval=edhf.getText().toString().length();
                    age = Integer.parseInt(eda.getText().toString());
                    weight = Integer.parseInt(edw.getText().toString());
                    hf = Integer.parseInt(edhf.getText().toString());
                    hi = Integer.parseInt(edhi.getText().toString());
                    height = Integer.parseInt(edhf.getText().toString()) * 12 + Integer.parseInt(edhi.getText().toString());
                }

                catch(Exception ex)
                {
                        Log.d("demo","exception occurred");
                }

                    if(ageval==0||wegval==0||hehval==0||heival==0)
                    {
                        Toast.makeText(MainActivity.this, "enter inputs", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (age < 18) {
                            Toast.makeText(MainActivity.this, "Age should be gretaer than 18", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            bmi = (703 * Integer.parseInt(edw.getText().toString())) / (height * height);
                            String str = String.format("%.2f", bmi);

                            if (bmi < 18.5) {
                                finalwg = weight - (25 * height * height) / 703;
                                String fg = String.format("%.2f", finalwg);

                                result.setText("Result\n BMI= " + bmi + "(Underweight) \n UnderWeight BMI range : 18.5-25 \n you will need to gain " + finalwg + "lbs to reach a bmi of 18.5");
                            }
                            if (bmi >= 18.5 && bmi < 25) {
                                result.setText("Result\n BMI= " + bmi + "(Normal) \n Normal BMI range : 18.5-25 \n Keep up the good work");

                            }
                            if (bmi >= 25 && bmi < 30) {
                                finalwl = weight - (25 * height * height) / 703;
                                String fg = String.format("%.2f", finalwg);
                                result.setText("Result\n BMI= " + bmi + "(Overweight) \n Normal BMI range : 18.5-25 \n you will need to lose " + finalwl + "lbs to reach a bmi of 25");

                            }
                            if (bmi > 30) {
                                finalwl = weight - (25 * height * height) / 703;
                                String fg = String.format("%.2f", finalwg);
                                result.setText("Result\n BMI= " + bmi + "(obese) \n Normal BMI range : 18.5-25 \n you will need to loose " + finalwl + " lbs to reach a bmi of 25");

                            }

                        }
                    }

            }
        });

    }
}
