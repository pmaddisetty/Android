package inclass02.poorna.com.firedemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    Context mcontext;
    int mresourse;
    List<Expense> mdata;

    public ExpenseAdapter(@NonNull Context context, int resource, @NonNull List<Expense> objects) {
        super(context, resource, objects);

        this.mcontext=context;
        this.mresourse=resource;
        this.mdata= objects;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresourse,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.exp_namedisp);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.exp_amountdisp);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expense expense= mdata.get(position);
        TextView ename = viewHolder.name;
        TextView eamount = viewHolder.amount;
        ename.setText(expense.getExpname().toString());
        eamount.setText(expense.getAmount().toString());
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView amount;
    }
    }
