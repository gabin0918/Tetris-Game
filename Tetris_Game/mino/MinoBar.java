package mino;

import java.awt.*;
import java.io.Serializable;

public class MinoBar extends Mino implements Serializable {
    public MinoBar(){
        create(Color.cyan);
    }


    @Override
    public void setXY(int x, int y) {
        // o  o  o  o ->  b[1] b[0] b[2] b[3]
        b[0].x = x;
        b[0].y = y;
        b[1].x = x - Block.SIZE;
        b[1].y = y;
        b[2].x = x + Block.SIZE;
        b[2].y = y;
        b[3].x = x + 2 * Block.SIZE;
        b[3].y = y;
    }

    @Override
    public void getDirection1() {
        // o  o  o  o ->  b[1] b[0] b[2] b[3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + 2 * Block.SIZE;
        tempB[3].y = b[0].y;
        updateXY(1);
    }

    @Override
    public void getDirection2() {
        // o  ->  b[1]
        // o  ->  b[0]
        // o  ->  b[2]
        // o  ->  b[3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y + 2 * Block.SIZE;
        updateXY(2);
    }

    @Override
    public void getDirection3() {
        // o  o  o  o ->  b[1] b[0] b[2] b[3]
        getDirection1();
    }

    @Override
    public void getDirection4() {
        // o  ->  b[1]
        // o  ->  b[0]
        // o  ->  b[2]
        // o  ->  b[3]
        getDirection2();
    }
}
