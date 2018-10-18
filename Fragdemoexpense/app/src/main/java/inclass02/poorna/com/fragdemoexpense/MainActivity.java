package inclass02.poorna.com.fragdemoexpense;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnFragmentInteractionListener,SecondFragment.OnFragmentInteractionSecond{
    ArrayList<Expense> elist;
    ListView mlist;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //mlist=findViewById(R.id.mylist);
        Log.d("demo","mlist="+mlist);

        getFragmentManager().beginTransaction()
                .add(R.id.container,new FirstFragment(),"firstexpense")
                .commit();
    }

    @Override
    public void gotoadd() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container,new SecondFragment(),"secondexpense")
                .addToBackStack(null)
                .commit();
    }

        @Override
        public void addexpenseobject(Expense exp) {
           elist=new ArrayList<>();
           elist.add(exp);
           Log.d("demo","list="+elist.toString());
            Bundle bundle = new Bundle();
            bundle.putSerializable("arrlist", elist);
            //getFragmentManager().popBackStack();
           FirstFragment ff= (FirstFragment) getFragmentManager().findFragmentByTag("firstexpense");
           ff.setArguments(bundle);
           //ff.setlistview(elist);
            onBackPressed();

        }



}
