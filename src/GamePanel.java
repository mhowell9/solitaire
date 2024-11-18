import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GamePanel {

    private static JFrame gameFrame;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private GamePanel() {
        gameFrame = new JFrame("Solitaire");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new GamePanel();
        DraggablePanel panel = new DraggablePanel();
        panel.setBounds(10, 10, DraggablePanel.PANEL_SIZE, DraggablePanel.PANEL_SIZE);
        panel.setBackground(Color.blue);
        panel.setBorder(new LineBorder(Color.green));
        panel.setVisible(true);
        gameFrame.add(panel);
    }
}
