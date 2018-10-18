package inclass02.poorna.com.multifragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BlankFragment.fragimplements{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        getFragmentManager().beginTransaction()
                .add(R.id.container,new BlankFragment(),"first")
                .commit();

    }


    @Override
    public void gotosecond() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container,new secondfragment(),"second")
                .addToBackStack(null)
                .commit();
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
}
