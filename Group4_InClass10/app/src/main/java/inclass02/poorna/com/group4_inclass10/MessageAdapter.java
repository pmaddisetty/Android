package inclass02.poorna.com.group4_inclass10;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by poorn on 4/6/2018.
 */

public class MessageAdapter extends ArrayAdapter<MessageResponse.Messages>{

    Context tcontext;
    int tuid;
    ArrayList<MessageResponse.Messages> tmessages;
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
    Date dinp, doup;
    String finaltime,formattime;

    public MessageAdapter(@NonNull Context context, int uid, @NonNull List<MessageResponse.Messages> msgs) {
        super(context, uid, msgs);
        tcontext= context;
        tuid= uid;
        tmessages= (ArrayList<MessageResponse.Messages>) msgs;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final MessageResponse.Messages msg= tmessages.get(position);

            if(convertView==null)
            {
                convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_message,parent,false);
            }

        TextView tvmsg= convertView.findViewById(R.id.msg_message);
        TextView tvusernm= convertView.findViewById(R.id.name_message);
        TextView tvtime = convertView.findViewById(R.id.time_message);

        tvmsg.setText(msg.getMessage());
        tvusernm.setText(msg.getUser_fname()+" "+msg.getUser_lname());
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
        tvtime.setText(finaltime);
        ImageView del= convertView.findViewById(R.id.del_message);
        del.setVisibility(View.INVISIBLE);
        del.setEnabled(false);
        if(tuid== msg.getUser_id()){
            del.setEnabled(true);
            del.setVisibility(View.VISIBLE);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MessageActivity)tcontext).removeMessage(msg);
            }
        });

        return convertView;
    }
}
