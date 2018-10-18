package inclass02.poorna.com.group4_inclass12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThreadActivity extends AppCompatActivity {
    EditText edThreadTitle;
    ListView mylist;
    FirebaseUser user;
    String name;
    FirebaseDatabase database;
    DatabaseReference dbref, ref, uref;
    private FirebaseAuth mAuth;
    Map<String, Object> threadMap;
    ArrayList<ChatThread> chtlist;
    ThreadAdapter adapter;
    public static String FIRST_KEY = "first";
    public static String SEC_KEY = "second";
    String dispname;
    TextView nametext;
    String lname;
    String fullname;
    User usobjval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        setTitle("Message Threads");
        mylist = findViewById(R.id.mylistview);

        edThreadTitle = findViewById(R.id.add_text);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        nametext = findViewById(R.id.name_text);
//        if (user != null) {
//            dispname = user.getDisplayName();
//        }
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference("Threads");
        chtlist = new ArrayList();
        ref = database.getReference();
        uref = database.getReference();
        Log.d("demo", "user val=" + mAuth.getCurrentUser().getUid());
        uref.child("Users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usobjval=dataSnapshot.getValue(User.class);
                Log.d("demo user", String.valueOf(usobjval));
                fullname=usobjval.getFname()+" "+usobjval.getLname();
                nametext.setText(fullname);
                Log.d("demo","fullname="+fullname);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("Threads").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatThread cht = dataSnapshot.getValue(ChatThread.class);
                chtlist.add(cht);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        adapter = new ThreadAdapter(ThreadActivity.this, R.layout.layout_thread, chtlist);
        mylist.setAdapter(adapter);


        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edThreadTitle.getText().length() != 0) {
                    String title = edThreadTitle.getText().toString();
                    Log.d("demo", "title=" + title);
                    addThread(title);
                    edThreadTitle.setText("");


                } else {
                    Toast.makeText(ThreadActivity.this, "Please enter Thread name", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent in=new Intent(ThreadActivity.this,MainActivity.class);
                startActivity(in);
                finishAffinity();
            }
        });
    }


    public void addThread(String title) {
        ChatThread chatThread = new ChatThread();
        chatThread.setTitle(title);
        chatThread.setUid(mAuth.getUid());
        chatThread.setMsgList(null);

        //threadMap=new HashMap<>();
        String key = dbref.push().getKey();
        Log.d("demo", "key=" + key);
        chatThread.setKey(key);
        dbref.child(key).setValue(chatThread);
    }


    public void getMessages(ChatThread th) {
        Intent in = new Intent(ThreadActivity.this, MessageActivity.class);
        in.putExtra(FIRST_KEY, th);
        in.putExtra(SEC_KEY,fullname);
        startActivity(in);
    }

    public void removeThread(ChatThread th, int position) {
        String removekey = th.getKey();
        DatabaseReference newref = database.getReference();
        ref.child("Threads").child(removekey).removeValue();
        chtlist.remove(position);
        adapter.notifyDataSetChanged();
    }
}
