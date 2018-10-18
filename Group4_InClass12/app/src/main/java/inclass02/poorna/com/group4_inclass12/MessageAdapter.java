package inclass02.poorna.com.group4_inclass12;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context tcontext;
    int tuid;
    ArrayList<ChatMessage> tmessages;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    Date dinp, doup;
    String finaltime,formattime;
    FirebaseAuth mAuth;
    String loggeduser;
    DatabaseReference uref;
    FirebaseDatabase database;

    public MessageAdapter(@NonNull Context context, int uid, @NonNull ArrayList<ChatMessage> msgs) {
        tcontext= context;
        tuid= uid;
        tmessages= (ArrayList<ChatMessage>) msgs;
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChatMessage msg= tmessages.get(position);

        loggeduser=mAuth.getUid();

        holder.tvmsg.setText(msg.getMsgcontent());
        holder.tvusernm.setText(msg.getName());

        //sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        try {
            Log.d("demo","time="+msg.getTimecreated());
            Log.d("demo","time="+mAuth.getCurrentUser());
            dinp=sdf.parse(msg.getTimecreated());
            formattime=sdf.format(dinp);
            doup=sdf.parse(formattime);
            PrettyTime p = new PrettyTime();
            finaltime=p.format(doup);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("demo","pretty value="+finaltime);
        holder.tvtime.setText(finaltime);
        holder.del.setVisibility(View.INVISIBLE);
        holder.del.setEnabled(false);
        if(loggeduser.equals(msg.getUid())){
            holder.del.setEnabled(true);
            holder.del.setVisibility(View.VISIBLE);
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MessageActivity)tcontext).removeMessage(msg,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tmessages.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvmsg, tvusernm, tvtime;
        ImageView del;

        public ViewHolder(View itemView) {
            super(itemView);

            tvmsg = itemView.findViewById(R.id.msg_message);
            tvusernm = itemView.findViewById(R.id.name_message);
            tvtime = itemView.findViewById(R.id.time_message);
            del = itemView.findViewById(R.id.del_message);

        }
    }

}
