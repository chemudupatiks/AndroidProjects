package com.example.expensetracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MultiInputDialogBox extends AppCompatDialogFragment {

    private String name ="";  // name
    private String category="";  //category
    private String date=""; //date
    private String amount=""; //amount
    private String note="";

    private EditText et_name;
    private EditText et_category;
    private EditText et_date;
    private EditText et_amount;
    private EditText et_note;

    private OnDialogFragmentInteractionListener mListener;

    public MultiInputDialogBox(){
        //empty
    }

    public MultiInputDialogBox(ExpenseItem ei){
        name = ei.getName();
        category = ei.getCategory();
        date = ei.getDate();
        amount = ei.getAmount().toString();
        note = ei.getNote();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogFragmentInteractionListener) {
            mListener = (OnDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.multi_input_dialog_box, null);
        et_name = myView.findViewById(R.id.et1);
        et_category = myView.findViewById(R.id.et2);
        et_date = myView.findViewById(R.id.et3);
        et_amount = myView.findViewById(R.id.et4);
        et_note = myView.findViewById(R.id.et5);

        if(!name.isEmpty()){et_name.setText(name);}
        if(!category.isEmpty()){et_category.setText(category);}
        if(!date.isEmpty()){et_date.setText(date);}
        if(!amount.isEmpty()){et_amount.setText(amount);}
        if(!note.isEmpty()){et_note.setText(note);}



        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView).setTitle("Add Expense").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder.create();
    }

    public interface OnDialogFragmentInteractionListener{
        void onMultiInputInteraction(String[] items);
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog ad = (AlertDialog)getDialog();
        ad.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = true;
                if(et_name.getText().toString().trim().isEmpty()){
                    et_name.setError("Cannot be empty");
                    valid=false;
                }
                if(et_category.getText().toString().trim().isEmpty()){
                    et_category.setError("Cannot be empty");
                    valid=false;
                }
                if(et_date.getText().toString().trim().isEmpty()){
                    et_date.setError("Cannot be empty");
                    valid=false;
                }
                if(et_amount.getText().toString().trim().isEmpty()){
                    et_amount.setError("Cannot be empty");
                    valid=false;
                }
                Log.d("MultiInputDialogBox", "not in valid section");
                if(valid) {
                    Log.d("MultiInputDialogBox", "in valid section");
                    String[] returnList =
                            new String[]{
                                    et_name.getText().toString(),
                                    et_category.getText().toString(),
                                    et_date.getText().toString(),
                                    et_amount.getText().toString(),
                                    et_note.getText().toString()
                            };
                    //send the list back to the MainActivity to process.
                    mListener.onMultiInputInteraction(returnList);

                    ad.dismiss();
                }

            }
        });
    }
}
