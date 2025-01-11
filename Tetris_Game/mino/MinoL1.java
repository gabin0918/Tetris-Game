package mino;

import java.awt.Color;

public class MinoL1 extends Mino {
    public MinoL1() {
        create(Color.orange);
    }

    @Override
    public void setXY(int x, int y) {
        // o    -> b[1]
        // o    -> b[0]
        // o o  -> b[2] b[4]
        b[0].x = x;
        b[0].y = y;
        b[1].x = x;
        b[1].y = y - Block.SIZE;
        b[2].x = x;
        b[2].y = y + Block.SIZE;
        b[3].x = x + Block.SIZE;
        b[3].y = y + Block.SIZE;
    }

    @Override
    public void getDirection1() {
        // o    -> b[1]
        // o    -> b[0]
        // o o  -> b[2] b[3]

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(1);
    }

    @Override
    public void getDirection2() {
        //
        // o  o  o  -> b[2] b[0] b[1]
        // o        -> b[3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y + Block.SIZE;

        updateXY(2);
    }

    @Override
    public void getDirection3() {
        //o  o  -> b[3] b[2]
        //   o  ->      b[0]
        //   o  ->      b[1]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x - Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(3);
    }

    @Override
    public void getDirection4() {
        //      o  ->           b[3]
        //o  o  o ->  b[1] b[0] b[2]
        //
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y - Block.SIZE;

        updateXY(4);
    }
}
