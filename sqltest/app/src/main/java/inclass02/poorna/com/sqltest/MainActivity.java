package inclass02.poorna.com.sqltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 DatabaseDataManager dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        dm=new DatabaseDataManager(this);
        dm.save(new Note("Note1 sub","note1 text"));
        dm.save(new Note("Note2 sub","note2 text"));

        List<Note> noteList=new ArrayList<>();
        noteList=dm.getAll();
        Log.d("demo",noteList.toString());

    }

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }
}
