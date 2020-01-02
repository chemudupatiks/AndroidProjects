package com.example.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BoardFragment extends Fragment implements MyBoardView.OnBoardTouched {
    public MyBoardView board;
    private TextView player1;
    private TextView player2;
    private TextView win;

    public BoardFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.board_fragment, container, false);
        board =  myView.findViewById(R.id.mbv);
        board.setmCallback(this);
        return myView;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onTouch(char player, boolean gameEnd) {
        player1 = getActivity().findViewById(R.id.tv_player1);
        player2 = getActivity().findViewById(R.id.tv_player2);
        win = getActivity().findViewById(R.id.win);
        if(!gameEnd) {
            if (player == 'x') {
                player1.setTextColor(Color.GREEN);
                player2.setTextColor(Color.GRAY);
            } else if (player == 'o') {
                player1.setTextColor(Color.GRAY);
                player2.setTextColor(Color.GREEN);
            }
        }else{
            if (player == 'x') {
                win.setText("Game ended, Player 1 "+"["+player+"] won!");
            } else if (player == 'o') {
                win.setText("Game ended, Player 2 "+"["+player+"] won!");
            }else if (player == 't') {
                win.setText("Game ended, it was a tie!");
            }
        }
    }
}
