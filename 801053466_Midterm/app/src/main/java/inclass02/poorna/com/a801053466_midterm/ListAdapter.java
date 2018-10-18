package inclass02.poorna.com.a801053466_midterm;

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
import java.util.Collections;
import java.util.List;

/**
 * Created by poorn on 3/12/2018.
 */

public class ListAdapter extends ArrayAdapter<Apps> {
    public ListAdapter(@NonNull Context context, int resource, @NonNull List<Apps> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Apps ap=getItem(position);
        ArrayList<String> strlist=new ArrayList<>();
        String str=null;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_apps,parent,false);
        }
        TextView tartist=convertView.findViewById(R.id.artist);
        TextView tappname=convertView.findViewById(R.id.appname);
        TextView tdate=convertView.findViewById(R.id.release);
        TextView tgen=convertView.findViewById(R.id.genre);
        ImageView imv2=convertView.findViewById(R.id.imgdisp);
        tartist.setText(ap.getArtist());
        tappname.setText(ap.getAppname());
        tdate.setText(ap.getReleasedate());
        strlist=ap.getGenlist();
        Collections.sort(strlist);
//        for (String s : strlist) {
//           // System.out.print(s + ", ");
//            s=s+", ";
//            str=s;
//        }
        tgen.setText(strlist.toString());
        Picasso.with(getContext()).load(ap.getImgurl()).into(imv2);
        return convertView;
    }
}
