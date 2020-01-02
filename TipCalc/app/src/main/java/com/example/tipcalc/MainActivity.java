package com.example.tipcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        final TextView tv1 = findViewById(R.id.textView2);
        final TextView tv2 = findViewById(R.id.textView4);
        final EditText amount = findViewById(R.id.editText);
        final EditText tip_percent = findViewById(R.id.editText2);
        final RadioButton no_round = findViewById(R.id.radioButton);
        final RadioButton total_round = findViewById(R.id.radioButton2);
        final RadioButton tip_round = findViewById(R.id.radioButton3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double bill_amount = Double.parseDouble(amount.getText().toString());
                    try{
                        Double tp = Double.parseDouble(tip_percent.getText().toString());
                        Double tip_amount = (tp/100.0)*bill_amount;
                        Double totalAmount = bill_amount+tip_amount;
                        if(no_round.isChecked()){
                            tv1.setText(String.format("%.2f",tip_amount));
                            tv2.setText(String.format("%.2f", totalAmount));
                        }else if(total_round.isChecked()){
                            tv1.setText(String.format("%.2f",tip_amount));
                            tv2.setText(Double.toString(Math.round(totalAmount)));
                        }else if(tip_round.isChecked()){
                            totalAmount = bill_amount + Math.round(tip_amount);
                            tv1.setText(Double.toString(Math.round(tip_amount)));
                            tv2.setText(String.format("%.2f",totalAmount));
                        }
                    }catch (NumberFormatException nfe){
                        Log.d(TAG, nfe.toString());
                        tip_percent.setError("Enter tip(decimal value)");
                        tip_percent.requestFocus();
                    }

                }catch (NumberFormatException nfe) {
                    Log.d(TAG, nfe.toString());
                    amount.setError("Enter bill amount(decimal value)");
                    amount.requestFocus();
                }

            }
        });
    }
}
