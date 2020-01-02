package com.example.tictactoe;

public class Board {
    char[][] pieces;
    char currentPlayer;
    boolean gameEnd;
    boolean tie;

    public Board(){
        pieces = new char[3][3];
        currentPlayer = 'x';
        gameEnd = false;
        tie = false;
    }

    boolean getGameEnd(){
        return gameEnd;
    }

    boolean getTie(){
        return tie;
    }

    char[][] getPieces(){
        return pieces;
    }

    char getPiece(int x, int y) {
        return pieces[x][y];
    }

    char getCurrentPlayer(){
        return currentPlayer;
    }

    boolean markPiece(int x, int y){
        if(!gameEnd) {
            if (pieces[x][y] == 0) {
                pieces[x][y] = currentPlayer;
                gameEnd = checkGameEnd();
                if (gameEnd) {
                    if(tie){
                        currentPlayer = 't';
                    }
                    return true;

                } else {
                    changePlayer();
                }
            }
        }
        return false;
    }

    void changePlayer(){
        if(currentPlayer == 'x'){
            currentPlayer = 'o';
        }else{
            currentPlayer = 'x';
        }
    }

    boolean checkGameEnd(){
        boolean returnValue = false;
        //Checking for row equivalency
        if(full()){
            returnValue = true;
            tie = true;
        }
        for(int i=0; i<3; i++){
            if(pieces[i][0]!=0) {
                if (pieces[i][0] == pieces[i][1] && pieces[i][1] == pieces[i][2]) {
                    returnValue = true;
                    tie = false;
                }
            }
        }
        //checking for columnn equivalency
        for(int i=0; i<3; i++){
            if(pieces[0][i]!=0) {
                if (pieces[0][i] == pieces[1][i] && pieces[1][i] == pieces[2][i]) {
                    returnValue = true;
                    tie = false;
                }
            }
        }

        //checking for cross equivalency
        if(pieces[1][1] != 0){
            if (pieces[0][0] == pieces[1][1] && pieces[1][1] == pieces[2][2]) {
                returnValue = true;
                tie = false;
            }
            if (pieces[0][2] == pieces[1][1] && pieces[1][1] == pieces[2][0]) {
                returnValue = true;
                tie = false;
            }
        }

        return returnValue;
    }

    void newGame(){
        pieces = new char[3][3];
        currentPlayer = 'x';
        gameEnd = false;
    }

    boolean full(){
        for(int i = 0; i<3;i++){
            for(int j=0; j<3;j++){
                if(pieces[i][j]==0){
                    return false;
                }
            }
        }
        return true;
    }
}
