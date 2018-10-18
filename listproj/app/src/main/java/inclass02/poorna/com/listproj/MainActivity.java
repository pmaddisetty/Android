package inclass02.poorna.com.listproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
ArrayList<Color> data;
ArrayList<Email> aemaillist=new ArrayList<>();
RecyclerView rview;
RecyclerView.LayoutManager rlayoutmanag;
RecyclerView.Adapter radapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* setContentView(R.layout.layout_main);*/
       setContentView(R.layout.layout_main2);
        aemaillist.add(new Email("hello","bsmith@tet.com","welcome"));
        aemaillist.add(new Email("hello1","bsmith1@tet.com","welcome1"));
        aemaillist.add(new Email("hello2","bsmith2@tet.com","welcome2"));
        aemaillist.add(new Email("hello3","bsmith3@tet.com","welcome3"));
        aemaillist.add(new Email("hello4","bsmith4@tet.com","welcome4"));
        aemaillist.add(new Email("hello5","bsmith5@tet.com","welcome5"));
        aemaillist.add(new Email("hello6","bsmith6@tet.com","welcome6"));
        aemaillist.add(new Email("hello7","bsmith7@tet.com","welcome7"));
        aemaillist.add(new Email("hello8","bsmith8@tet.com","welcome8"));

        rview=findViewById(R.id.myrecycler_view);
       rview.setHasFixedSize(true);
       rlayoutmanag=new LinearLayoutManager(this);
       rview.setLayoutManager(rlayoutmanag);
       radapter=new EmailAdapter2(aemaillist);
       rview.setAdapter(radapter);


       // final String[] colors=new String[]{"red","yellow","blue","green"};
        //final Color[] colorobj={new Color("red"),new Color("yellow"),new Color("blue"),new Color("green")};
//        data=new ArrayList<>();
//        data.add(new Color("red"));
//        data.add(new Color("yellow"));
//        data.add(new Color("blue"));
//        data.add(new Color("green"));


        /*aemaillist.add(new Email("hello","bsmith@tet.com","welcome"));
        aemaillist.add(new Email("hello1","bsmith1@tet.com","welcome1"));
        aemaillist.add(new Email("hello2","bsmith2@tet.com","welcome2"));
        aemaillist.add(new Email("hello3","bsmith3@tet.com","welcome3"));
        aemaillist.add(new Email("hello4","bsmith4@tet.com","welcome4"));
        aemaillist.add(new Email("hello5","bsmith5@tet.com","welcome5"));
        aemaillist.add(new Email("hello6","bsmith6@tet.com","welcome6"));
        aemaillist.add(new Email("hello7","bsmith7@tet.com","welcome7"));
        aemaillist.add(new Email("hello8","bsmith8@tet.com","welcome8"));

        ListView mylist=findViewById(R.id.listView);
        EmailAdapter adapter=new EmailAdapter(this,R.layout.layout_email,aemaillist);
        mylist.setAdapter(adapter); */



       // ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,colors);
       // final ArrayAdapter<Color> adapter=new ArrayAdapter<Color>(this,android.R.layout.simple_list_item_1,android.R.id.text1,data);
        //        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText ed1=findViewById(R.id.edtext);
//               adapter.add(new Color(ed1.getText().toString()));
//                adapter.notifyDataSetChanged();
//
//            }
//        });
//
//        mylist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                                              @Override
//        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//        Color clr=adapter.getItem(i);
//        adapter.remove(clr);
//        adapter.notifyDataSetChanged();
//        return false;
//     }
//    });

        /*mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Log.d("demo", "item clicked at" + i );
                        }
                    });   */
    }
    static class Color{
        String name;
        int hex;
        public Color(String name){
            this.name=name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
