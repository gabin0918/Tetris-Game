package main;

import javax.swing.JFrame;

public class Main {
 public static void main(String[] args) {
     
JFrame window = new JFrame("Tetris");// Tytuł
window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program kończy działanie po zamknięciu okna
window.setResizable(false); // okno nie jest zmienialne

GamePanel gp = new GamePanel();// tworzenie obiektu klasy GamePanel
window.add(gp);// dodanie obiektu klasy GamePanel do okna
window.pack();// okno dopasowuje się do rozmiaru panelu

window.setLocationRelativeTo(null);// okno pojawia się na środku ekranu
window.setVisible(true);// okno jest widoczne



 }
}
