package com.example.tictactoe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MyBoardView extends View {

    Paint grid, xColor, oColor;
    public int incr, size=3;
    int mheight =0, mwidth =0;
    int leftside, rightside, boardwidth;
    private Board board;
    public Context myContext;
    private OnBoardTouched mCallback;

    public interface OnBoardTouched{
        void onTouch(char player, boolean gameEnd);
    }

    void setmCallback(OnBoardTouched obt){
        mCallback = obt;
    }

    public MyBoardView(Context context) {
        super(context);
        myContext = context;
        setup();
    }

    public MyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        myContext = context;
        setup();
    }

    public MyBoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        myContext = context;
        setup();
    }


    public Board getBoard(){
        return board;
    }

    public void setup() {

        grid = new Paint();
        grid.setColor(Color.BLACK); //defaults to black, but just making sure.
        grid.setStyle(Paint.Style.STROKE);
        grid.setStrokeWidth(15);

        xColor = new Paint(grid);  //copies all the attributes, ill change the color next
        xColor.setColor(Color.RED);

        oColor = new Paint(grid);  //copies all the attributes, ill change the color next
        oColor.setColor(Color.BLUE);

        board = new Board();

        if (mheight >0) setsizes();  //in case not on screen.
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mCallback.onTouch(board.getCurrentPlayer(), board.getGameEnd());
    }

    /*
     * Sets up the default sizes for whatever the size of the screen is
     * so that the grid takes up most of the space with a margin around it.
     *
     */
    public void setsizes() {
        incr = mwidth /(size +2);  //give a margin.
        leftside = incr -1;
        rightside = incr*(size+1);
        boardwidth = incr * size;
        Log.i("setsizes", "incr is "+incr);
    }

    /*
     * clears the grid and then has the view redraw.
     */
    void clearmaze() {
        board.newGame();
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = incr;
        int y = incr;

        canvas.drawColor(Color.WHITE);
        //top line  not needed anymore.
        //canvas.drawLine(x, y -1, rightside, y-1, black);
        //draw squares across, then down
        for (int yi = 0; yi<size; yi++) {
            for (int xi =0; xi<size; xi++) {

                canvas.drawRect(x,y,x+incr, y+incr, grid);  //draw black box.
                if (board.getPiece(xi,yi) == 'x') {
                    Log.i("onDraw", "Drawing x");
                    canvas.drawLine(x, y, x+incr, y+incr, xColor);
                    canvas.drawLine(x+incr, y, x, y+incr, xColor);
                }
                if (board.getPiece(xi,yi) == 'o') {
                    Log.i("onDraw", "Drawing o");
                    canvas.drawCircle(x+(incr/2), y+(incr/2), (0.48f*incr), oColor);
                }

                x+=incr; //move to next square across
            }
            x = incr;
            y += incr;
        }
        //bottom line  not needed anymore.
        //canvas.drawLine(leftside, rightside, rightside, rightside,black);
    }

    /*
     * used by the ontouch event to figure out which box (if any) was "touched"
     *
     */
    boolean where(int x, int y) {
        int cx, cy;
        boolean end = false;
        if (y >= leftside && y < rightside &&
                x >= leftside && x < rightside) {
            y -= incr;
            x -= incr; //simplifies the math here.
            cx = x / incr;
            cy = y / incr;
            if (cx < size && cy < size) {
                if (board.getPiece(cx,cy) == 0) {
                    end = board.markPiece(cx,cy);
                }
            } else {
                Log.i("where", "Error in Where, cx=" + cx + " cy=" + cy);
            }
        }
        return end;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        // Retrieve the new x and y touch positions
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.i("onTouchEvent", "Action_down");
                if(where(x,y)){
                    mCallback.onTouch(board.getCurrentPlayer(), board.getGameEnd());
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("MSW", ""+getMeasuredWidth());
        Log.i("MSH", ""+getMeasuredHeight());
        mwidth = getMeasuredWidth();
        mheight = getMeasuredHeight();
        if (mheight >0 && mwidth > mheight ) {
            mwidth = mheight;
        } else if (mheight ==0) {
            mheight = mwidth;
        }
        setsizes();

        //setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
        setMeasuredDimension(mwidth, mheight);
    }
}
