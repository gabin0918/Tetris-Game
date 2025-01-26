package main;

import interfaces.Drawable;
import interfaces.Updatable;
import mino.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager implements Serializable, Drawable, Updatable {
    //jeden blok będzie miał 30pikseli wiec mamy 12 na 20 bloków
    final int WIDTH = 360; // szerokość pola gry
    final int HEIGHT = 600; // wysokość pola gry

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXT_MINO_X;
    final int NEXT_MINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();
    public ArrayList<Block> copyOfBlocks = new ArrayList<>();

    // Dodatkowe zmienne
    public static int dropInterval = 60; // klocek opuszcza sie co 60 klatek, czyli co 1 sekunde
    boolean gameOver = false;

    // Efekt na usuwanie klocek
    int effectCounter = 0;
    boolean effectCounterOn = false;
    ArrayList<Integer> effectY = new ArrayList<>();

    // Score & Levele
    int level = 1;
    int lines;
    int score;

    public PlayManager(){
        // Ramka dla pola gry
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        System.out.println(left_x);
        right_x = left_x + WIDTH; // 460 + 360 = 820
        System.out.println(right_x);
        top_y = 50; 
        bottom_y = top_y + HEIGHT; //650

        // Pozycja wejsciowa dla klocka - na srodku planszy
        MINO_START_X = left_x + WIDTH/2 - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXT_MINO_X = right_x + 175;
        NEXT_MINO_Y = top_y + 500;

        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

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

    @Override
    public void update(){
        //update
        if (currentMino.active == false) {
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            // sprawdzenie czy koniec gry - klocek nie zmienil pozycji po pojaiwniue sie i nie mogl sie ruszyc,
            // bo wystapila od razu kolizja
            if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                gameOver = true;
            }

            currentMino.deactivating = false; // reset flagi opoznienia deaktywacji klocka

            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

            checkDelete(); // kiedy Mino staje sie nieaktywny, to sprawdzenie czy sa pelne linie klocek do usuniecia
            copyOfBlocks = staticBlocks;
        }
        else {
            currentMino.update();
        }
    }

    private void checkDelete(){
        // zmienne do poruszania sie po PlayManager
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0; // licznik usunietych linii na raz

        while(x < right_x && y < bottom_y){

            for(int i = 0; i < staticBlocks.size(); i++){
                if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y){
                    blockCount++; // zwiekszanie licznika jesli w danym punkcie jest klocek
                }
            }

            x += Block.SIZE;

            if(x == right_x){ // jesli na koncu obecnej lininii

                if(blockCount == 12){ // jesli prawda, to cala linia pelna klockow i mozna je usunac

                    effectY.add(y); // dodanie rzedu klocek, ktory usuwamy i gdie bedzie efekt
                    effectCounterOn = true;

                    for(int i = staticBlocks.size() - 1; i > -1; i--){
                        // usuwanie klocka, jesli jest w danej linii y
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }

                    lines++; // calkowita liczba usunietych linii za gre
                    lineCount++; // liczba usunietych linii na raz

                    // Przyspieszenie spadania kolckow co 10 usunietych linii i level-up,
                    if(lines % 10 == 0 && dropInterval > 1){
                        level++;
                        if (dropInterval > 10) {
                            dropInterval-=10;
                        } else {
                            dropInterval-=1;
                        }
                    }

                    // linia klockow zostala usunieta, wiec klocki nad nia powinny zostac opuszczone
                    for(int i = 0; i < staticBlocks.size(); i++){
                        // jesli klocek jest nad obecnym y, to opuszczamy go o rozmiar klocka
                        if(staticBlocks.get(i).y < y){
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }
                // przejscie do linia nizej
                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        // jesli zostala usunieta linia klocek
        if(lineCount > 0){
            int singleLineScore = level * 10;
            score += singleLineScore * lineCount;
        }
    }

    @Override
    public void draw(Graphics2D g2){

        // ta duza ramka
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f)); // grubosc lini to bd 4pxl
        g2.drawRect(left_x-4,top_y-4,WIDTH+8,HEIGHT+8); // współrzędne lewego gornego rogu no i wysokosc szerokosc
                                                        // odejmuje 4 i dodaje 8 bo ramka ma szerokosc 4 pixeli
        
        // mniejsza ramka na wyswietlanie nastepnego elementu
        int x=right_x +100;
        int y=bottom_y-200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Comic Sans",Font.PLAIN,30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); //to robi wygladzanie krawedzi i wsm to nw czy jst potrzebne
        g2.drawString("Nastepny",x+35,y+50);

        // Ramka ze score
        g2.drawRect(x, top_y, 250, 300);
        x +=40;
        y = top_y + 90;
        g2.drawString("Poziomy: " + level,x,y); y += 70;
        g2.drawString("Linie: " + lines,x,y); y += 70;
        g2.drawString("Wynik: " + score,x,y);

        // Rysowanie obecnego klocka
        if(currentMino != null) {
            currentMino.draw(g2);
        }
        nextMino.draw(g2);

        // Rysowanie juz opadnietych klocek
        for(int i = 0 ; i < staticBlocks.size() ; i++){
            staticBlocks.get(i).draw(g2);
        }

        //Rysowanie effektu przy usuwaniu wypelnionego rzedu klocek
        if(effectCounterOn){
            effectCounter++;

            // kazdy rzad przed usunieciem zrobi sie caly na czerwono
            g2.setColor(Color.red);
            for(int i = 0; i < effectY.size(); i++){
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }

            // Stop rysowania efektu
            if(effectCounter == 10){
                effectY.clear();
                effectCounter = 0;
                effectCounterOn = false;
            }
        }

        // Komunikat GAME OVER
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(gameOver){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("KONIEC", x, y);
        }

        // Rysowania nazwy gry
        x = 45;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Projekt TETRIS", x, y);
    }

}
