package inclass02.poorna.com.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AFragment extends android.app.Fragment {

    private OnFragmentInteractionListener mListener;

    public AFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_a, container, false);
//        view.findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"button",Toast.LENGTH_SHORT);
//            }
//        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    OnFragmentImplements fragimps;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            fragimps=(OnFragmentImplements) context;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("demo","Afrag:on Activity create");
//        getActivity().findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"button",Toast.LENGTH_LONG).show();
//                EditText ed= getActivity().findViewById(R.id.edmsg);
//               fragimps.onTextChanged(ed.getText().toString());
//                Log.d("demo","button clicked");
//            }
//        });
        getView().findViewById(R.id.click_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"button",Toast.LENGTH_LONG).show();
                EditText ed= getView().findViewById(R.id.edmsg);
                fragimps.onTextChanged(ed.getText().toString());
                Log.d("demo","button clicked");
            }
        });


    }

    public void changecolor(int color)
    {
            getActivity().findViewById(R.id.fragment_root).setBackgroundColor(color);
        getView().setBackgroundColor(color);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public interface OnFragmentImplements
    {
        public void onTextChanged(String str);
    }
}
