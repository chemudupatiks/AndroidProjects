package com.example.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //private ArrayList<ExpenseItem> mExpenses;
    private onItemClickListener mListener;
    private Context mContext;
    private Cursor mCursor;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView category;
        public TextView date;
        public TextView amount;
        public TextView note;

        public ViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            name =  itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            note = itemView.findViewById(R.id.note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyAdapter(Context context, Cursor cursor){
        mContext = context;
        mCursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this assumes it's not called with a null mCursor, since i means there is a data.
        if(mCursor != null){
            mCursor.moveToPosition(position);
        }else {
            Log.d("MyAdapter", "mCursor is null");
            return;
        }
        holder.name.setText(
                mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_NAME))
        );
        holder.category.setText(
                mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_CATEGORY))
        );
        holder.date.setText(
                mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_DATE))
        );
        holder.amount.setText(
                String.valueOf(mCursor.getFloat(mCursor.getColumnIndex(DatabaseHelper.KEY_AMOUNT)))
        );
        holder.note.setText(
                mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_NOTE))
        );
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }else{
            return mCursor.getCount();
        }
    }

    //change the cursor as needed and have the system redraw the data.
    public void setCursor(Cursor c) {
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = c;
        if(c != null){
            notifyDataSetChanged();
        }
    }
}
