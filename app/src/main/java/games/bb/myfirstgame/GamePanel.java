package games.bb.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.spacing;
import static android.graphics.BitmapFactory.decodeResource;
import static games.bb.myfirstgame.MainThread.canvas;

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

    private games.bb.myfirstgame.Button menuButton;

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

    private boolean displayMenu = false, displayNewGameMenu = false;

    private PopupMenu popupMenu, newGameMenu;

    private Dropdown gameType, rowSelect, columnSelect;

    private ArrayList<Dropdown> dropdowns = new ArrayList<Dropdown>();

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

        grid = new Grid(5, 5);

        menuButton = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.menubutton),0,0,.25);//hard scale not based on screen size

        createMenus();







        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = new Point(x,y);

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //determine where the user is touching and act accordingly

            //check tiles
            if (!displayMenu&&!displayNewGameMenu) {
                grid.checkTiles(p);
            }

            //check buttons
            if (menuButton.tapped(x,y)){
                displayMenu = true;
                displayNewGameMenu = false;

            }
            //for popupMenu
            if (displayMenu){
                games.bb.myfirstgame.Button[] tempButtons = popupMenu.getButtons();
                for (int i = 0; i<tempButtons.length;i++){
                    if (tempButtons[i].tapped(x,y)){
                        switch (i) {
                            case 0://close
                                displayMenu = false;
                                break;
                            case 1://reset
                                grid = new Grid(grid.getColumns(),grid.getRows());
                                break;
                            case 2://new Game
                                displayMenu = false;
                                displayNewGameMenu = true;

                                break;
                            case 3:

                                break;
                            case 4:

                                break;
                        }
                    }
                }


            }
            //for newGameMenu
            if (displayNewGameMenu) {
                games.bb.myfirstgame.Button[] bButtons = newGameMenu.getButtons();
                games.bb.myfirstgame.Button[] dropButtons = new games.bb.myfirstgame.Button[newGameMenu.getDropdowns().length];

                for (int i = 0; i < newGameMenu.getDropdowns().length;i++){
                    dropButtons[i]=(newGameMenu.getDropdowns()[i].getMainButton());
                }

                games.bb.myfirstgame.Button[] tempButtons = new games.bb.myfirstgame.Button[dropButtons.length + bButtons.length];
                System.arraycopy(dropButtons, 0, tempButtons, 0, dropButtons.length);
                System.arraycopy(bButtons, 0, tempButtons, dropButtons.length, bButtons.length);

                for (int i = 0; i < tempButtons.length; i++) {
                    if (tempButtons[i].tapped(x, y)) {
                        switch (i) {
                            case 0://gameType
                                dropdowns.get(0).setDrawDrop(!dropdowns.get(0).isDrawDrop());
                                dropdowns.get(1).setDrawDrop(false);
                                dropdowns.get(2).setDrawDrop(false);
                                System.out.println("GAME TYPE PRESSED");
                                break;
                            case 1://x
                                dropdowns.get(0).setDrawDrop(false);
                                dropdowns.get(1).setDrawDrop(!dropdowns.get(1).isDrawDrop());
                                dropdowns.get(2).setDrawDrop(false);
                                break;
                            case 2://y
                                dropdowns.get(0).setDrawDrop(false);
                                dropdowns.get(1).setDrawDrop(false);
                                dropdowns.get(2).setDrawDrop(!dropdowns.get(2).isDrawDrop());
                                displayNewGameMenu = true;

                                break;
                            case 3://start
                                dropdowns.get(0).setDrawDrop(false);
                                dropdowns.get(1).setDrawDrop(false);
                                dropdowns.get(2).setDrawDrop(false);
                                displayNewGameMenu = false;
                                grid = new Grid(dropdowns.get(1).getCurrentSelection(), dropdowns.get(2).getCurrentSelection());

                                break;
                            case 4:

                                break;
                        }
                    }
                }
            }

            for (int i = 0; i<dropdowns.size(); i++) {
                if (dropdowns.get(i).isDrawDrop()) {
                    for (int j = 0; j<2; j++) {

                        if (dropdowns.get(i).getButtons()[j].tapped(x, y)) {
                            switch (j) {
                                case 0://gameType
                                    dropdowns.get(i).changeCurrentSelection(-1);
                                    break;
                                case 1://reset
                                   dropdowns.get(i).changeCurrentSelection(1);
                                    break;

                            }
                        }
                    }
                }
            }


            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            //player.setUp(false);
            tapTimer = System.nanoTime();
            oneTap = true;
            return true;
        }

        return super.onTouchEvent(event);
    }

    public void update()
    {




        if (grid.getWinner()>=0){
            System.out.println("A WINNER HAS BEEN FOUND!!!");
            displayMenu = !displayNewGameMenu;
        }

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
            menuButton.draw(canvas);
            //bg.draw(canvas);
            //grid.draw(canvas);

            if (displayMenu){popupMenu.draw(canvas);}
            if (displayNewGameMenu){newGameMenu.draw(canvas);}


            //bg.draw(canvas);
            if(!dissapear) {
                //player.draw(canvas);


               // basket.draw(canvas);
            }

            //for (int i = 0; i < dropdowns.size(); i++){
            //    if(dropdowns.get(i).isShouldDraw()){
            //        dropdowns.get(i).draw(canvas);
            //    }
            //}





            //draw explosion
            if(started)
            {
                //explosion.draw(canvas);
            }
            //drawText(canvas);
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

    public void checkWinner(){
        grid.winStatus();
    }

    public void createMenus(){

        double standardButtonScale = .25;

        //in game popup
        int popupX = (15*(Swidth/100)), popupY = (15*(Sheight/100)), popupW = (70*(Swidth/100)), popupH = (70*(Sheight/100));//base the button locations off these

        games.bb.myfirstgame.Button[] popupButtons = new games.bb.myfirstgame.Button[3];
        popupButtons[0] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangeclose),
                popupX+95*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangeclose).getWidth()),popupY+85*popupH/100,standardButtonScale);//close
        popupButtons[1] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangereset),popupX+5*popupW/100,popupY+85*popupH/100,standardButtonScale);//reset
        popupButtons[2] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangenewgame),
                popupX+50*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangenewgame).getWidth()/2),popupY+5*popupH/100,standardButtonScale);//new game

        popupMenu = new PopupMenu(popupX,popupY,popupW,popupH,popupButtons);

        //new game menu
        popupX = (15*(Swidth/100)); popupY = (15*(Sheight/100)); popupW = (70*(Swidth/100)); popupH = (70*(Sheight/100));//base the button locations off these

        popupButtons = new games.bb.myfirstgame.Button[1];
        Dropdown[] tempDrops = new Dropdown[3];

        games.bb.myfirstgame.Button[] upDown = new games.bb.myfirstgame.Button[2];
        upDown[0] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangeup),0,0,.25);
        upDown[1] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangedown),0,0,.25);

        games.bb.myfirstgame.Button mainDrop = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangegametype),
                popupX+50*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangegametype).getWidth()/2),popupY+5*popupH/100,standardButtonScale);//new game

        tempDrops[0] = new Dropdown(new String[] {"Choose one","Standard", "Sandbox"},upDown,mainDrop);

        mainDrop = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangex),
                popupX+10*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangex).getWidth()/2),popupY+20*popupH/100,standardButtonScale);//x

        tempDrops[1] = new Dropdown(new String[] {"13","12","11","10","9","8","7","6","5","4","3","2"},upDown,mainDrop,11);

        mainDrop = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangey),
                popupX+90*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangey).getWidth()/2),popupY+20*popupH/100,standardButtonScale);//y

        tempDrops[2] = new Dropdown(new String[] {"13","12","11","10","9","8","7","6","5","4","3","2"},upDown,mainDrop,11);


        popupButtons[0] = new games.bb.myfirstgame.Button(BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangestart),
                popupX+95*popupW/100-(int)(standardButtonScale*BitmapFactory.decodeResource(getResources(), R.drawable.buttonorangestart).getWidth()),popupY+85*popupH/100,standardButtonScale);//close


        dropdowns.add(tempDrops[0]);//change this to remove dropdowns arraylist
        dropdowns.add(tempDrops[1]);
        dropdowns.add(tempDrops[2]);



        newGameMenu = new PopupMenu(popupX,popupY,popupW,popupH,popupButtons,tempDrops);

    }

    public void createDropdown(){

    }







}