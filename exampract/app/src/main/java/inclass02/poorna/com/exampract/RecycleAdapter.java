package inclass02.poorna.com.exampract;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by poorn on 3/12/2018.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>  {

    ArrayList<iTunes> arrayList;
    Context cont;
    int res;
    public static String MAIN_KEY="main";

    public RecycleAdapter(Context context,int resource,ArrayList<iTunes> tunes)
    {
        this.arrayList=tunes;
        this.cont=context;
        this.res=resource;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_modified,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        iTunes it=arrayList.get(position);
        holder.tname.setText(it.getAppname());
        holder.tprice.setText(String.valueOf(it.getPrice()));
         Double price=it.getPrice();

        if(it.getThumbnail()==1)
        {
            Picasso.with(cont).load(R.drawable.one).into(holder.imv2);
        }
        else if(it.getThumbnail()==2)
        {
            Picasso.with(cont).load(R.drawable.two).into(holder.imv2);
        }
        else if(it.getThumbnail()==3)
        {
            Picasso.with(cont).load(R.drawable.three).into(holder.imv2);
        }
        Picasso.with(cont).load(it.getImageurl()).into(holder.imv1);
        holder.itun=it;
        holder.imv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(position);
                notifyItemChanged(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tname,tprice;
        ImageView imv1,imv2,imv3;
        iTunes itun;
        Context conte;
        public ViewHolder(View itemView) {
            super(itemView);
            conte=itemView.getContext();
             tname=itemView.findViewById(R.id.tvname);
             tprice=itemView.findViewById(R.id.tvprice);
             imv1=itemView.findViewById(R.id.imgdisp);
            imv2 =itemView.findViewById(R.id.imgdolar);
            imv3=itemView.findViewById(R.id.deleteimg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(conte,ThirdActivity.class);
                    in.putExtra(MAIN_KEY,itun);
                    conte.startActivity(in);
                }
            });

        }
    }

}
