package games.bb.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;



/**
 * Created by brand on 1/20/2017.
 */

public class Grid {

    private int rows, columns, spacing, width, height;
    private int topple = 4;

    public Tile[][] tiles;


    //this looks backwards but it is so that you can make a grid with the input in form (x,y)
    public Grid(int c, int r){
        rows = r;
        columns = c;
        height = GamePanel.Sheight;
        width = GamePanel.Swidth;
        spacing = (int)(Integer.valueOf(width).doubleValue()/(double)(c+2));

        tiles = new Tile[c][r];

        int middle = height/2;

        //order like reading a book top left to bottom right
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                tiles[i][j] = new Tile(spacing*(i+1),(int)(middle-(spacing/2.0)*rows)+spacing*(j),spacing,i,j);

            }
        }


    }


    public void draw(Canvas C){

        C.drawRGB(222,222,222);

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
                //C.drawCircle(tiles[i][j].getX(),tiles[i][j].getY(),6,numbers);
                C.drawText(new Integer(tiles[i][j].getCount()).toString(),spacing*(i+1)+(int)((spacing/100.0)*23.0),
                        (int)(middle-(spacing/2.0)*rows)+spacing*(j)+(int)((spacing/100.0)*87.0),numbers);

            }
        }


    }

    public boolean checkTiles(Point p){
        int middle = height/2;
        //iterate all tiles
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                if (tiles[i][j].touched(p)){

                    if (tiles[i][j].increase()) {
                        rippleTiles(i, j);
                    }


                    return true;
                }

            }
        }
        return false;
    }

    public void rippleTiles(int i, int j){
        //have to reference all tiles from here to avoid static issues
        if(i-1>=0){if(tiles[i-1][j].increase()){
            rippleTiles(i-1,j);}}//left
        if(i+1<columns){if(tiles[i+1][j].increase()) {
            rippleTiles(i+1, j);}}//right
        if(j-1>=0){if(tiles[i][j-1].increase()){
            rippleTiles(i,j-1);}}//top
        if(j+1<rows){if(tiles[i][j+1].increase()){
            rippleTiles(i,j+1);}}//bottom
    }

    public int getTopple(){return topple;}

    public Tile[][] getTiles(){return tiles;}
}
