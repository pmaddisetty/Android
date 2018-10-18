package com.example.finalexam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.finalexam.utils.Person;
import com.example.finalexam.utils.PersonsAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChristmasListActivity extends AppCompatActivity {
    final String TAG = "demo";
    ListView listView;
    PersonsAdapter personsAdapter;
    ArrayList<Person> persons = new ArrayList<>();
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas_list);
        setTitle(R.string.main_name);

        listView = findViewById(R.id.listview);
        if (getIntent().getExtras() != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            uid= (String) getIntent().getExtras().get("id");
            Log.d("uid",uid);
            Query myQuery = mDatabase.child("users").child(uid).child("persons");
            myQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    persons.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Person person = snapshot.getValue(Person.class);
                        persons.add(person);
                        Log.d("tag", "entered");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.d("persons",persons.toString());
            personsAdapter = new PersonsAdapter(this, R.layout.person_item, persons);
            listView.setAdapter(personsAdapter);

        }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                }
            });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.christmas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_person_menu_item:
                Log.d(TAG, "onOptionsItemSelected: add person");
                Intent intent = new Intent(this, AddPersonActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu_item:
                //logout
                mAuth.signOut();
                // go to the login activity
                Intent i=new Intent(ChristmasListActivity.this,LoginActivity.class);
                startActivity(i);
                // finish this activity
                Log.d(TAG, "onOptionsItemSelected: logout");
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
