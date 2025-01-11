package mino;

import main.KeyHandler;
import main.PlayManager;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class  Mino { // klasa bazowa dla wszystkich rodzajow klockow
    public Block[] b = new Block[4]; // tablicy na bloki, przechowujace x i y wejsciowe
    public Block[] tempB = new Block[4]; // array na bloki,  przechowujace x i y po obracaniu klocka
    int autoDropCounter = 0;
    public int direction = 1; // są 4 możliwe ustawienia klocka (1,2,3,4)

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
    // ustawienie zmiennej direction i nowego położenia klocka
    public void updateXY(int direction){
        this.direction = direction;
        b[0].x = tempB[0].x;
        b[0].y = tempB[0].y;
        b[1].x = tempB[1].x;
        b[1].y = tempB[1].y;
        b[2].x = tempB[2].x;
        b[2].y = tempB[2].y;
        b[3].x = tempB[3].x;
        b[3].y = tempB[3].y;
    };
    public abstract void getDirection1(); // ustawia na polozenie 1
    public abstract void getDirection2(); // ustawia na polozenie 2
    public abstract void getDirection3(); // ustawia na polozenie 3
    public abstract void getDirection4(); // ustawia na polozenie 4
    public void update(){
        // aktualizacja wspolrzednzch klocka po nacisnieciu przyciskow
        if(KeyHandler.upPressed){ // przekręcanie klocka
            switch(direction){
                case 1: getDirection2(); break;
                case 2: getDirection3(); break;
                case 3: getDirection4(); break;
                case 4: getDirection1(); break;
            }
            KeyHandler.upPressed = false;
        }
        if (KeyHandler.downPressed) {
            b[0].y += Block.SIZE;
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;

            KeyHandler.downPressed = false;
            autoDropCounter = 0; // przy przesunieciu w dol zerowanie licznika
        }
        if (KeyHandler.leftPressed) {
            b[0].x -= Block.SIZE;
            b[1].x -= Block.SIZE;
            b[2].x -= Block.SIZE;
            b[3].x -= Block.SIZE;

            KeyHandler.leftPressed = false;
        }

        if (KeyHandler.rightPressed) {
            b[0].x += Block.SIZE;
            b[1].x += Block.SIZE;
            b[2].x += Block.SIZE;
            b[3].x += Block.SIZE;

            KeyHandler.rightPressed = false;
        }
        // automatyczne opadanie klockow
        autoDropCounter++; // zwieksza sie kazda klatke (kazde rysowanie GamePanel)
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
