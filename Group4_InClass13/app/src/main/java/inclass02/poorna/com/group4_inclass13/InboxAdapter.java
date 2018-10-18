package inclass02.poorna.com.group4_inclass13;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class InboxAdapter extends ArrayAdapter<Message> {
    Context tcontext;
    int res;
    int threadid;
    public static String FIRST_KEY="first";
    ArrayList<Message> msglist;
    FirebaseAuth mAuth;
    DatabaseReference dref;
    FirebaseDatabase database;
    String loggedUser;


    public InboxAdapter(@NonNull Context context, int resource, @NonNull List<Message> msgs) {
        super(context, resource, msgs);

        tcontext=context;
        res=resource;
        msglist= (ArrayList<Message>) msgs;
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        loggedUser=mAuth.getUid();
        dref=database.getReference("Mailboxes");

        Log.d("demo", "Inbox adapter: userid="+loggedUser);
        Log.d("demo", "Message list=="+msglist);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Message ms=msglist.get(position);
        Log.d("demo","entered view");
        Log.d("demo", "thread in adapter=" + ms);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_mail, parent, false);
        }
        TextView tname = convertView.findViewById(R.id.name_mail);
        TextView tdate = convertView.findViewById(R.id.time_mail);
        final TextView tcontent = convertView.findViewById(R.id.content_mail);
        final ImageView im = convertView.findViewById(R.id.img_mail);

       if(ms.isRead==true)
       {
           im.setImageResource(R.drawable.circle_grey);
       }
       if(ms.isRead==false)
       {
           im.setImageResource(R.drawable.circle_blue);
       }
        tname.setText(ms.getSender());
        tdate.setText(ms.getDateandtime());
        tcontent.setText(ms.getMsgcontent());

//        im.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((ThreadActivity)tcontext).removeThread(th,position);
//            }
//        });
//
//
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ms.isRead=true;
                Log.d("demo","msguid="+mAuth.getUid());
                Log.d("demo","msgkey="+ms.getMsgkey());
                Log.d("demo","msgkey="+msglist.get(position).getMsgkey());
                dref=database.getReference("Mailboxes");
                dref.child(loggedUser).child("Messages").child(ms.getMsgkey()).setValue(ms);
                Intent in=new Intent(tcontext,ReadActivity.class);
                in.putExtra(FIRST_KEY,ms);
                tcontext.startActivity(in);
                im.setImageResource(R.drawable.circle_grey);

            }
        });

        return convertView;
    }

}
