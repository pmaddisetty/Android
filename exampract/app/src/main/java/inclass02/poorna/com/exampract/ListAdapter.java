package inclass02.poorna.com.exampract;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by poorn on 3/12/2018.
 */

public class ListAdapter extends ArrayAdapter<iTunes> {
    public ListAdapter(@NonNull Context context, int resource,@NonNull ArrayList<iTunes> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        iTunes itune=getItem(position);
        double price;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_tunes,parent,false);

        }
        TextView tname=convertView.findViewById(R.id.tvname);
        TextView tprice=convertView.findViewById(R.id.tvprice);
        ImageView imv=convertView.findViewById(R.id.imgdisp);
        ImageView imv2=convertView.findViewById(R.id.imgdolar);

        tname.setText(itune.getAppname().toString());
        tprice.setText(String.valueOf(itune.getPrice()));
        price=itune.getPrice();

        if(itune.getThumbnail()==1)
        {
            Picasso.with(getContext()).load(R.drawable.one).into(imv2);
        }
        else if(itune.getThumbnail()==2)
        {
            Picasso.with(getContext()).load(R.drawable.two).into(imv2);
        }
        else if(itune.getThumbnail()==3)
        {
            Picasso.with(getContext()).load(R.drawable.three).into(imv2);
        }
        Picasso.with(getContext()).load(itune.getImageurl()).into(imv);

        return convertView;
    }
}
