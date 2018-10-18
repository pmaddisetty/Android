package inclass02.poorna.com.fragdemoexpense;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by poorn on 3/19/2018.
 */

public class ListAdapter extends ArrayAdapter<Expense> {
    public ListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Expense> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Expense ep= getItem(position);
        Log.d("demo","entered getview");
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.layout_disp,parent,false);
            Log.d("demo","entered list");
        }
        TextView tn=convertView.findViewById(R.id.tname);
        TextView ta=convertView.findViewById(R.id.tamount);
        tn.setText(ep.getExpname());
        Log.d("demo","name="+ep.getExpname());
        ta.setText("$"+ep.getAmount());
        Log.d("demo","amount-="+ep.getAmount());
        return convertView;
    }
}
