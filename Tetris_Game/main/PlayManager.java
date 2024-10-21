
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class PlayManager {
    //jeden blok będzie miał 30pikseli wiec mamy 12 na 20 bloków
    final int WIDTH = 360; // szerokość pola gry
    final int HEIGHT = 600; // wysokość pola gry

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;


    public PlayManager(){
        // Ramka dla pola gry
        left_x = (GamePanel.WIDTH/2)-(WIDTH/2); // 1280/2 - 360/2 = 460 
        right_x = left_x + WIDTH; // 460 + 360 = 820
        top_y = 50; 
        bottom_y = top_y + HEIGHT; //650
    }

    public void update(){
        //update
    }

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
        g2.drawString("Next",x+60,y+60);
        
    }

}
