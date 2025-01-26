package mino;

import interfaces.Drawable;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Block extends Rectangle implements Serializable, Drawable { // klasa dla pojedynczego bloka w klocku
    public int x;
    public int y;
    public static final int SIZE=30; // 30x30px blok
    Color color;

    public Block(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(color);
        g2.fillRect(x+margin, y+margin, SIZE-(margin*2), SIZE-(margin*2));
    }
}
