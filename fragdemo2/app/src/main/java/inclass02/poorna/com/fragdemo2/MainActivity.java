package inclass02.poorna.com.fragdemo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddFragment.OnFragmentAddMovie {
ArrayList<Movie> movlist=new ArrayList<>();
    StartFragment af;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        getFragmentManager().beginTransaction()
                .add(R.id.container,new StartFragment(),"startmovie")
                .commit();


    }

    @Override
    public void onAddMovie(Movie movie) {
        Log.d("demo","object="+movie);
        movlist.add(movie);
        Log.d("demo","movlist="+movie);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movielist", movlist);
//        getFragmentManager().beginTransaction()
//                .replace(R.id.container,new StartFragment(),"startmovie")
//                .commit();
        af= (StartFragment) getFragmentManager().findFragmentByTag("startmovie");
       if(af!=null)
       {
           af.setArguments(bundle);
       }
        onBackPressed();
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
