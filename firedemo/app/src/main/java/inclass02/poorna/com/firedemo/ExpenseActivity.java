package inclass02.poorna.com.firedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
ListView mylist;
ExpenseAdapter adapter;
ArrayList<Expense> explist;
FirebaseDatabase database;
DatabaseReference ref;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        mylist=findViewById(R.id.expense_list);
        explist=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        DatabaseReference dref=ref.child("Users").child(mAuth.getCurrentUser().getUid());

        dref.addChildEventListener(new ChildEventListener() {
                                      @Override
                                      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                          Expense exp=dataSnapshot.getValue(Expense.class);
                                          explist.add(exp);

                                          adapter.notifyDataSetChanged();

                                      }

                                      @Override
                                      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                      }

                                      @Override
                                      public void onChildRemoved(DataSnapshot dataSnapshot) {


                                      }

                                      @Override
                                      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                      }

                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {

                                      }
                                  });

                adapter = new ExpenseAdapter(ExpenseActivity.this, R.layout.layout_expense,explist );
                mylist.setAdapter(adapter);
                mylist.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        return false;
                    }
                });

                findViewById(R.id.add_imagebtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
                        startActivity(in);
                    }
                });
    }

}
