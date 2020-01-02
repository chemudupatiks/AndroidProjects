package com.example.expensetracker;

import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MultiInputDialogBox.OnDialogFragmentInteractionListener {

    private EditText et_name;
    private EditText et_category;
    private EditText et_date;
    private EditText et_amount;
    private EditText et_note;

    private RecyclerView rv;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager lm;
    private int posInArray = -1;
    private ExpenseDatabase expenseDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseDb = new ExpenseDatabase(this);
        expenseDb.open();

        rv = findViewById(R.id.rv);
        lm = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(this, getAllItems());
        rv.setLayoutManager(lm);
        rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ExpenseItem ei = new ExpenseItem(expenseDb.getRowArrayAtPosition(position));
                MultiInputDialogBox dialog = new MultiInputDialogBox(ei);
                posInArray = position;
                dialog.show(getSupportFragmentManager(), "Expense Item");
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                expenseDb.cpDelete(DatabaseHelper.TABLE_NAME,
                        DatabaseHelper.KEY_ROWID+"="+expenseDb.getIdAtPos(viewHolder.getAdapterPosition()),
                        null);
                mAdapter.setCursor(getAllItems());
            }
        }).attachToRecyclerView(rv);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiInputDialogBox dialog = new MultiInputDialogBox();
                dialog.show(getSupportFragmentManager(), "Expense Item");
                posInArray = -1;
                Log.d("main", "fab");
            }
        });
    }

    private Cursor getAllItems(){
        return expenseDb.getAllRows();
    }

    @Override
    public void onMultiInputInteraction(String[] items) {
        ExpenseItem expenseItem;
        if(items[3].isEmpty()){
            if (posInArray>=0){
                expenseDb.updateRow(expenseDb.getIdAtPos(posInArray),items[0], items[1], items[2], 0.0f, items[4]);
                mAdapter.setCursor(getAllItems());
                //mAdapter.notifyDataSetChanged();
            }else{
                expenseDb.insertRow(items[0], items[1], items[2], 0.0f, items[4]);
                mAdapter.setCursor(getAllItems());
                //Log.d("main", "after notified");
            }
        }else{
            if (posInArray>=0){
                expenseDb.updateRow(expenseDb.getIdAtPos(posInArray),items[0], items[1], items[2], Float.parseFloat(items[3]), items[4]);
                mAdapter.setCursor(getAllItems());
                //mAdapter.notifyItemChanged(posInArray);
                //mAdapter.notifyDataSetChanged();
            }else{
                expenseDb.insertRow(items[0], items[1], items[2], Float.parseFloat(items[3]), items[4]);
                mAdapter.setCursor(getAllItems());
                //mAdapter.notifyItemInserted(mExpenseList.size()-1);
                //Log.d("main", "after notified");
            }
        }


        //Log.d("main", "changed based on MultiInputInteraction");
    }
}
