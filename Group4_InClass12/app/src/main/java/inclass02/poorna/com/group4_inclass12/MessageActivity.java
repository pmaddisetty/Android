package inclass02.poorna.com.group4_inclass12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {
    ChatThread ch;
    ChatMessage msgobj;
    FirebaseAuth mAuth;
    MessageAdapter adapter;

    RecyclerView rview;
    MessageAdapter radapter;
    FirebaseUser user;
    EditText edcontent;
    String chatkey;
    ArrayList<ChatMessage> msglist;
    DatabaseReference dbref,ref;
    FirebaseDatabase database;
    TextView titletxt;
    String fullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        rview=findViewById(R.id.myrecycler_view);
        LinearLayoutManager rlayoutmanag=new LinearLayoutManager(MessageActivity.this,LinearLayoutManager.VERTICAL,false);
        rview.setLayoutManager(rlayoutmanag);
        rview.setHasFixedSize(true);
        mAuth=FirebaseAuth.getInstance();
        msglist=new ArrayList<>();
        edcontent=findViewById(R.id.content_message);
        titletxt=findViewById(R.id.thrtitle_message);
        database = FirebaseDatabase.getInstance();
        dbref=database.getReference("Threads");
        ref = database.getReference();
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            ch= (ChatThread) getIntent().getExtras().getSerializable(ThreadActivity.FIRST_KEY);
            fullname=getIntent().getExtras().getString(ThreadActivity.SEC_KEY);
            chatkey=ch.getKey();
            titletxt.setText(ch.getTitle());
            ref.child("Threads").child(chatkey).child("messages").addChildEventListener(new ChildEventListener() {
                @Override

                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    msgobj= dataSnapshot.getValue(ChatMessage.class);
                    msglist.add(msgobj);
                    radapter.notifyDataSetChanged();

                    Log.d("demo","msglist="+msglist+ " " +msglist.size());
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
            findViewById(R.id.send_message).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(edcontent.getText().length() != 0){
                        String msg= edcontent.getText().toString();
                        Log.d("demo","chat key val="+chatkey);
                        addMessage(msg,chatkey,ch);
                        edcontent.setText("");

                    }
                    else{
                        Toast.makeText(MessageActivity.this, "Please enter Your Message", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Collections.reverse(msglist);
            radapter=new MessageAdapter(MessageActivity.this, R.layout.layout_message, msglist);
            rview.setAdapter(radapter);
        }
        findViewById(R.id.home_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MessageActivity.this,ThreadActivity.class);
                startActivity(in);
            }
        });
    }


    public void addMessage(String message,String key,ChatThread chthrd)
    {
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setUid(mAuth.getUid());
        chatMessage.setMsgcontent(message);
        chatMessage.setName(fullname);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        chatMessage.setTimecreated(currentDateandTime);
        chthrd.setMsgList(chatMessage);

        //String msgkey=dbref.push().getKey();
        String msgkey=dbref.child(key).child("messages").push().getKey();

        chatMessage.setKey(msgkey);
       // dbref.child(key).setValue(chatThread);

        dbref.child(key).child("messages").child(msgkey).setValue(chatMessage);
//        //threadMap=new HashMap<>();

    }

    public void removeMessage(ChatMessage msg,int position)
    {
        String removekey=msg.getKey();
        DatabaseReference newref=database.getReference();
        ref.child("Threads").child(chatkey).child("messages").child(removekey).removeValue();
        msglist.remove(position);
        radapter.notifyDataSetChanged();
    }
}
