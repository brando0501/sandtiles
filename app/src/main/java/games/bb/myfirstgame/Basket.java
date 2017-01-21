package games.bb.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by brand on 1/13/2016.
 */
public class Basket extends GameObject {


    private Bitmap spritesheet;
    private int score;

    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;


   // int ny;
    Random rand = new Random();
    Rect scaled = new Rect(0, 0, GamePanel.WIDTH/5, GamePanel.WIDTH/5);


    public Basket(Bitmap res, int w, int h, int numFrames) {


        dy = 0;
        score = 0;
        height = h;
        width = w;






        x = GamePanel.WIDTH - 100;
        y = newY();

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



    public void update()
    {



        y = newY();

    }

    public int newY()
    {

        int z = (int) (rand.nextDouble() * (2 * (GamePanel.HEIGHT / 3)) +(GamePanel.HEIGHT / 12));
        System.out.println(z);
        return z;

    }

    public void draw(Canvas canvas)
    {

        scaled.set(x, y, x + GamePanel.WIDTH/5, y + GamePanel.WIDTH/5);
        canvas.drawBitmap(animation.getImage(), null, scaled, null);
        //canvas.drawBitmap(animation.getImage(), x, y, null);
        //canvas.drawBitmap(animation.getImage(), null, scaled ,null);


    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetScore(){score = 0;}
    public int getDy(){return dy;}

    @Override
    public int getX(){return x;}

    @Override
    public Rect getRectangle()
    {

        //System.out.println(y + "   " +  (y +  GamePanel.WIDTH/10));
        return new Rect(x, (int) (y + GamePanel.WIDTH/11.5)  , x+width, (int)(y +  GamePanel.WIDTH/11.5) + 5);
    }

    public Rect getBackboard()
    {
        //System.out.println(x + "   " +  (int)(x+GamePanel.WIDTH/19));
        return new Rect((int)(x+GamePanel.WIDTH/15), y, (int)(x+GamePanel.WIDTH/15) + 5, y + GamePanel.WIDTH/10);
    }

}
