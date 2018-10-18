
package inclass02.poorna.com.firedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddExpenseActivity extends AppCompatActivity {
EditText edexpname,edexpamount;
Spinner sp;

DatabaseReference dbref;
FirebaseAuth mAuth;
FirebaseUser currentUser;
    static Map<String,Object> expenseMap;

    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        edexpname=findViewById(R.id.expname_edit);
        edexpamount=findViewById(R.id.amount_edit);
        sp=findViewById(R.id.spinner_category);
        expenseMap=new HashMap<>();
        mAuth=FirebaseAuth.getInstance();
        String category= (String) sp.getSelectedItem().toString();
        database = FirebaseDatabase.getInstance();

        dbref=database.getReference("Users");


        findViewById(R.id.addexp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edexpname.getText().toString().length()!=0 && edexpamount.getText().toString().length()!=0)
                {
                    addExpense(edexpname.getText().toString(),edexpamount.getText().toString(),sp.getSelectedItem().toString());
                }
                else
                {
                    Toast.makeText(AddExpenseActivity.this,"Please Enter values",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
         currentUser = mAuth.getCurrentUser();

    }

    public void addExpense(String expname, String expamount, String cat)
    {
        Expense exp=new Expense();
        exp.setExpname(expname);
        exp.setAmount(expamount);
        exp.setCategory(cat);

        String key=dbref.child("Users").push().getKey();
        expenseMap.put(currentUser.getUid()+"/"+key,exp);
        dbref.updateChildren(expenseMap);
        Intent in=new Intent(AddExpenseActivity.this,ExpenseActivity.class);
        startActivity(in);
    }
}
