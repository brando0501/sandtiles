package games.bb.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Paint;

import static android.R.attr.width;

/**
 * Created by brand on 1/20/2017.
 */

public class Grid {

    private int rows, columns, spacing, width, height;

    Tile[][] tiles;


    //this looks backwards but it is so that you can make a grid with the input in form (x,y)
    public Grid(int c, int r){
        rows = r;
        columns = c;
        height = GamePanel.Sheight;
        width = GamePanel.Swidth;
        spacing = (int)(Integer.valueOf(width).doubleValue()/(double)(c+2));

        tiles = new Tile[c][r];

        //order like reading a book top left to bottom right
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                tiles[i][j] = new Tile();

            }
        }


    }


    public void draw(Canvas C){

        Paint paint = new Paint();
        paint.setStrokeWidth(width/(20*columns));
        paint.setARGB(255,244,84,32);

        int middle = height/2;

        //vertical
        for (int i = 0; i<=columns; i++){
            C.drawLine(spacing*(i+1),(int)(middle-(spacing/2.0)*rows),
                    spacing*(i+1),(int)(middle+(spacing/2.0)*rows),paint);
        }

        //horizontal
        for (int i = 0; i<=rows; i++){
            C.drawLine(spacing,(int)(middle-(spacing/2.0)*rows)+spacing*(i),
                    spacing*(columns+1),(int)(middle-(spacing/2.0)*rows)+spacing*(i),paint);
        }

        //dot corners TL BL TR BR
        C.drawCircle(spacing,(int)(middle-(spacing/2.0)*rows),paint.getStrokeWidth()/2,paint);
        C.drawCircle(spacing,(int)(middle+(spacing/2.0)*rows),paint.getStrokeWidth()/2,paint);
        C.drawCircle(spacing*(columns+1),(int)(middle-(spacing/2.0)*rows),paint.getStrokeWidth()/2,paint);
        C.drawCircle(spacing*(columns+1),(int)(middle+(spacing/2.0)*rows),paint.getStrokeWidth()/2,paint);

        Paint numbers = new Paint();

        numbers.setTextSize(spacing);
        numbers.setARGB(255,255,50,50);



        //order like reading a book top left to bottom right
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                C.drawText(new Integer(tiles[i][j].getCount()).toString(),spacing*(i+1)+(int)((spacing/100.0)*23.0),
                        (int)(middle-(spacing/2.0)*rows)+spacing*(j)+(int)((spacing/100.0)*87.0),numbers);

            }
        }


    }
}
