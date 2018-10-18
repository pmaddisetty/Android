package androidintents.poorna.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static  String NAME_KEY="Name";
    public static String age="21";
    static String USER_KEY="User";
    static String PERSON_KEY="Person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        setTitle("Main Activity");
        findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent in= new Intent(MainActivity.this, secondActivity.class);
             /*   Intent in=new Intent("androidintents.poorna.com.myapplication.intent.action.VIEW");
                in.addCategory(Intent.CATEGORY_DEFAULT);*/

              /*  Intent in =new Intent(MainActivity.this, secondActivity.class);
                in.putExtra(NAME_KEY, "Poorna");
                in.putExtra(age,(double)23.5); */
              Intent in= new Intent(MainActivity.this,secondActivity.class);
             /* User user=new User("Poori",21);
              in.putExtra(USER_KEY,user); */
             Person per= new Person("Poori",21,"NC");
                in.putExtra(PERSON_KEY,per);
                startActivity(in);
            }
        });


    }
}
