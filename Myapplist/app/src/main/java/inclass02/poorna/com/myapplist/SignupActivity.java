package inclass02.poorna.com.myapplist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth.createUserWithEmailAndPassword(email, pwd1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fname + " " + lname)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                            }
                                        }
                                    });

                            writeNewUser(fname,lname,email,user.getUid());
                            Intent i = new Intent(getBaseContext(), MessageThreads.class);
                            i.putExtra("name", mUser.firstName + " " + mUser.lastName);
                            i.putExtra("id", user.getUid());
                            startActivity(i);
                        } else {
                            Toast.makeText(SignUp.this, "Authentication Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
