package games.bb.myfirstgame;


import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by brand on 1/20/2017.
 */

public class Tile {
    private int count = 0;
    private int x, y, sideLength;

    public Tile(int xpos, int ypos, int l){
        x = xpos;
        y = ypos;
        sideLength = l;



    }

    public Tile(int xpos, int ypos, int l, int c){
        x = xpos;
        y = ypos;
        sideLength = l;
        count = c;
    }

    public int getCount(){
        return count;
    }

    public int getX() {return x;}

    public int getY() {return y;}

    public int getSideLength() {return sideLength;}

    public boolean touched(Point p){
        if(x<p.x&&p.x<x+sideLength&&y<p.y&&p.y<y+sideLength){
            count++;
            return true;
        }
        return false;
    }

}
