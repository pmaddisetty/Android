package inclass02.poorna.com.listproj;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by poorn on 2/25/2018.
 */

public class EmailAdapter2 extends RecyclerView.Adapter<EmailAdapter2.ViewHolder> {
    ArrayList<Email> arrayList;

    public EmailAdapter2(ArrayList<Email> emails)
    {
        arrayList=emails;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_email,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Email email=arrayList.get(position);
        holder.tsubj.setText(email.getSubject());
        holder.tsum.setText(email.getSummary());
        holder.temail.setText(email.getEmail());
        holder.em=email;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tsubj,tsum,temail;
        Email em;
        public ViewHolder(View itemView) {
            super(itemView);
             this.em=em;
             tsubj=itemView.findViewById(R.id.tsubject);
             tsum=itemView.findViewById(R.id.tsummary);
             temail=itemView.findViewById(R.id.temail);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Log.d("demo","item clicked"+em.getSubject());
                 }
             });
            itemView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo","item clicked"+em.getSubject());

                }
            });
        }
    }
}
