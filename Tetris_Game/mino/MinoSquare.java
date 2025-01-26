package mino;

import java.awt.*;
import java.io.Serializable;

public class MinoSquare extends Mino implements Serializable {
    public MinoSquare() {
        create(Color.yellow);
    }


    @Override
    public void setXY(int x, int y) {
        // o  o  ->  b[0] b[2]
        // o  o  ->  b[1] b[3]
        b[0].x=x;
        b[0].y=y;
        b[1].x=x;
        b[1].y=y + Block.SIZE;
        b[2].x=x + Block.SIZE;
        b[2].y=y;
        b[3].x=x + Block.SIZE;
        b[3].y=y + Block.SIZE;
    }

    @Override
    public void getDirection1() {}
    @Override
    public void getDirection2() {}
    @Override
    public void getDirection3() {}
    @Override
    public void getDirection4() {}
}
