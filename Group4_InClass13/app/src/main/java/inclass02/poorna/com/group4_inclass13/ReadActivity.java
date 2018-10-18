package inclass02.poorna.com.group4_inclass13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadActivity extends AppCompatActivity {
Message msg;
String TAG="demo";
Toolbar toolbar;
TextView tname,tcontent;
DatabaseReference dref;
FirebaseDatabase database;
FirebaseAuth mAuth;
public static String SECOND_KEY="second";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        toolbar=(Toolbar) findViewById(R.id.toolbaract);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        getSupportActionBar().setTitle("        Read Message");
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        dref=database.getReference("Mailboxes");
        tname=findViewById(R.id.from_read);
        tcontent=findViewById(R.id.msg_read);
        if(getIntent()!=null && getIntent().getExtras()!=null)
        {
            msg= (Message) getIntent().getExtras().getSerializable(InboxAdapter.FIRST_KEY);
            Log.d("demo", "onCreate: msg="+msg);
            tname.setText(" From :  "+ " "+msg.getSender());
            tcontent.setText(msg.getMsgcontent());
            findViewById(R.id.reply_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in=new Intent(ReadActivity.this,ComposeActivity.class);
                    in.putExtra(SECOND_KEY,msg);
                    startActivity(in);
                }
            });

            findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        dref.child(mAuth.getUid()).child("Messages").child(msg.getMsgkey()).removeValue();
                        Intent in=new Intent(ReadActivity.this,InboxActivity.class);
                        startActivity(in);
                }
            });
        }
    }
}
