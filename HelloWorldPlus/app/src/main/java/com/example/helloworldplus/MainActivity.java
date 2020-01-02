package com.example.helloworldplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        if(savedInstanceState==null){
            fm.beginTransaction().add(R.id.container, new MainFragment()).commit();
        }
    }

    @Override
    public void onFragmentInteraction(String text) {
        FragmentTransaction transaction = fm.beginTransaction();
        Log.d(TAG, text);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
