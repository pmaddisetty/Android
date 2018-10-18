package inclass02.poorna.com.group4_inclass13;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText fname, lname,email,pwd,pwd2;
    String token,finalname;
    int userid;
    Button signup;
    String signupemail,signuppassword;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference dbref,newref;
    Map<String, Object> usermap;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar=(Toolbar) findViewById(R.id.toolbaract);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("        MessageMe!(SignUp)");
        mAuth= FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        newref = database.getReference("Mailboxes");
        dbref=database.getReference("Users");
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        email=findViewById(R.id.email);
        pwd= findViewById(R.id.passwd);
        pwd2=findViewById(R.id.repeatpasswd);
        signup=findViewById(R.id.sign2_btn);
        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(in);
                finishAffinity();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
                } else if (lname.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
                }
                else if(fname.getText().toString().equals(lname.getText().toString())){
                    Toast.makeText(SignupActivity.this, "First Name and Last Name should not be the same", Toast.LENGTH_SHORT).show();
                }
                else if (email.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else if (pwd.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please choose password", Toast.LENGTH_SHORT).show();
                } else if (pwd2.getText().toString().equals("")) {
                    Toast.makeText(SignupActivity.this, "Please Re-enter the password again to verify", Toast.LENGTH_SHORT).show();
                } else if (!(pwd.getText().toString().equals(pwd2.getText().toString()))) {
                    Toast.makeText(SignupActivity.this, "Please enter matching passwords", Toast.LENGTH_SHORT).show();
                } else if (pwd.getText().toString().length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password should be of minimum six characters", Toast.LENGTH_SHORT).show();
                }  else if( !(email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    Log.d("demo","entered validation");
                    Toast.makeText(SignupActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }
                else {
                    callaftersign(fname.getText().toString(), lname.getText().toString(), email.getText().toString(), pwd.getText().toString());
                }


            }
        });
    }

    public void callaftersign(final String firstname, final String lastname, final String metemail, final String metpwd )
    {
        mAuth.createUserWithEmailAndPassword(metemail, metpwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("demo", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name=firstname+" "+lastname;
                            User userobj=new User(name,metemail,user.getUid());
                            dbref.child(user.getUid()).setValue(userobj);
                            newref.child(user.getUid()).setValue(userobj);

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstname+" "+lastname)
                                    .build();

                            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent in=new Intent(SignupActivity.this,InboxActivity.class);
                                        startActivity(in);
                                        finishAffinity();
                                    }
                                }
                            });

                        }
                        else {
                            Log.w("demo", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed."+task.getException() ,
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
