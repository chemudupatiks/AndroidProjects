package com.example.simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String start = "";
    private String end = "";
    private String operation = "";
    private boolean clearText = false;
    private boolean operationClicked = false;

    private String getResult(){
        String result ="";
        if (operation.equals("+")) {
            result = Double.toString(Double.parseDouble(start) + Double.parseDouble(end));
        } else if (operation.equals("-")) {
            result = Double.toString(Double.parseDouble(start) - Double.parseDouble(end));
        } else if (operation.equals("*")) {
            result = Double.toString(Double.parseDouble(start) * Double.parseDouble(end));
        } else if (operation.equals("/")) {
            result = Double.toString(Double.parseDouble(start) / Double.parseDouble(end));
        } else if (operation.equals("^")) {
            result = Double.toString(Math.pow(Double.parseDouble(start), Double.parseDouble(end)));
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = findViewById(R.id.text);
        Button one = findViewById(R.id.one);
        Button two = findViewById(R.id.two);
        Button three = findViewById(R.id.three);
        Button four = findViewById(R.id.four);
        Button five = findViewById(R.id.five);
        Button six = findViewById(R.id.six);
        Button seven = findViewById(R.id.seven);
        Button eight = findViewById(R.id.eight);
        Button nine = findViewById(R.id.nine);
        Button zero = findViewById(R.id.zero);
        Button negate = findViewById(R.id.negate);
        Button equal = findViewById(R.id.equal);
        Button decimal = findViewById(R.id.decimal);
        Button add = findViewById(R.id.add);
        Button subtract = findViewById(R.id.subtract);
        Button product = findViewById(R.id.product);
        Button divide = findViewById(R.id.divide);
        Button power = findViewById(R.id.power);
        Button clear = findViewById(R.id.clear);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                text.setText(text.getText().toString()+"1");
                operationClicked = false;
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                text.setText(text.getText().toString()+"2");
                operationClicked = false;
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                text.setText(text.getText().toString()+"3");
                operationClicked = false;
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                text.setText(text.getText().toString()+"4");
                operationClicked = false;
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"9");
            }
        });
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clearText){
                    text.setText("");
                    clearText = false;
                }
                else if(text.getText().toString().equals("0")){
                    text.setText("");
                }
                operationClicked = false;
                text.setText(text.getText().toString()+"0");
            }
        });
        negate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!text.getText().toString().equals("0")) {
                    if (text.getText().toString().charAt(0) == '-') {
                        text.setText(text.getText().toString().substring(1));
                    } else {
                        text.setText("-" + text.getText().toString());
                    }
                    operationClicked = false;
                }
            }
        });
        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!start.isEmpty()){
                    end = text.getText().toString();
                    text.setText(getResult());
                }
                start = "";
                end="";
                operation = "";
                operationClicked = false;
            }
        });

        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    text.setText(text.getText().toString() + ".");
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    if (!start.isEmpty()) {
                        end = text.getText().toString();
                        text.setText(getResult());
                        end = "";
                    }
                    start = text.getText().toString();
                    operationClicked = true;
                    operation = "+";
                    clearText = true;
                }else{
                    operation = "+";
                }
            }
        });
        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    if (!start.isEmpty()) {
                        end = text.getText().toString();
                        text.setText(getResult());
                        end = "";
                    }
                    start = text.getText().toString();
                    operationClicked = true;
                    operation = "-";
                    clearText = true;
                }else{
                    operation = "-";
                }
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    if (!start.isEmpty()) {
                        end = text.getText().toString();
                        text.setText(getResult());
                        end = "";
                    }
                    start = text.getText().toString();
                    operationClicked = true;
                    operation = "*";
                    clearText = true;
                }else{
                    operation = "*";
                }
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    if (!start.isEmpty()) {
                        end = text.getText().toString();
                        text.setText(getResult());
                        end = "";
                    }
                    start = text.getText().toString();
                    operationClicked = true;
                    operation = "/";
                    clearText = true;
                }else{
                    operation = "/";
                }
            }
        });
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!operationClicked) {
                    if (!start.isEmpty()) {
                        end = text.getText().toString();
                        text.setText(getResult());
                        end = "";
                    }
                    start = text.getText().toString();
                    operationClicked = true;
                    operation = "^";
                    clearText = true;
                }else{
                    operation = "^";
                }
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operation = "";
                text.setText("0");
                start="";
                end="";
                clearText = false;
                operationClicked = false;
            }
        });

    }
}
