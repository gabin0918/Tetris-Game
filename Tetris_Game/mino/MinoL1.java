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
}
