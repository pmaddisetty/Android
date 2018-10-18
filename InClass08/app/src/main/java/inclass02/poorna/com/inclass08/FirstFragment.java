package inclass02.poorna.com.inclass08;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;


public class FirstFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    EditText edname,edmail;
    RadioGroup rg;
    RadioButton rbsis,rbcs,rbbio,rbothers;
    SeekBar sb;
    int moodst;
    String namest,emailst,deptst;
    Student stu;
    Button submitchg;
    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_first, container, false);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edname=getActivity().findViewById(R.id.name_edit);
        edmail=getActivity().findViewById(R.id.email_edit);
        rg=getActivity().findViewById(R.id.rGroup);
        sb=getActivity().findViewById(R.id.mood_edit);
        submitchg = (Button) getActivity().findViewById(R.id.submitchg);

        submitchg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stu = new Student();
                if (edname.getText().toString().length() != 0 && edmail.getText().toString().length() != 0) {
                    if (edname.getText().toString().matches("[a-zA-Z]+\\s*[a-zA-Z]+")) {
                        if (edmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
                        {
                            stu.setName(edname.getText().toString());
                            int id = rg.getCheckedRadioButtonId();
                            moodst = sb.getProgress();
                            if (id == R.id.sis_edit) {
                                deptst = "SIS";
                            } else if (id == R.id.cs_edit) {
                                deptst = "CS";
                            } else if (id == R.id.bio_edit) {
                                deptst = "BIO";
                            } else if (id == R.id.others_edit) {
                                deptst = "Others";
                            }
                            stu.setEmail(edmail.getText().toString());
                            stu.setMood(moodst);
                            stu.setDepartment(deptst);
                            mListener.addStudentObject(stu);
                        }
                        else {
                            edmail.setError("Please enter a valid email");
                    }
                } else {
                        edname.setError("Please enter a valid name");
                }
            }
                 else
                {
                    Log.d("demo","enyterd else");
                    Toast.makeText(getActivity(),"Please enter values",Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public interface OnFragmentInteractionListener {
        void addStudentObject(Student st);
    }

}
