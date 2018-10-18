package inclass02.poorna.com.firedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView tpwd, temail;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mAuth = FirebaseAuth.getInstance();
        temail=findViewById(R.id.email_edit);
        tpwd=findViewById(R.id.password_edit);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temail.getText().toString().length()==0||tpwd.getText().toString().length()==0)
                {
                    Toast.makeText(MainActivity.this,"Please enter the values",Toast.LENGTH_SHORT);
                }
                else
                {
                    login(temail.getText().toString(),tpwd.getText().toString());
                }

            }
        });

        findViewById(R.id.create_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(in);

            }
        });



    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }




    public void login(String eml, String pwd)
    {
        mAuth.signInWithEmailAndPassword(eml, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent in=new Intent(MainActivity.this,ExpenseActivity.class);
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
