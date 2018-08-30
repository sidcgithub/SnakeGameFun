package com.bestsnakegame.siddharthchordia.snakegame;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
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
    List<Integer> snakeRotate = new ArrayList<>();
    public int eggX;
    public int eggY;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static int lastDirection = 2;



    public GameUI(Context context) {
        super(context);
        appContext = context;
        setWillNotDraw(false);
        setBackgroundColor(Color.BLACK);
    }

    int lastHorizontalSwipe = 1, lastVerticalSwipe = 0; //Initilaize swipe direction so that snake moves Right

    public int detectSwipe(int swipeDirection)
    {

        switch(swipeDirection)
        {
            case LEFT: if(lastDirection==RIGHT)
            {
                break;
            }
                lastHorizontalSwipe = -1;
                        lastVerticalSwipe = 0;
                        lastDirection = LEFT;
                        break;
            case RIGHT: if(lastDirection==LEFT)
            {
                break;
            }

                lastHorizontalSwipe = 1;
                lastVerticalSwipe = 0;
                lastDirection= RIGHT;
                break;

            case UP:  if(lastDirection==DOWN)
            {
                break;
            }

                lastHorizontalSwipe = 0;
                lastVerticalSwipe = -1;
                lastDirection = UP;
                break;
            case DOWN:
                if(lastDirection==UP)
                {
                    break;
                }
                lastHorizontalSwipe = 0;
                lastVerticalSwipe = 1;
                lastDirection  = DOWN;
                break;
            default: Log.e("Error","Incorrect input");


        }
        return lastHorizontalSwipe;
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

        Paint boundaryColor = new Paint();
        boundaryColor.setColor(Color.RED);
        boundaryColor.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100,100,width-100,height-100,boundaryColor);

        Bitmap btHeadR = BitmapFactory.decodeResource(getResources(),R.drawable.closedmouth);
        Bitmap btBodyH =  BitmapFactory.decodeResource(getResources(),R.drawable.body);
        Bitmap btTailR =  BitmapFactory.decodeResource(getResources(),R.drawable.tail);

        Bitmap btHeadL = BitmapFactory.decodeResource(getResources(),R.drawable.closedmouthleft);
        Bitmap btBodyV =  BitmapFactory.decodeResource(getResources(),R.drawable.bodyvertical);
        Bitmap btTailL =  BitmapFactory.decodeResource(getResources(),R.drawable.tailleft);

        Bitmap btHeadU = BitmapFactory.decodeResource(getResources(),R.drawable.closedmouthup);
        Bitmap btTailU =  BitmapFactory.decodeResource(getResources(),R.drawable.tailup);

        Bitmap btHeadD = BitmapFactory.decodeResource(getResources(),R.drawable.closedmouthdown);
        Bitmap btTailD =  BitmapFactory.decodeResource(getResources(),R.drawable.taildown);

        Bitmap headRight = Bitmap.createScaledBitmap(btHeadR,20,20,false);
        Bitmap bodyHorizontal = Bitmap.createScaledBitmap(btBodyH,20,20,false);
        Bitmap tailRight = Bitmap.createScaledBitmap(btTailR,20,20,false);

        Bitmap headLeft = Bitmap.createScaledBitmap(btHeadL,20,20,false);
        Bitmap bodyVertical = Bitmap.createScaledBitmap(btBodyV,20,20,false);
        Bitmap tailLeft = Bitmap.createScaledBitmap(btTailL,20,20,false);

        Bitmap headUp = Bitmap.createScaledBitmap(btHeadU,20,20,false);
        Bitmap tailUp = Bitmap.createScaledBitmap(btTailU,20,20,false);

        Bitmap headDown = Bitmap.createScaledBitmap(btHeadD,20,20,false);
        Bitmap tailDown = Bitmap.createScaledBitmap(btTailD,20,20,false);

        Drawable background = getResources().getDrawable(R.drawable.snakebackground);

         this.setBackground(background);
        if(snakePx!=null) {

            for (int i = 0; i < snakePx.size(); i++) {
                Paint squareColor = new Paint();
                squareColor.setColor(Color.YELLOW);
                //This is incorrect but easier to implement for the egg
                canvas.drawRect(eggX,eggY,eggX+20,eggY+20,squareColor);
                if(i==0) {
                    switch(snakeRotate.get(i))
                    {
                        case UP:
                            canvas.drawBitmap(headUp, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case DOWN:
                            canvas.drawBitmap(headDown, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case LEFT:
                            canvas.drawBitmap(headLeft, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case RIGHT:
                            canvas.drawBitmap(headRight, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;

                    }
                }
                else if(i == snakePx.size()-1)
                {
                    switch(snakeRotate.get(i))
                    {
                        case UP:
                            canvas.drawBitmap(tailUp, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case DOWN:
                            canvas.drawBitmap(tailDown, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case LEFT:
                            canvas.drawBitmap(tailLeft, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case RIGHT:
                            canvas.drawBitmap(tailRight, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;

                    }
                }
                else
                {
                    switch(snakeRotate.get(i))
                    {
                        case UP:
                            canvas.drawBitmap(bodyVertical, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case DOWN:
                            canvas.drawBitmap(bodyVertical, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case LEFT:
                            canvas.drawBitmap(bodyHorizontal, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;
                        case RIGHT:
                            canvas.drawBitmap(bodyHorizontal, (float) snakePx.get(i), (float) snakePy.get(i), null);

                            break;

                    }
                }
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
                        detectSwipe(LEFT);
                        Log.d("Horizontal Swipe", "onTouchEvent: SUccessful Horizontal Swipe Left ");
                        Toast.makeText(appContext, "Horizontal Left Swipe", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getActivity(), "Left swipe", Toast.LENGTH_SHORT).show();
                    }
                    if (x2 > x1 && (x1 - x2) * (x1 - x2) > 100 * 100) {
                        detectSwipe(RIGHT);
                        Log.d("Horizontal Swipe", "onTouchEvent: SUccessful Horizontal Swipe RIght ");
                        Toast.makeText(appContext, "Horizontal Right Swipe", Toast.LENGTH_SHORT).show();


                        //Toast.makeText(getActivity(), "Right swipe", Toast.LENGTH_SHORT).show();
                    }
                    if (y2 > y1 && (y1 - y2) * (y1 - y2) > 100 * 100) {
                        Log.d("Vertical Swipe", "onTouchEvent: SUccessful Vertical Swipe Down ");

                        detectSwipe(DOWN);

                        Toast.makeText(appContext, "Vertical Down Swipe", Toast.LENGTH_SHORT).show();

                        //Down swipe
                    }
                    if (y2 < y1 && (y1 - y2) * (y1 - y2) > 100 * 100) {
                        Log.d("Vertical Swipe", "onTouchEvent: SUccessful Vertical Swipe Up ");

                        detectSwipe(UP);

                        Toast.makeText(appContext, "Vertical Up Swipe", Toast.LENGTH_SHORT).show();

                        //Up swipe
                    }
                }

                return true;
        }

        return false;
    }


}
