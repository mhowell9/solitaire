import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GamePanel {

    private static JFrame gameFrame;
    public static JLayeredPane layerPane;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GamePanel() {
        gameFrame = new JFrame("Solitaire");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setLayout(null);
        gameFrame.setVisible(true);

        layerPane = new JLayeredPane();
        layerPane.setBounds(0, 0, WIDTH, HEIGHT);
        layerPane.setBackground(Color.darkGray);
        layerPane.setOpaque(true);
        layerPane.setVisible(true);
        layerPane.setLayout(null);
        gameFrame.add(layerPane);
    }

    public static void main(String[] args) {
        new GamePanel();
        CardPanel card1 = new CardPanel(Suit.CLUBS, 3, 10, 10);
        CardPanel card2 = new CardPanel(Suit.SPADES, 5, 200, 10);
        layerPane.add(card1);
        card2.flip();
        layerPane.add(card2);
    }
}
