package mino;

import interfaces.Collidable;
import interfaces.Drawable;
import interfaces.Rotatable;
import interfaces.Updatable;
import main.KeyHandler;
import main.PlayManager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public abstract class  Mino implements Serializable, Drawable, Updatable, Rotatable, Collidable { // klasa bazowa dla wszystkich rodzajow klockow
    public Block[] b = new Block[4]; // tablicy na bloki, przechowujace x i y wejsciowe
    public Block[] tempB = new Block[4]; // array na bloki,  przechowujace x i y po obracaniu klocka
    int autoDropCounter = 0;
    public int direction = 1; // są 4 możliwe ustawienia klocka (1,2,3,4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating = false; // flaga, ze Mino dotarl do dolu, pozwoli zachowac aktywnosc jeszcze chwile
    int deactivatingCounter = 0; // ile jeszcze Mino jest aktywny po kolizji dolem

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
    // ustawienie zmiennej direction i nowego położenia klocka po wcisnieciu Up

    @Override
    public void updateXY(int direction){

        checkRotationCollision();

        if(leftCollision == false && rightCollision == false && bottomCollision == false) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }
    @Override
    public abstract void getDirection1(); // ustawia na polozenie 1
    @Override
    public abstract void getDirection2(); // ustawia na polozenie 2
    @Override
    public abstract void getDirection3(); // ustawia na polozenie 3
    @Override
    public abstract void getDirection4(); // ustawia na polozenie 4
    @Override
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if(b[i].x == PlayManager.left_x) {
                leftCollision = true;

            }
        }

        for (int i = 0; i < b.length; i++) {
            if(b[i].x + Block.SIZE == PlayManager.right_x) {
                rightCollision = true;

            }
        }

        for (int i = 0; i < b.length; i++) {
            if(b[i].y + Block.SIZE == PlayManager.bottom_y) {
                bottomCollision = true;

            }
        }
    }
    @Override
    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        checkStaticBlockCollision();

        for (int i = 0; i < b.length; i++) {
            if(tempB[i].x < PlayManager.left_x) {
                leftCollision = true;
                System.out.println("test");
            }
        }

        for (int i = 0; i < b.length; i++) {
            if(tempB[i].x + Block.SIZE > PlayManager.right_x) {
                rightCollision = true;
                System.out.println("test");
            }
        }

        for (int i = 0; i < b.length; i++) {
            if(tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true;
                System.out.println("test");
            }
        }
    };
    @Override
    public void checkStaticBlockCollision() {
        for(int i = 0; i < PlayManager.staticBlocks.size(); i++){
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            for (int j = 0; j < b.length; j++) {
                if(b[j].y + Block.SIZE == targetY && b[j].x == targetX) {
                    bottomCollision = true;
                }
            }
            for (int j = 0; j < b.length; j++) {
                if(b[j].x - Block.SIZE == targetX && b[j].y == targetY) {
                    leftCollision = true;
                }
            }
            for (int j = 0; j < b.length; j++) {
                if(b[j].x + Block.SIZE == targetX && b[j].y == targetY) {
                    rightCollision = true;
                }
            }
        }
    }

    @Override
    public void update(){

        // sprawdzenie czy klocek osiagnal dolu - jesli tak, to zachowac jego aktywnosc na chwile, by mogl zrobic slide
        if(deactivating){
            deactivating();
        }

        checkMovementCollision();

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
            if (bottomCollision == false) {
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                autoDropCounter = 0; // przy przesunieciu w dol zerowanie licznika
            }
            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed) {
            if (leftCollision == false) {
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
            KeyHandler.leftPressed = false;
        }

        if (KeyHandler.rightPressed) {
            if (rightCollision == false) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
            deactivating= true;
        }
        else {
        // automatyczne opadanie klockow
            autoDropCounter++; // zwieksza sie kazda klatke (kazde rysowanie GamePanel)
            if(autoDropCounter == PlayManager.dropInterval) {
                if (bottomCollision == false) {
                    b[0].y += Block.SIZE;
                    b[1].y += Block.SIZE;
                    b[2].y += Block.SIZE;
                    b[3].y += Block.SIZE;
                    autoDropCounter = 0;
                }
            }
        }
    }
    // opoznienie nieaktywnosci klocka, by mogl zrobic slide
    public void deactivating(){
        deactivatingCounter++;

        if (deactivatingCounter == 45) { // po 45 narysowan panelu
            deactivatingCounter = 0;
            checkMovementCollision(); // sprawdzenie czy nadal pozostaje kolizja z dolem
            if(bottomCollision){
                active = false;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].color);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - 2*margin,  Block.SIZE - 2*margin);
        g2.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - 2*margin,  Block.SIZE-  2*margin);
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - 2*margin,  Block.SIZE-  2*margin);
        g2.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - 2*margin,  Block.SIZE-  2*margin);
    }
}
