package inclass02.poorna.com.group4_inclass12;
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

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import inclass02.poorna.com.group4_inclass12.ChatThread;

public class ThreadAdapter extends ArrayAdapter<ChatThread> {

    Context tcontext;
    int tuserid;
    int threadid;
    ArrayList<ChatThread> tthreads;
    FirebaseAuth mAuth;
    String loggedUser;

    public ThreadAdapter(@NonNull Context context, int uid, @NonNull List<ChatThread> threads) {
        super(context, uid, threads);
        Log.d("demo","thread list received= "+threads);
        tcontext=context;
        tuserid=uid;
        tthreads= (ArrayList<ChatThread>) threads;
        mAuth=FirebaseAuth.getInstance();
        loggedUser=mAuth.getUid();
        Log.d("demo", "ThreadAdapter: userid="+loggedUser);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ChatThread th=tthreads.get(position);

            //th=getItem(position);
            Log.d("demo", "thread in adapter=" + th);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_thread, parent, false);
            }
            TextView ta = convertView.findViewById(R.id.title_thread);
            ImageView im = convertView.findViewById(R.id.remove_thread);
            im.setVisibility(View.INVISIBLE);
            im.setEnabled(false);
            if (loggedUser .equals(th.getUid())) {
                im.setEnabled(true);
                im.setVisibility(View.VISIBLE);
            }
            ta.setText(th.getTitle());

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ThreadActivity)tcontext).removeThread(th,position);
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("item clicked","thread id="+th.getUid()+""+th.getTitle());
                ((ThreadActivity)tcontext).getMessages(th);
            }
        });

        return convertView;
    }


}
