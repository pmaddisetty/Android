package inclass02.poorna.com.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AFragment.OnFragmentImplements {
    TextView  ta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        RadioGroup rg= findViewById(R.id.rg1);
       ta=findViewById(R.id.rtext);

       getFragmentManager().beginTransaction()
               .add(R.id.container, new AFragment(),"tag_afrag")
               .commit();
        getFragmentManager().beginTransaction()
                .add(R.id.container, new AFragment(),"tag_afrag1")
                .commit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

//                AFragment f=(AFragment) getFragmentManager().findFragmentById(R.id.fragment);
//                if(i==R.id.rbred)
//                {
//                    f.changecolor(Color.RED);
//                }
//                else if(i==R.id.rbgreen)
//                {
//                    f.changecolor(Color.GREEN);
//                }
//                else if(i==R.id.rbblue)
//                {
//                    f.changecolor(Color.BLUE);
//                }

                AFragment f=(AFragment) getFragmentManager().findFragmentByTag("tag_afrag");
                AFragment af=(AFragment) getFragmentManager().findFragmentByTag("tag_afrag1");
                if(i==R.id.rbred)
                {
                    f.changecolor(Color.RED);
                    af.changecolor(Color.RED);
                }
                else if(i==R.id.rbgreen)
                {
                    f.changecolor(Color.GREEN);
                    af.changecolor(Color.GREEN);
                }
                else if(i==R.id.rbblue)
                {
                    f.changecolor(Color.BLUE);
                    af.changecolor(Color.BLUE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("demo","Main : on Start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("demo","Main : on Destroy");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("demo","Main : on Resume");
    }

    @Override
    public void onTextChanged(String str) {
        Log.d("demo",str);
        ta.setText(str);
    }
}
