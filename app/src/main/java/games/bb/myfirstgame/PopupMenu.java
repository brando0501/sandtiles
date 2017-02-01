package games.bb.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import static games.bb.myfirstgame.MainThread.canvas;

/**
 * Created by brand on 1/22/2017.
 */

public class PopupMenu {

    private int x,y,width,height;
    private Button[] buttons = new Button[0];
    private Dropdown[] dropdowns = new Dropdown[0];

    public PopupMenu(int xpos, int ypos, int w, int h, Button[] b){
        x=xpos;
        y=ypos;
        //width=w;
        //height=h;
        width = GamePanel.Swidth - x*2;
        height = GamePanel.Sheight - y*2;

        buttons = b;

    }

    public PopupMenu(int xpos, int ypos, int w, int h, Button[] b, Dropdown[] d){
        x=xpos;
        y=ypos;
        //width=w;
        //height=h;
        width = GamePanel.Swidth - x*2;
        height = GamePanel.Sheight - y*2;

        buttons = b;
        dropdowns = d;

    }

    public void draw(Canvas C){
        Paint paint = new Paint();
        paint.setARGB(150,150,150,150);
        paint.setStrokeWidth(GamePanel.Swidth/50);
        paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        Rect area = new Rect(x,y,x+width,y+height);
        C.drawRect(area,paint);

        //draw border
        paint.setARGB(255,100,100,100);
        C.drawLine(x,y,x,y+height,paint);//left
        C.drawLine(x+width,y,x+width,y+height,paint);//right
        C.drawLine(x,y,x+width,y,paint);//top
        C.drawLine(x,y+height,x+width,y+height,paint);//bottom

        //round corners
        C.drawCircle(x,y,GamePanel.Swidth/100,paint);//TL
        C.drawCircle(x+width,y,GamePanel.Swidth/100,paint);//TR
        C.drawCircle(x,y+height,GamePanel.Swidth/100,paint);//BL
        C.drawCircle(x+width,y+height,GamePanel.Swidth/100,paint);//BR

        //draw buttons
        for (int i = 0;i<buttons.length;i++){
            buttons[i].draw(canvas);
        }

        //draw drops
        for (int i = 0;i<dropdowns.length;i++){//main buttons
            dropdowns[i].drawMainButton(canvas);
        }
        for (int i = 0;i<dropdowns.length;i++){//active dropdowns
            dropdowns[i].draw(canvas);
        }






    }

    public Button[] getButtons(){return buttons;}
    public Dropdown[] getDropdowns(){return dropdowns;}
}
