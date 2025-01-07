package mino;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class Block extends Rectangle { // klasa dla pojedynczego bloka w klocku
    public int x;
    public int y;
    public static final int SIZE=30; // 30x30px blok
    Color color;

    public Block(Color color) {
        this.color = color;
    }
    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, SIZE, SIZE);
    }
}
