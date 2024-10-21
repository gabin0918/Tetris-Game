
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class GamePanel extends JPanel{ // klasa dziedziczy po JPanel
    public static final int WIDTH =1280; // szerokość okna
    public static final int HEIGHT = 720; // wysokość okna
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));// ustawienie rozmiaru okna
        this.setBackground(Color.black);// ustawienie koloru tła
        this.setLayout(null); //żeby można było rysować na panelu
    }


}