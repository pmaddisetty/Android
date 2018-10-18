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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by poorn on 4/5/2018.
 */

public class ThreadAdapter extends ArrayAdapter<ThreadResponse.MessageThread> {

Context tcontext;
int tuserid;
int threadid;

ArrayList<ThreadResponse.MessageThread> tthreads;
    public ThreadAdapter(@NonNull Context context, int uid, @NonNull List<ThreadResponse.MessageThread> threads) {
        super(context, uid, threads);
        tcontext=context;
        tuserid=uid;
        tthreads= (ArrayList<ThreadResponse.MessageThread>) threads;
        Log.d("demo", "ThreadAdapter: userid"+tuserid);


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ThreadResponse.MessageThread th=tthreads.get(position);
        //th=getItem(position);
        Log.d("demo","thread in adapter="+th);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_thread,parent,false);
        }
        TextView ta=convertView.findViewById(R.id.title_thread);
        ImageView im= convertView.findViewById(R.id.remove_thread);
        //threadid=th.getId();
        im.setVisibility(View.INVISIBLE);
        im.setEnabled(false);
        if(tuserid== th.getUser_id()){
            im.setEnabled(true);
            im.setVisibility(View.VISIBLE);
        }
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // tthreads.remove(position);
                Log.d("demo", "onClick: deleting thread at position="+position);
                Log.d("demo","thread id="+th.getId());

                Log.d("demo","thread id="+th.toString()+" "+tthreads.get(position));
                ((ThreadActivity)tcontext).removeThread(th);
            }
        });

        ta.setText(th.getTitle());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("item clicked","thread id="+th.getId()+""+th.getTitle());
                ((ThreadActivity)tcontext).getMessages(th);
            }
        });

        return convertView;
    }
}
