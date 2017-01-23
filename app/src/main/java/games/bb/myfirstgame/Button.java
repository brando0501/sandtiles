package games.bb.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

/**
 * Created by brand on 1/21/2017.
 */

public class Button {

    private int x,y,width,height;
    double scale;
    private Bitmap image;



    public Button(Bitmap i, int xpos, int ypos, double s){
        x=xpos;
        y=ypos;
        scale = s;
        width = (int)(i.getWidth()*s);
        height = (int)(i.getHeight()*s);

        image = Bitmap.createScaledBitmap(
                i, width, height, true);


    }


    public void draw(Canvas C){
        Paint paint = new Paint();
        paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        C.drawBitmap(image,x,y,paint);
    }

    public boolean tapped(int xp, int yp){
        return (x<xp&&xp<x+image.getWidth()&&y<yp&&yp<y+image.getHeight());
    }

    public void setX(int xpos){x=xpos;}
    public void setY(int ypos){y=ypos;}

    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return width;}
    public int getHeight(){return height;}






}
