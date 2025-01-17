package main;

import mino.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;

public class PlayManager {
    //jeden blok będzie miał 30pikseli wiec mamy 12 na 20 bloków
    final int WIDTH = 390; // szerokość pola gry
    final int HEIGHT = 600; // wysokość pola gry

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    // Dodatkowe zmienne
    public static int dropInterval = 60; // klocek opuszcza sie co 60 klatek, czyli co 1 sekunde

    public PlayManager(){
        // Ramka dla pola gry
        //left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        //right_x = left_x + WIDTH; // 460 + 360 = 820
        left_x = 460;
        right_x = 850;
        top_y = 50; 
        bottom_y = top_y + HEIGHT; //650

        // Pozycja wejsciowa dla klocka - na srodku planszy
        MINO_START_X = left_x + WIDTH/2 - Block.SIZE/2;
        MINO_START_Y = top_y + Block.SIZE;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    private Mino pickMino() {

        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0: mino = new MinoL1(); break;
            case 1: mino = new MinoL2(); break;
            case 2: mino = new MinoSquare(); break;
            case 3: mino = new MinoBar(); break;
            case 4: mino = new MinoT(); break;
            case 5: mino = new MinoZ1(); break;
            case 6: mino = new MinoZ2(); break;
        }
        return mino;
    }


    public void update(){
        //update
        currentMino.update();
    }

    public void draw(Graphics2D g2){

        // ta duza ramka
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f)); // grubosc lini to bd 4pxl
        g2.drawRect(left_x-34,top_y-4,WIDTH+38,HEIGHT+8); // współrzędne lewego gornego rogu no i wysokosc szerokosc
                                                        // odejmuje 4 i dodaje 8 bo ramka ma szerokosc 4 pixeli
        
        // mniejsza ramka na wyswietlanie nastepnego elementu
        int x=right_x +100;
        int y=bottom_y-200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Comic Sans",Font.PLAIN,30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //to robi wygladzanie krawedzi i wsm to nw czy jst potrzebne
        g2.drawString("Next",x+60,y+60);

        // Rysujemy obecny klocek
        if(currentMino != null) {
            currentMino.draw(g2);
        }
    }

}
