package games.bb.myfirstgame;


import android.graphics.Canvas;
import android.graphics.Point;

/**
 * Created by brand on 1/20/2017.
 */

public class Tile {
    private int count = 0;
    private int topple = 4;

    private int x, y, sideLength, row, column;

    public Tile(int xpos, int ypos, int l, int col, int r){
        x = xpos;
        y = ypos;
        sideLength = l;

        row = r;
        column = col;



    }

    public Tile(int xpos, int ypos, int l, int c, int col, int r){
        x = xpos;
        y = ypos;
        sideLength = l;
        count = c;

        row = r;
        column = col;
    }

    public int getCount(){
        return count;
    }

    public int getX() {return x;}

    public int getY() {return y;}

    public int getSideLength() {return sideLength;}

    public boolean touched(Point p){
        if(x<p.x&&p.x<x+sideLength&&y<p.y&&p.y<y+sideLength){


            return true;
        }
        return false;
    }

    public boolean increase(){
        count++;
        return update();
    }

    public boolean update(){

        if (count >= topple){
            count = count - 4;
            return true;
        }
        return false;

    }

}
