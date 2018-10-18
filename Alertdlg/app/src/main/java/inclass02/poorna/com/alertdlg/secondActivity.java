package inclass02.poorna.com.alertdlg;

import android.content.Intent;
import android.preference.EditTextPreference;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class secondActivity extends AppCompatActivity {
EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et=(EditText)(findViewById(R.id.enter_text));
                String val=et.getText().toString();
                if(val==null||val.length()==0)
                {
                    Log.d("demo","null received");
                    setResult(RESULT_CANCELED);
                }
                else {
                    Intent in = new Intent();
                    in.putExtra(MainActivity.VALUE_KEY,val);
                    Log.d("demo","val received is"+val);
                    setResult(RESULT_OK,in);
                }
                finish();
            }
        });

    }
}
