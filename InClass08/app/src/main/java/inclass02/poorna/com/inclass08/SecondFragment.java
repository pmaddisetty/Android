package inclass02.poorna.com.inclass08;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



public class SecondFragment extends Fragment {

    Student student;
    Activity at;
    TextView tname,temail,tdept,tmood;
    ImageView imgname,imgemail,imgdepartment,imgmood;
    private static final String ARG_PARAM2 = "param2";
    int check;
    private OnEditFragDetails mListener;

    public SecondFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            student = (Student) getArguments().getSerializable(ARG_PARAM1);
        }
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
        if (context instanceof OnEditFragDetails) {
            mListener = (OnEditFragDetails) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    private static final String ARG_PARAM1 = "param1";

    public static SecondFragment newInstance(Student student) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, student);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        at=getActivity();
        if(student !=null)
        {
            Log.d("demo","stu in second="+ student);
            tname=at.findViewById(R.id.edit_name);
            temail=at.findViewById(R.id.edit_email);
            tdept=at.findViewById(R.id.edit_dept);
            tmood=at.findViewById(R.id.edit_mood);
            tname.setText(student.getName().toString());
            temail.setText(student.getEmail().toString());
            tdept.setText(student.getDepartment().toString());
            tmood.setText(student.getMood()+"% Positive");
            imgname=getActivity().findViewById(R.id.name_img);
            imgemail=getActivity().findViewById(R.id.email_img);
            imgdepartment=getActivity().findViewById(R.id.dept_img);
            imgmood=getActivity().findViewById(R.id.mood_img);

            imgname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check=1;
                    mListener.onEditDetails(student,check);
                }
            });

            imgemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check=2;
                    mListener.onEditDetails(student,check);
                }
            });
            imgdepartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check=3;
                    mListener.onEditDetails(student,check);
                }
            });
            imgmood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    check=4;
                    mListener.onEditDetails(student,check);
                }
            });


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
       mListener = null;
    }

  public interface OnEditFragDetails {
        void onEditDetails(Student stu,int check);
   }
}
