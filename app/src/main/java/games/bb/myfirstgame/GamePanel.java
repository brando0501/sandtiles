package games.bb.myfirstgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

//import static android.R.attr.width;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static final int WIDTH = 480;
    public static final int HEIGHT = 856;
    public static final int MOVESPEED = -5;

    public static int Sheight, Swidth;


    private MainThread thread;


    private Player player;
    private Background bg;
    private Basket basket;

    private Grid grid;

    private long tapTimer;

    private Random rand = new Random();
    private int maxBorderHeight;
    private int minBorderHeight;
    private boolean topDown = true;
    private boolean botDown = true;
    private boolean newGameCreated;

    private boolean oneTap = true;

    private boolean firstDraw = true;

    //public int height, width;



    //increase to slow down difficulty progression, decrease to speed up difficulty progression
    private int progressDenom = 20;


    private long startReset;
    private boolean reset;
    private boolean dissapear;
    private boolean started;
    private int best;



    public GamePanel(Context context)
    {
        super(context);




        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);



        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        //width and height need to be first
        Sheight = getResources().getDisplayMetrics().heightPixels;
        Swidth = getResources().getDisplayMetrics().widthPixels;

        grid = new Grid(16, 10);






        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //determine where the user is touching and act accordingly
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(false);
            tapTimer = System.nanoTime();
            oneTap = true;
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {

    }





    @Override
    public void draw(Canvas canvas)
    {
        System.out.println("shit");

        Paint paint = new Paint();
        paint.setStrokeWidth(WIDTH/50);
        paint.setARGB(255,244,84,32);


        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();
            //canvas.scale(scaleFactorX, scaleFactorY);
            //canvas.drawLine(0,0,WIDTH,HEIGHT,paint);



            grid.draw(canvas);
            //bg.draw(canvas);
            //grid.draw(canvas);


            //bg.draw(canvas);
            if(!dissapear) {
                //player.draw(canvas);


               // basket.draw(canvas);
            }





            //draw explosion
            if(started)
            {
                //explosion.draw(canvas);
            }
            drawText(canvas);
            canvas.restoreToCount(savedState);

        }
    }




    public void newGame()
    {
        dissapear = false;


        newGameCreated = true;


    }
    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

    }





}