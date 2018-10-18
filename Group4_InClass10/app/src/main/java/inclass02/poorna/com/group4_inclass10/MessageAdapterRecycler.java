package inclass02.poorna.com.group4_inclass10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by poorn on 4/8/2018.
 */

public class MessageAdapterRecycler extends RecyclerView.Adapter<MessageAdapterRecycler.ViewHolder>{
    Context tcontext;
    int tuid;
    ArrayList<MessageResponse.Messages> tmessages;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    Date dinp, doup;
    String finaltime,formattime;

    public MessageAdapterRecycler(@NonNull Context context, int uid, @NonNull ArrayList<MessageResponse.Messages> msgs) {
        tcontext= context;
        tuid= uid;
        tmessages= (ArrayList<MessageResponse.Messages>) msgs;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final MessageResponse.Messages msg= tmessages.get(position);

        holder.tvmsg.setText(msg.getMessage());
        holder.tvusernm.setText(msg.getUser_fname()+" "+msg.getUser_lname());

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            dinp=sdf.parse(msg.getCreated_at());
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
        if(tuid== msg.getUser_id()){
            holder.del.setEnabled(true);
            holder.del.setVisibility(View.VISIBLE);
        }

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MessageActivity)tcontext).removeMessage(msg);
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

            tvmsg= itemView.findViewById(R.id.msg_message);
            tvusernm= itemView.findViewById(R.id.name_message);
            tvtime = itemView.findViewById(R.id.time_message);
            del= itemView.findViewById(R.id.del_message);


        }
    }

}
