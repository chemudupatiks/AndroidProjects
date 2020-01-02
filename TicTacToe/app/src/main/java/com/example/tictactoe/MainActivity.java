package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button reset;
    private BoardFragment boardFragment;
    private TextView win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boardFragment = new BoardFragment();
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container, boardFragment).commit();
        }
//        else{
//            boardFragment = (BoardFragment) getSupportFragmentManager().getFragment(savedInstanceState, "BoardFragment");
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, boardFragment).commit();
//        }

        reset = findViewById(R.id.reset);
        win = findViewById(R.id.win);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardFragment.board.clearmaze();
                win.setText("");
            }
        });
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, "BoardFragment", boardFragment);
//    }
}
