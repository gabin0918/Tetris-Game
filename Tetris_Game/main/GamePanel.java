package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{ // klasa dziedziczy po JPanel i implementuje interfejs Runnable
    public static final int WIDTH = 1280; // szerokość okna
    public static final int HEIGHT = 720; // wysokość okna

    final int FPS = 60; // ilość klatek na sekundę
    Thread gameThread; // wątek gry
    PlayManager pm;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));// ustawienie rozmiaru okna
        this.setBackground(Color.black);// ustawienie koloru tła
        this.setLayout(null); //żeby można było rysować na panelu
        // Dodanie KeyListener
        this.addKeyListener(new KeyHandler()); // dodanie obiektu, który obsłyży zdarzenie z klawiaturą
        this.setFocusable(true); // abz GamePanel przechwytywał zdarzenia

        pm= new PlayManager(); // utworzenie pola gry
    }
    public void lauchGame(){
        gameThread = new Thread(this); // utworzenie nowego wątku
        gameThread.start(); // uruchomienie wątku
    }

    @Override
    public void run() {
       // its so confusing sometimes to be a girl
       //girl girl girl
        double drawInterval =1000000000/FPS; // czas pomiędzy klatkami
        double delta = 0; // zmienna pomocnicza
        long lastTime = System.nanoTime(); // czas w nanosekundach
        long currentTime; // aktualny czas

        while(gameThread!=null ){// pętla gry
            currentTime = System.nanoTime(); // aktualny czas
            delta += (currentTime - lastTime)/drawInterval; // obliczenie zmiany czasu
            lastTime = currentTime; // przypisanie aktualnego czasu do zmiennej lastTime

            if(delta>=1){ // 60 razy na sekundę
                update(); 
                repaint(); 
                delta--; 
            }

        }
    }
    private void update(){
        pm.update(); 
    }
    public void paintComponent(Graphics g){// rysowanie komponentów
        super.paintComponent(g); // rysowanie tła

        Graphics2D g2 = (Graphics2D) g; // rzutowanie obiektu Graphics na Graphics2D
        pm.draw(g2); // rysowanie pola gry
    }
}