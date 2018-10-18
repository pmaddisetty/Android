package inclass02.poorna.com.fragdemoexpense;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FirstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class FirstFragment extends Fragment {
    ListView mlist;
    Activity at;
    ListAdapter adapter;
    ArrayList<Expense> expenselist;
    private OnFragmentInteractionListener mListener;
    ArrayList<String> att=new ArrayList<>();
    ArrayList<Expense> expelist=new ArrayList<>();

    public FirstFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        att.add("dello");
        att.add("dhdk");
        att.add("fmld");

        View view= inflater.inflate(R.layout.fragment_first, container, false);

       //ArrayAdapter adap= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,att);
       mlist=view.findViewById(R.id.mylist);
       //mlist.setAdapter(adap);
        Bundle bd=this.getArguments();
        if(bd!=null)
        {

            expelist= (ArrayList<Expense>) bd.getSerializable("arrlist");
            Log.d("demo","entered list"+expelist.toString());
            adapter=new ListAdapter(at,R.layout.layout_disp,expelist);
            mlist.setAdapter(adapter);
        }

            return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
         at=getActivity();
         expenselist= new ArrayList<>();
       //f mlist=getActivity().findViewById(R.id.mylist);
//        Bundle bundle = this.getArguments();
//        if(bundle!=null)
//        {
//            expenselist=
//        }
//        Log.d("list",expenselist+"");
        getActivity().findViewById(R.id.img_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.gotoadd();
            }
        });
       // adapter=new ListAdapter (getActivity(), android.R.layout.simple_list_item_1, );

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void gotoadd();
    }
    public void setlistview(ArrayList<Expense> exp)
    {
        Log.d("demo","exp="+exp.toString());
        //Log.d("demo","adapter count="+adapter.getCount());
        adapter=new ListAdapter(at,R.layout.layout_disp,exp);
        Log.d("demo","adapter count="+adapter.getCount());
        mlist.setBackgroundColor(Color.RED);
        mlist.setAdapter(adapter);


    }

}
