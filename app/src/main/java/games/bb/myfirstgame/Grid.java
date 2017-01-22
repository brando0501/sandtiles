package games.bb.myfirstgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextPaint;


/**
 * Created by brand on 1/20/2017.
 */

public class Grid {

    private int rows, columns, spacing, width, height;
    private int topple = 4;
    private int players = 2;
    private int tilesToWin = 2;//tiles allowed to NOT be owned by winner
    private int winner = -1;//-1 until winner declared

    private Tile[][] tiles;

    private int turns = 0;
    private int playerSlot = 0;//base 0 reset on player number





    //this looks backwards but it is so that you can make a grid with the input in form (x,y)
    public Grid(int c, int r){
        rows = r;
        columns = c;
        height = GamePanel.Sheight;
        width = GamePanel.Swidth;
        spacing = (int)(Integer.valueOf(width).doubleValue()/(double)(c+2));

        tilesToWin = (int)(((rows*columns))/4.0);

        tiles = new Tile[c][r];

        int middle = height/2;

        //order like reading a book top left to bottom right
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                tiles[i][j] = new Tile(spacing*(i+1),(int)(middle-(spacing/2.0)*rows)+spacing*(j),spacing,i,j, -1);

            }
        }


    }


    public void draw(Canvas C){

        System.out.println(rows +" "+ columns);



        C.drawRGB(222,222,222);

        Paint paint = new Paint();
        paint.setStrokeWidth(width/(20*columns));
        paint.setARGB(255,244,84,32);
        paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        int middle = height/2;

        //C.drawLine(0,0,width,height,paint);

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
        numbers.setFlags(TextPaint.ANTI_ALIAS_FLAG);
        numbers.setFakeBoldText(true);


        //order like reading a book top left to bottom right
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                //C.drawCircle(tiles[i][j].getX(),tiles[i][j].getY(),6,numbers);
                //add more cases if want to incorporate more players
                switch (tiles[i][j].getOwnership()+1) {
                    case 0:
                        numbers.setARGB(255, 255, 255, 255);
                        break;
                    case 1:
                        numbers.setARGB(255, 255, 50, 50);
                        break;
                    case 2:
                        numbers.setARGB(255, 50, 50, 255);
                        break;
                    case 3:
                        numbers.setARGB(255, 0, 155, 25);
                        break;
                    case 4:
                        numbers.setARGB(255, 152, 66, 244);
                        break;
                }
                C.drawText(new Integer(tiles[i][j].getCount()).toString(),spacing*(i+1)+(int)((spacing/100.0)*23.0),
                        (int)(middle-(spacing/2.0)*rows)+spacing*(j)+(int)((spacing/100.0)*87.0),numbers);

            }
        }

        drawStats(C);


    }

    public void drawStats(Canvas C){
        int middleH = height/2;
        int middleW = width/2;

        Paint paint = new Paint();
        paint.setTextSize(width/12);
        paint.setFlags(TextPaint.ANTI_ALIAS_FLAG);

        //currently only displays first two players
        Rect bounds = new Rect();
        Paint textPaint = paint;


        String toWinString = ("To Win: " + (rows*columns-tilesToWin));
        textPaint.getTextBounds(toWinString, 0, toWinString.length(), bounds);
        int Twidth = bounds.width();
        paint.setARGB(255, 0, 0, 0);
        C.drawText(toWinString,middleW-Twidth/2,(int)(middleH+(spacing/2.0)*rows)+(int)((height/100.0)*5.0),paint);

        paint.setARGB(255, 255, 50, 50);
        C.drawText("Player 1: " + playerStatus(0),spacing-(int)((width/100.0)*8.0),(int)(middleH+(spacing/2.0)*rows)+(int)((height/100.0)*10.0),paint);

        paint.setARGB(255, 50, 50, 255);
        C.drawText("Player 2: " + playerStatus(1),spacing+middleW-(int)((width/100.0)*8.0),(int)(middleH+(spacing/2.0)*rows)+(int)((height/100.0)*10.0),paint);

        //C.drawText("test",0,20,paint);

    }

    public int playerStatus(int p){
        int tileCount = 0;
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                if (tiles[i][j].getOwnership()==p){
                    tileCount++;
                }
            }
        }
        return tileCount;
    }

    public boolean checkTiles(Point p){
        turns++;
        playerSlot = turns%players;
        int middle = height/2;
        //iterate all tiles
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                if (tiles[i][j].touched(p)){

                    if (tiles[i][j].increase(playerSlot)) {
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
        if(i-1>=0){if(tiles[i-1][j].increase(playerSlot)){
            rippleTiles(i-1,j);}}//left
        if(i+1<columns){if(tiles[i+1][j].increase(playerSlot)) {
            rippleTiles(i+1, j);}}//right
        if(j-1>=0){if(tiles[i][j-1].increase(playerSlot)){
            rippleTiles(i,j-1);}}//top
        if(j+1<rows){if(tiles[i][j+1].increase(playerSlot)){
            rippleTiles(i,j+1);}}//bottom
    }

    public void winStatus(){
        //order like reading a book top left to bottom right
        int[] playas = new int[players];
        for (int i = 0; i < columns; i++){
            for (int j = 0; j < rows; j++){
                if (tiles[i][j].getOwnership()>-1){
                    playas[tiles[i][j].getOwnership()]++;
                }
            }
        }

        for (int i = 0; i<playas.length; i++){
            if (playas[i]>=(rows*columns)-tilesToWin){
                winner = i;
            }
        }
    }

    public int getWinner(){
        winStatus();
        return winner;
    }

    public int getTopple(){return topple;}

    public Tile[][] getTiles(){return tiles;}
}
