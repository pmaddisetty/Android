package inclass02.poorna.com.fragdemoexpense;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class SecondFragment extends Fragment {
    EditText edname,edcat,edamount;
    int amount;
    Spinner sp;
    Date dt=new Date();
    Expense ep=new Expense();
    private OnFragmentInteractionSecond mListener;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionSecond) {
            mListener = (OnFragmentInteractionSecond) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edname=getActivity().findViewById(R.id.exp_edit);
                sp=getActivity().findViewById(R.id.spinner_cat);
                ep.setExpname(edname.getText().toString());
                ep.setCategory(sp.getSelectedItem().toString());
                edamount=getActivity().findViewById(R.id.amount_edit);
                ep.setAmount(Integer.parseInt(edamount.getText().toString()));
                dt= Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = df.format(dt);
                ep.setDate(formattedDate);
                Log.d("demo","value of exp is"+ep.toString());
                mListener.addexpenseobject(ep);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    public interface OnFragmentInteractionSecond {
        void addexpenseobject(Expense exp);
    }
}
