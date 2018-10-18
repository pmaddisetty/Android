package inclass02.poorna.com.group4_inclass12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private  FirebaseUser user;
    EditText edemail, edpwd;
    String email,password;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        if(user!=null)
        {
            Log.d("demo","user not null");
            Intent in=new Intent(MainActivity.this,ThreadActivity.class);
            startActivity(in);
            finishAffinity();
       }
        setContentView(R.layout.layout_main);
        mAuth=FirebaseAuth.getInstance();
        setTitle("Chat Room");
        edemail=findViewById(R.id.logemail);
        edpwd=findViewById(R.id.logpwd);
        findViewById(R.id.log1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edemail.getText().toString().length() != 0 && edpwd.getText().toString().length() != 0) {
                    performLogin(edemail.getText().toString(), edpwd.getText().toString());
                } else {
                    Toast.makeText(getBaseContext(), "Please Enter values", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.sign1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getBaseContext(), SignupActivity.class);
                startActivity(in);
            }
        });
    }


    public void performLogin(String eml, String pwd)
    {
        mAuth.signInWithEmailAndPassword(eml, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Intent in=new Intent(MainActivity.this,ThreadActivity.class);
                            startActivity(in);

                        }
                        else {

                            Log.w("demo", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
