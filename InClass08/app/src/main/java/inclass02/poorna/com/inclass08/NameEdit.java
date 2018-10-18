package inclass02.poorna.com.inclass08;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class NameEdit extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    int check;
    String name,email,dept,mood;
    EditText edname,edemail;
    TextView tdept,tmood;
    SeekBar sb1;
    RadioButton rb1,rb2,rb3,rb4;
    String dep1,dep2,dep3,dep4;
    Student student;
    RadioGroup rg1;
    String val;

    private OnGotoDisplay mListener;
    public NameEdit() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NameEdit newInstance(Student student,int check) {
        NameEdit fragment = new NameEdit();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, student);
        args.putInt(ARG_PARAM2, check);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          student = (Student) getArguments().getSerializable(ARG_PARAM1);
          check = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_name_edit, container, false);
        edname=view.findViewById(R.id.name_edit);
        edemail=view.findViewById(R.id.email_edit);
        rg1=view.findViewById(R.id.rGroup);
        tdept=view.findViewById(R.id.department);
        tmood=view.findViewById(R.id.current_mood);
        sb1=view.findViewById(R.id.mood_edit);
        rb1=view.findViewById(R.id.sis_edit);
        rb2=view.findViewById(R.id.cs_edit);
        rb3=view.findViewById(R.id.bio_edit);
        rb4=view.findViewById(R.id.others_edit);

        if(check==1)
        {
            edname.setText(student.getName());
            edemail.setVisibility(View.INVISIBLE);
            rg1.setVisibility(View.INVISIBLE);
            tdept.setVisibility(View.INVISIBLE);
            tmood.setVisibility(View.INVISIBLE);
            sb1.setVisibility(View.INVISIBLE);
        }
        if(check==2)
        {
            edname.setVisibility(View.INVISIBLE);
            edemail.setVisibility(View.VISIBLE);
            edemail.setText(student.getEmail());
            rg1.setVisibility(View.INVISIBLE);
            tdept.setVisibility(View.INVISIBLE);
            tmood.setVisibility(View.INVISIBLE);
            sb1.setVisibility(View.INVISIBLE);

        }
        if(check==3)
        {
            edname.setVisibility(View.INVISIBLE);
            edemail.setVisibility(View.INVISIBLE);
            rg1.setVisibility(View.VISIBLE);
            tdept.setVisibility(View.VISIBLE);
            String dept=student.getDepartment();
            Log.d("demo",dept);
            dep1=getResources().getString(R.string.sis_edit);
            dep2=getResources().getString(R.string.cs_edit);
            dep3=getResources().getString(R.string.bio_edit);
            dep4=getResources().getString(R.string.others_edit);
            Log.d("demo","depat2"+dep4);
            if(dept.equalsIgnoreCase(dep1))
            {
                rb1.setChecked(true);
            }
            if(dept.equalsIgnoreCase(dep2)){ rb2.setChecked(true);}
            if(dept.equalsIgnoreCase(dep3)){ rb3.setChecked(true);}
            if(dept.equalsIgnoreCase(dep4)){
                Log.d("demo","depat3"+dept);
                rb4.setChecked(true);
            }
            else{
                Log.d("demo","not equal"+dept+dep4);
            }
            tmood.setVisibility(View.INVISIBLE);
            sb1.setVisibility(View.INVISIBLE);
        }
        if(check==4)
        {
            edname.setVisibility(View.INVISIBLE);
            edemail.setVisibility(View.INVISIBLE);
            rg1.setVisibility(View.INVISIBLE);
            tdept.setVisibility(View.INVISIBLE);
            tmood.setVisibility(View.VISIBLE);
            sb1.setVisibility(View.VISIBLE);
            sb1.setProgress(student.getMood());
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGotoDisplay) {
            mListener = (OnGotoDisplay) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.submit_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check==1)
                {
                    val = edname.getText().toString();
                    String namepattern = "[a-zA-Z]+\\s?+[a-zA-Z]+";
                    if (val.matches(namepattern)&& val.trim().length()!= 0) {
                       // Toast.makeText(getActivity(), "Please Enter Value", Toast.LENGTH_SHORT).show();
                        student.setName(val);
                        mListener.ongotodisp();
                    }
                    else {
                        edname.setError("Please enter a valid Name");
                    }
                }
                else if(check==2)
                {
                    String val1=edemail.getText().toString();
                    if (val1 == null||val1.trim().length()==0)
                    {
                        Toast.makeText(getActivity(),"Please Enter value",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String email = edemail.getText().toString().trim();
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (email.matches(emailPattern)) {
                            student.setEmail(val1);
                            mListener.ongotodisp();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Invalid email",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else if(check==3)
                {
                    int id=rg1.getCheckedRadioButtonId();
                    if(id==R.id.sis_edit){
                        val="SIS";
                    }
                    if(id==R.id.cs_edit){val="CS";}
                    if(id==R.id.bio_edit){val="BIO";}
                    if(id==R.id.others_edit){val="Others";}
                    student.setDepartment(val);
                    mListener.ongotodisp();
                }
                else if(check==4)
                {
                    int prg=sb1.getProgress();
                    student.setMood(prg);
                    mListener.ongotodisp();
                }
            }
        });
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnGotoDisplay {
        void ongotodisp();
    }


}
