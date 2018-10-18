package inclass02.poorna.com.listproj;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by poorn on 2/25/2018.
 */

public class EmailAdapter  extends ArrayAdapter<Email> {

    public EmailAdapter(@NonNull Context context, int resource, @NonNull List<Email> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Email email=getItem(position);
        ViewHolder holder;
        if(convertView==null)
        {
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.layout_email,parent,false);
            holder=new ViewHolder();
            holder.tsubject=(TextView)convertView.findViewById(R.id.tsubject);
            holder.tsummary=(TextView)convertView.findViewById(R.id.tsummary);
            holder.temail=(TextView)convertView.findViewById(R.id.temail);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tsubject.setText(email.getSubject());
        holder.tsummary.setText(email.getSummary());
        holder.temail.setText(email.getEmail());

        return convertView;
    }
    public static class ViewHolder
    {
        TextView tsubject;
        TextView tsummary;
        TextView temail;
    }
}
