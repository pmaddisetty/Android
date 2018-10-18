package inclass02.poorna.com.inclass08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener,SecondFragment.OnEditFragDetails,NameEdit.OnGotoDisplay {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new FirstFragment(),"firstdetails")
                .commit();

    }

    @Override
    public void addStudentObject(Student st) {
        if(st!=null)
        {
            Log.d("demo","stu object="+st.toString());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container,SecondFragment.newInstance(st),"seconddetails")
                    .commit();
        }
    }

    @Override
    public void onEditDetails(Student stu, int check) {

        Log.d("demo","entered third activity");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, NameEdit.newInstance(stu,check),"thirddetails")
                .addToBackStack(null)
                .commit();
        //onBackPressed();
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0)
        {
            getFragmentManager().popBackStack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void ongotodisp() {
        onBackPressed();
    }
}
