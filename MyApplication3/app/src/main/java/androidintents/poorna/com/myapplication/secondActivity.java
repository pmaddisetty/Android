package androidintents.poorna.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class secondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        setTitle("Second Activity");
        if(getIntent()!=null && getIntent().getExtras()!=null) {
           /*
            Log.d("demo","printed");
            Log.d("demo",getIntent().getExtras().getString(MainActivity.NAME_KEY));
            Toast.makeText(this,getIntent().getExtras().getString(MainActivity.NAME_KEY)+" ,"+ getIntent().getExtras().getDouble(MainActivity.age),Toast.LENGTH_LONG).show();
        */

          /* User user=(User)getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
           Toast.makeText(this,user.toString(),Toast.LENGTH_LONG).show();   */

            Person p = (Person) getIntent().getExtras().getParcelable(MainActivity.PERSON_KEY);
            Toast.makeText(this, p.toString(), Toast.LENGTH_LONG).show();
        }

        findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
