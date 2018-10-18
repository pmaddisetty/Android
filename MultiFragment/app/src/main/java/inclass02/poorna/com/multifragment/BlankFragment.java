package inclass02.poorna.com.multifragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {

fragimplements fimpl;
    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.firstfragment, container, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            fimpl=(fragimplements) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+"should implement this");
        }
//        Log.d("demo","Afrag:on Attach");
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getActivity().findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fimpl.gotosecond();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
    public  interface fragimplements
    {
        public void gotosecond();
    }
}
