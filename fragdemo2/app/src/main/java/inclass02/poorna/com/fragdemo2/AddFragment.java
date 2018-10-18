package inclass02.poorna.com.fragdemo2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;


public class AddFragment extends Fragment {

    EditText edname,eddesc,edyear,edimdb;
    Spinner sp;
    SeekBar sb;
    Movie  mov=new Movie();

    private OnFragmentAddMovie mListener;

    public AddFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_add, container, false);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAddMovie) {
            mListener = (OnFragmentAddMovie) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.add_moviebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edname=getActivity().findViewById(R.id.edname);
                eddesc=getActivity().findViewById(R.id.eddesc);
                edyear=getActivity().findViewById(R.id.edyear);
                edimdb=getActivity().findViewById(R.id.edimdb);
                sp=getActivity().findViewById(R.id.addspinner);
                sb=getActivity().findViewById(R.id.addseek);
                mov.setTitle(edname.getText().toString());
                mov.setDescription(eddesc.getText().toString());
                mov.setYear(edyear.getText().toString().trim());
                mov.setImdb(edimdb.getText().toString());
                mov.setRating(sb.getProgress());
                mov.setGenre(sp.getSelectedItem().toString());
                Log.d("demo","mov in frag="+mov);
                mListener.onAddMovie(mov);
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentAddMovie {
        void onAddMovie(Movie movie);
    }
}
