package games.bb.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import android.media.MediaPlayer;


public class Player extends GameObject{



    private Bitmap spritesheet;
    private int score;

    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    Rect scaled = new Rect(0, 0, GamePanel.WIDTH/20, GamePanel.WIDTH/20);

    private int direction = 1;


    public Player(Bitmap res, int w, int h, int numFrames) {

        x = 100;
        y = GamePanel.HEIGHT / 2;
        dy = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b){dy = -10;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        animation.update();


            dy +=1;


        if(dy>14)dy = 14;
        if(dy<-14)dy = -14;

        y += dy*2;


//increments difficulty
        x += (5 + score) * direction;
        System.out.println("direction= " + direction);

    }

    public void draw(Canvas canvas)
    {
        scaled.set(x, y, x + GamePanel.WIDTH/20, y + GamePanel.WIDTH/20);
        canvas.drawBitmap(animation.getImage(), null, scaled, null);
        //canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){

        playing = b;

    }
    public void resetDY(){
        dy = 0;
        direction = 1;
    }

    public void resetScore(){score = 0;}

    public int getDy(){return dy;}

    public int getXmid(){return (x + GamePanel.WIDTH/40);}

    public void incramentScore()
    {
        score++;
    }

    public void hitBackboard()
    {
        direction = -1;
    }

    public void flipDirection()
    {
        direction = direction * -1;
    }

    public int getWidth()
    {
        return GamePanel.WIDTH/20;
    }

    public int getHeight()
    {
        return GamePanel.WIDTH/20;
    }

    public Rect getRectangle()
    {

        //System.out.println(x + "   " + y);
        return new Rect(x, y, x+GamePanel.WIDTH/20, y+GamePanel.WIDTH/20);
    }

    public int getRadius()
    {
        return GamePanel.WIDTH/40;
    }

   
}