package inclass02.poorna.com.firedemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edname, edemail,edpwd;
    String fullname, email,pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        mAuth = FirebaseAuth.getInstance();
        edname=findViewById(R.id.fullname_edit);
        edemail=findViewById(R.id.email_edit);
        edpwd=findViewById(R.id.password_edit);

        findViewById(R.id.signup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((edemail.getText().toString().length()!=0)&&(edpwd.getText().toString().length()!=0))

                {
                    createUser(edemail.getText().toString(),edpwd.getText().toString());
                }
                else
                {
                    Toast.makeText(SignupActivity.this,"please enter values",Toast.LENGTH_SHORT);
                }
            }
        });

    }


    public void createUser(String em, String pw)
    {
        mAuth.createUserWithEmailAndPassword(em, pw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent in=new Intent(SignupActivity.this,ExpenseActivity.class);
                            startActivity(in);
                        }
                        else {
                            Log.w("demo", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
