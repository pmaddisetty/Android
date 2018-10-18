package inclass02.poorna.com.group4_inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class    InboxActivity extends AppCompatActivity {
Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference dbref;
    InboxAdapter adapter;
    ListView mylist;
    FirebaseAuth mAuth;
    Message msgobj;
    ArrayList<Message> msglist,reverselist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        mAuth=FirebaseAuth.getInstance();
        msgobj=new Message();
        toolbar=(Toolbar) findViewById(R.id.toolbaract);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("        Inbox");
        mylist=findViewById(R.id.mylistview);
        msglist=new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("Mailboxes");

        findViewById(R.id.img_compose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(InboxActivity.this,ComposeActivity.class);
                startActivity(in);
            }
        });
        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent in=new Intent(InboxActivity.this,MainActivity.class);
                startActivity(in);
                finishAffinity();
            }
        });
        dbref.child(mAuth.getUid()).child("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                msgobj=dataSnapshot.getValue(Message.class);
                msglist.add(0,msgobj);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                }
        });
//        reverselist=msglist;
//        Collections.reverse(reverselist);
        adapter = new InboxAdapter(InboxActivity.this, R.layout.layout_mail, msglist);
        mylist.setAdapter(adapter);
    }
}
