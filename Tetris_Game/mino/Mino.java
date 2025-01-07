package mino;

import main.PlayManager;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class  Mino { // klasa bazowa dla wszystkich rodzajow klockow
    public Block[] b = new Block[4]; // tablicy na bloki, przechowujace x i y wejsciowe
    public Block[] tempB = new Block[4]; // array na bloki,  przechowujace x i y po obracaniu klocka
    int autoDropCounter = 0;

    public void create(Color c){ // inicjalizacja tablic
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public abstract void setXY(int x, int y);
    public void updateXY(int direction){};
    public void update(){
        autoDropCounter++; // zwiaksza sie kazda klatke (kazde rysowanie GamePanel)
        if(autoDropCounter == PlayManager.dropInterval){
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].color);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - 2*margin,  Block.SIZE - 2*margin);
        g2.fillRect(b[1].x+ margin, b[1].y+ margin, Block.SIZE- 2*margin,  Block.SIZE- 2*margin);
        g2.fillRect(b[2].x+ margin, b[2].y+ margin, Block.SIZE- 2*margin,  Block.SIZE- 2*margin);
        g2.fillRect(b[3].x+ margin, b[3].y+ margin, Block.SIZE- 2*margin,  Block.SIZE- 2*margin);
    }
}
