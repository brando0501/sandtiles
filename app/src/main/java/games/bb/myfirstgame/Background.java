package games.bb.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by brand on 1/9/2016.
 */
public class Background {

    private Bitmap image;
    private int x, y, dx;


    public Background(Bitmap res)
    {
        image = res;
        dx = GamePanel.MOVESPEED;

    }

    public void update()
    {
        //x+=dx;
        if (x<-GamePanel.WIDTH)
        {
            x=0;
        }
    }

    public void draw(Canvas canvas)
    {
        System.out.println("fuck");
        Rect scaled = new Rect(x, y, x + GamePanel.WIDTH , y + GamePanel.HEIGHT);
        canvas.drawBitmap(image, null, scaled, null );
        if (x<0)
        {
            canvas.drawBitmap(image, x + GamePanel.WIDTH, y, null);
        }
    }


}
