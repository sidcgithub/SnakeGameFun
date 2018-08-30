package com.bestsnakegame.siddharthchordia.snakegame;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine {

    int vx = 1, vy =0;// These are the x and y velocities of the head of the snake
    List<Integer> snakePx = new ArrayList<Integer>();
    List<Integer> snakePy = new ArrayList<Integer>();
    List<Integer> snakeRotate = new ArrayList<>();

    int eggX = 200;
    int eggY = 200;

    int screenHeight;
    int screenWIdth;





    Context theMainApplicationContext;


    GameUI gameUI ;//Need to get the context from GameActivity
    public GameEngine(Context context, GameUI gameView)
    {
        this.vx = 0;
        this.vy = 1;
        for(int i = 0, pos = 200;i<80;i+=20, pos = pos-20)
        {
            snakePx.add(pos);
            snakePy.add(200);
            snakeRotate.add(1);
        }
        theMainApplicationContext = context;
        gameUI = gameView;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWIdth = size.x;
        screenHeight = size.y;
    }

    public void snakeMovementStep()
    {
        int snakeLength = snakePx.size();
        for(int i = snakeLength-1;i>=1;i--) {
            snakePx.set(i, snakePx.get(i - 1));
            snakePy.set(i, snakePy.get(i - 1));
            snakeRotate.set(i, snakeRotate.get(i-1));
        }
        vx = 20*gameUI.lastHorizontalSwipe;
        snakePx.set(0, snakePx.get(0) + vx);


        vy = 20* gameUI.lastVerticalSwipe;
        snakePy.set(0, snakePy.get(0) + vy);

        snakeRotate.set(0,GameUI.lastDirection);











    }

    public void snakeGrowth()
    {
        snakePy.add(snakePy.get(snakePy.size()-1));
        snakePx.add(snakePx.get(snakePx.size()-1));
        snakeRotate.add(snakeRotate.get(snakeRotate.size()-1));

    }

    public void snakeEatsEgg()
    {

        if(snakePx.get(0)==eggX && snakePy.get(0)==eggY)
        {
            snakeGrowth();
            generateNewEgg();
        }

    }

    public void generateNewEgg()
    {
        Random rand = new Random();
        eggX = rand.nextInt((screenWIdth-200)/20)*20+100;
        eggY = rand.nextInt((screenHeight-200)/20)*20+100;
    }

    public void snakeDies()
    {


    }

    public boolean snakeBitesItself()
    {
        boolean biteFlag =false;
        for(int i=0;i<snakePx.size();i++)
        {
            if(snakePx.get(i)==snakePx.get(0)&&snakePy.get(0)==snakePy.get(i))
            {
                biteFlag=true;
            }
        }


        return biteFlag;

    }

    public boolean snakeBitesWall()
    {
        boolean biteFlag = false;
        if(snakePx.get(0)>=screenWIdth-120||snakePx.get(0)<=100||snakePy.get(0)>=screenHeight-120||snakePy.get(0)<=100)
        {
            biteFlag=true;
        }

        return biteFlag;

    }

    public void renderSnake()
    {
        gameUI.eggX = eggX;
        gameUI.eggY = eggY;

        gameUI.snakePx = snakePx;
        gameUI.snakePy = snakePy;
        gameUI.snakeRotate = snakeRotate;

    }

    public void updateGameFrame() //This will update every frame of the game
    {

        snakeMovementStep();
        snakeEatsEgg();
        GameThread.gameOver = snakeBitesItself();

            GameThread.gameOver = snakeBitesWall();

        renderSnake();
        //Fill this up with all the game updating methods like the above one

    }

}
