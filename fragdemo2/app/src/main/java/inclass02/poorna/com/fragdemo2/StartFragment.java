package inclass02.poorna.com.fragdemo2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;


public class StartFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    ArrayList<Movie> movies=new ArrayList<>();
    ArrayList<String> strlist=new ArrayList<>();
    ArrayList<String> atlist;
    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_start, container, false);
        view.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,new AddFragment(),"addmovie")
                        .addToBackStack(null)
                        .commit();
            }
        });
       Bundle bd=new Bundle();
       bd=this.getArguments();
       if(bd!=null)
       {
           movies=(ArrayList<Movie>) bd.getSerializable("movielist");
           if(movies.size()>0)
           {
               for(int i=0;i<movies.size();i++)
               {
                   strlist.add(movies.get(i).getTitle());
               }
               atlist=strlist;
               Log.d("demo","atrlist="+atlist.toString());
           }


       }


        view.findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                       builder.setTitle("Pick up a movie");
                       builder.setCancelable(false);
                       builder.setItems(atlist.toArray(new CharSequence[atlist.size()]), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               Log.d("demo","selected item="+strlist.get(i));
                               Log.d("demo","selectd movie="+movies.get(i).toString());
                               //movies.get(i).getTitle();
                           }
                       });
                       builder.create();
                       builder.show();
           }
       });

               return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     //   mListener = null;
    }

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
