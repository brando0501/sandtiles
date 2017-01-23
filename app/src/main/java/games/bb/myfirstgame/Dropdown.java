package games.bb.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

import static android.R.attr.width;
import static android.R.attr.x;
import static android.R.attr.y;
import static games.bb.myfirstgame.MainThread.canvas;

/**
 * Created by brand on 1/22/2017.
 */

public class Dropdown {

    private int currentSelection=0, displayAtOnce = 3;
    private String[] options;
    private Button[] buttons;
    private Button mainButton;

    private Paint optionsPaint = new Paint();

    private boolean shouldDraw = false, drawDrop = false;



    public Dropdown(String[] o, Button[] b, Button main){

        options = o;
        buttons = b;
        mainButton = main;


        optionsPaint.setARGB(255,0,0,0);
        optionsPaint.setStrokeWidth(GamePanel.Swidth/50);
        optionsPaint.setFlags(TextPaint.ANTI_ALIAS_FLAG);
        optionsPaint.setTextSize(displayAtOnce*7*GamePanel.Swidth/300);

    }

    public void draw(Canvas C){
        int x,y,width,height;

        mainButton.draw(C);

        if (drawDrop) {

            Paint paint = new Paint();
            paint.setARGB(255, 200, 200, 200);
            paint.setStrokeWidth(GamePanel.Swidth / 50);
            paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

            x = mainButton.getX();
            y = mainButton.getY()+mainButton.getHeight();
            width = mainButton.getWidth();

            Rect bounds = new Rect();
            optionsPaint.getTextBounds("Sample", 0, 6, bounds);//cant use for width
            height = (int)(bounds.height()*3 + paint.getStrokeWidth());

            //set up button pos
            buttons[0].setX(mainButton.getX() + mainButton.getWidth()+(int)(optionsPaint.getStrokeWidth()/2));
            buttons[0].setY(mainButton.getY() + mainButton.getHeight());

            //set down button pos
            buttons[1].setX(mainButton.getX() + mainButton.getWidth()+(int)(optionsPaint.getStrokeWidth()/2));
            buttons[1].setY(mainButton.getY() + height);




            Rect area = new Rect(x, y, x + width, y + height);
            C.drawRect(area, paint);

            //draw border
            paint.setARGB(255, 50, 50, 50);
            C.drawLine(x, y, x, y + height, paint);//left
            C.drawLine(x + width, y, x + width, y + height, paint);//right
            C.drawLine(x, y, x + width, y, paint);//top
            C.drawLine(x, y + height, x + width, y + height, paint);//bottom

            //round corners
            C.drawCircle(x, y, GamePanel.Swidth / 100, paint);//TL
            C.drawCircle(x + width, y, GamePanel.Swidth / 100, paint);//TR
            C.drawCircle(x, y + height, GamePanel.Swidth / 100, paint);//BL
            C.drawCircle(x + width, y + height, GamePanel.Swidth / 100, paint);//BR

            //draw buttons
            for (int i = 0;i<buttons.length;i++){
                buttons[i].draw(canvas);
            }



            //draw options

            int t = 23;
            bounds = new Rect();
            optionsPaint.getTextBounds("Sample", 0, 6, bounds);//cant use for width
            int textHeight = bounds.height();
            for (int i = 0;i<displayAtOnce;i++){
                int actuali = getShiftedOptionPos(i+currentSelection);
                optionsPaint.getTextBounds(options[actuali], 0, options[actuali].length(), bounds);
                int textWidth = bounds.width();
                //draw center darker
                if (i == displayAtOnce/2){
                    optionsPaint.setARGB(255, 0, 0, 0);
                    optionsPaint.setFakeBoldText(true);
                }
                C.drawText(options[actuali],x+width/2-textWidth/2,y+textHeight*(i+1),optionsPaint);


                optionsPaint.setFakeBoldText(false);
                optionsPaint.setARGB(150, 100, 100, 100);



            }

        }





    }

    public int getShiftedOptionPos(int i){
        int r = (((options.length-displayAtOnce/2)+i)+options.length)%(options.length);
        return r;
    }

    public String[] getOptions(){return options;}
    public Button getMainButton(){return mainButton;}

    public boolean isShouldDraw(){return shouldDraw;}
    public boolean isDrawDrop(){return drawDrop;}


    public void setShouldDraw(boolean setting){shouldDraw = setting;}
    public void setDrawDrop(boolean setting){drawDrop = setting;}

    public Button[] getButtons(){return buttons;}

    public void changeCurrentSelection(int c){
        currentSelection += c;
        if (currentSelection<0){//this is not an elegant solution fix if have time
            currentSelection+=options.length;
        }
    }
}
