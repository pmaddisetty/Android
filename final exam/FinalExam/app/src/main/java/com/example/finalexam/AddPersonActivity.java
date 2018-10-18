package com.example.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalexam.utils.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddPersonActivity extends AppCompatActivity {
    final String TAG = "demo";
    EditText editTextBudget, editTextName;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    Integer totalBudget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        setTitle(R.string.add_person);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        editTextBudget = findViewById(R.id.editTextBudget);
        editTextName = findViewById(R.id.editTextName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_person_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit_person_menu_item:
                String name = editTextName.getText().toString();
                String budgetString = editTextBudget.getText().toString();

                if(name == null || name.equals("")){
                    Toast.makeText(this, "Enter Name !!", Toast.LENGTH_SHORT).show();
                } else if(budgetString == null || budgetString.equals("")){
                    Toast.makeText(this, "Enter Budget !!", Toast.LENGTH_SHORT).show();
                } else{
                    try{
                        int budget = Integer.valueOf(budgetString);
                        if(budget > 0 ){
                            //save person
                            FirebaseUser user=mAuth.getCurrentUser();
                            Person person=new Person();
                            person.setName(name);
                            totalBudget=Integer.parseInt(budgetString);
                            person.setTotalBudget(totalBudget);
                            String pushID = mDatabase.child("users").child(user.getUid()).push().getKey();
                            Map<String, Object> postValues = person.toMap();
                            Map<String, Object> childUpdates = new HashMap<>();
                            //childUpdates.put("/persons/" + pushID, postValues);
                            childUpdates.put("users/" + user.getUid() + "/persons/" + pushID, postValues);

                            mDatabase.updateChildren(childUpdates);
                           // mDatabase.child("users").child(pushID).child("persons").setValue(thread);
                            //then finish activity
                            finish();
                        } else{
                            Toast.makeText(this, "Enter Valid Budget !!", Toast.LENGTH_SHORT).show();
                            editTextBudget.setText("");
                        }
                    } catch (NumberFormatException ex){
                        Toast.makeText(this, "Enter Valid Budget !!", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
