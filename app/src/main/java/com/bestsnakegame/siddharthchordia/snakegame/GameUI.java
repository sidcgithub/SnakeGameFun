package com.bestsnakegame.siddharthchordia.snakegame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameUI extends View {


    Context appContext;

    List<Integer> snakePx = new ArrayList<Integer>();
    List<Integer> snakePy = new ArrayList<Integer>();
    public int eggX;
    public int eggY;


    public GameUI(Context context) {
        super(context);
        appContext = context;
        setWillNotDraw(false);
        setBackgroundColor(Color.BLACK);
    }

    int lastHorizontalSwipe = 1, lastVerticalSwipe = 0;

    public int detectHorizontalSwipe(boolean swipeDirection)
    {
        //Enter the logic for a horizontal swipe
        if(swipeDirection==true)
        {
            lastHorizontalSwipe = 1;
        }
        else
        {
            lastHorizontalSwipe = -1;
        }
        return lastHorizontalSwipe;
    }

    public int detectVerticalSwipe(boolean swipeDirection)
    {
        if(swipeDirection==true)
        {
            lastVerticalSwipe = -1;
        }
        else
        {
            lastVerticalSwipe = 1;
        }
        return 1; //Enter the logic for a vertical swipe
    }

    public void printSnake()
    {

    }

    public void printEgg()
    {

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        WindowManager wm = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float width = size.x;
        float height = size.y;

        if(snakePx!=null) {

            for (int i = 0; i < snakePx.size(); i++) {
                Paint squareColor = new Paint();
                squareColor.setColor(Color.BLUE);
                //This is incorrect but easier to implement for the egg
                canvas.drawRect(eggX,eggY,eggX+10,eggY+10,squareColor);
                canvas.drawRect(snakePx.get(i), snakePy.get(i), snakePx.get(i) + 10, snakePy.get(i) + 10, squareColor);
                Log.d("This is working", "onDraw: X coord "+ snakePx.get(i)+" Y coord "+ snakePy.get(i));
            }
        }


    }

    int CLICK_DURATION = 10;
    double x1=0, y1=0, t1=0, x2=0, y2=0, t2=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN:

                x1 = event.getX();
                y1 = event.getY();
                t1 = System.currentTimeMillis();
                return true;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                t2 = System.currentTimeMillis();
                boolean xSwipe = false;
/*
                if((Math.pow(x1-x2,2)>Math.pow(y1-y2,2)) )
                {
                    xSwipe = true;

                }*/


                if (t2 - t1 < CLICK_DURATION) {

                } else {
                    if (x1 > x2 && (x1 - x2) * (x1 - x2) > 100 * 100) {
                        lastHorizontalSwipe = -1;
                        lastVerticalSwipe = 0;
                        Log.d("Horizontal Swipe", "onTouchEvent: SUccessful Horizontal Swipe Left ");
                        Toast.makeText(appContext, "Horizontal Left Swipe", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), "Left swipe", Toast.LENGTH_SHORT).show();
                    }
                    if (x2 > x1 && (x1 - x2) * (x1 - x2) > 100 * 100) {
                        lastHorizontalSwipe = 1;
                        lastVerticalSwipe = 0;
                        Log.d("Horizontal Swipe", "onTouchEvent: SUccessful Horizontal Swipe RIght ");
                        Toast.makeText(appContext, "Horizontal Right Swipe", Toast.LENGTH_SHORT).show();


                        //Toast.makeText(getActivity(), "Right swipe", Toast.LENGTH_SHORT).show();
                    }
                    if (y2 > y1 && (y1 - y2) * (y1 - y2) > 100 * 100) {
                        Log.d("Vertical Swipe", "onTouchEvent: SUccessful Vertical Swipe Down ");

                        lastHorizontalSwipe = 0;
                        lastVerticalSwipe = 1;

                        Toast.makeText(appContext, "Vertical Down Swipe", Toast.LENGTH_SHORT).show();

                        //Down swipe
                    }
                    if (y2 < y1 && (y1 - y2) * (y1 - y2) > 100 * 100) {
                        Log.d("Vertical Swipe", "onTouchEvent: SUccessful Vertical Swipe Up ");

                        lastHorizontalSwipe = 0;
                        lastVerticalSwipe = -1;

                        Toast.makeText(appContext, "Vertical Up Swipe", Toast.LENGTH_SHORT).show();

                        //Up swipe
                    }
                }

                return true;
        }

        return false;
    }


}
