package inclass02.poorna.com.group4_inclass13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComposeActivity extends AppCompatActivity {
    Toolbar toolbar;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dbref, newref;
    ArrayList<User> userlist;
    ArrayList<String> strlist;
    EditText edcontent;
    TextView tuser;
    User usobjval, loggeduser;
    String receiveid;
    String msgkey, dispname;
    Message msgobj,msgintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_compose);
        toolbar = (Toolbar) findViewById(R.id.toolbaract);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("        Compose Message");
        edcontent = findViewById(R.id.content_send);
        msgobj = new Message();
        tuser = findViewById(R.id.user_send);
        userlist = new ArrayList<>();
        strlist = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            dispname = user.getDisplayName();
        }
        database = FirebaseDatabase.getInstance();
        dbref = database.getReference();
        newref = database.getReference("Mailboxes");

        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
                msgintent= (Message) getIntent().getExtras().getSerializable(ReadActivity.SECOND_KEY);
                tuser.setText(msgintent.getSender());
                findViewById(R.id.contact_send).setVisibility(View.INVISIBLE);
        }
        Log.d("demo", "currentuid=" + mAuth.getUid());
        dbref.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                usobjval = dataSnapshot.getValue(User.class);
                Log.d("demo user", String.valueOf(usobjval));
                userlist.add(usobjval);
                strlist.add(usobjval.getName());
                Log.d("demo", "userlist=" + userlist);

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


        findViewById(R.id.contact_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ComposeActivity.this);
                builder.setTitle("Users");
                builder.setCancelable(false);
                builder.setItems(strlist.toArray(new CharSequence[strlist.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        receiveid = userlist.get(which).getUserid();
                        tuser.setText(strlist.get(which));
                        Log.d("demo", "receiveid=" + receiveid);
                    }
                });
                builder.create();
                builder.show();
            }

        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgobj.setMsgcontent(edcontent.getText().toString());
                msgobj.setSender(dispname);
                msgobj.setSenderid(mAuth.getUid());
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
                String formattedDate = sdf.format(dt).toString();
                String timeformat = sdf2.format(dt).toString();
                String tottime = formattedDate + ", " + timeformat;
                msgobj.setDateandtime(tottime);
                msgobj.setRead(false);

                if(msgintent!=null)
                {
                   String msg2key = newref.child(msgintent.getSenderid()).child("Messages").push().getKey();
                    msgobj.setMsgkey(msg2key);
                    newref.child(msgintent.getSenderid()).child("Messages").child(msg2key).setValue(msgobj);
                    Toast.makeText(ComposeActivity.this,"Message Sent",Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(ComposeActivity.this, InboxActivity.class);
                    startActivity(in);
                    finishAffinity();
                }
                else
                {
                    if (tuser.getText().toString().length() != 0) {

                        msgkey = newref.child(receiveid).child("Messages").push().getKey();
                        msgobj.setMsgkey(msgkey);
                        Log.d("demo", "msobj=" + msgobj);
                        newref.child(receiveid).child("Messages").child(msgkey).setValue(msgobj);
                        Intent in = new Intent(ComposeActivity.this, InboxActivity.class);
                        startActivity(in);
                        finishAffinity();
                    }
                    else {
                        Toast.makeText(ComposeActivity.this, "Please enter all Values", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });
    }
}
