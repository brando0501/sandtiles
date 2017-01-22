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

        image = Bitmap.createScaledBitmap(
                i, (int)(i.getWidth()*s), (int)(i.getHeight()*s), true);

        width=i.getWidth();
        height=i.getHeight();


    }


    public void draw(Canvas C){
        Paint paint = new Paint();
        paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        C.drawBitmap(image,x,y,paint);
    }

    public boolean tapped(int xp, int yp){
        return (x<xp&&xp<x+image.getWidth()&&y<yp&&yp<y+image.getHeight());
    }






}
