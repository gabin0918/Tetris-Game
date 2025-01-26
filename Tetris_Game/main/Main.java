package main;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.JFrame;

public class Main {
 public static void main(String[] args) {

  FileLogger logger = new FileLogger("application.log");

  logger.log("Aplikacja została uruchomiona.");

  System.out.println("Logi zostały zapisane.");

  JFrame window = new JFrame("Tetris");// Tytuł
  window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program kończy działanie po zamknięciu okna
  window.setResizable(false); // okno nie jest zmienialne

  GamePanel gp = new GamePanel();// tworzenie obiektu klasy GamePanel

  window.addWindowListener(new WindowAdapter() {
   @Override
   public void windowClosing(WindowEvent e) {
    logger.log("Aplikacja została zamknięta.");

    try (FileOutputStream fileOut = new FileOutputStream("save.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

     out.writeObject(gp.pm);
     System.out.println("Obiekt został zapisany");
    } catch (Exception c) {
     c.printStackTrace();
    }

    window.dispose();
    System.exit(0);
   }
  });


  window.add(gp);// dodanie obiektu klasy GamePanel do okna
  window.pack();// okno dopasowuje się do rozmiaru panelu

  window.setLocationRelativeTo(null);// okno pojawia się na środku ekranu
  window.setVisible(true);// okno jest widoczne

  gp.lauchGame(); // wlaczamy Thread, aby puscic game loop

 }
}
