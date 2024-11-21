import javax.swing.*;
import java.awt.*;

public class GamePanel {

    private static JFrame gameFrame;
    public static JLayeredPane playSpace;
    public static CardBank cardBank;
    public static CardRiver cardRiver;
    public static final int WIDTH = 905;
    public static final int HEIGHT = 500;

    private static void createGamePanel() {
        gameFrame = new JFrame("Solitaire");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setLayout(null);
        gameFrame.setVisible(true);

        playSpace = new JLayeredPane();
        playSpace.setBounds(0, 0, WIDTH, HEIGHT);
        playSpace.setBackground(Color.darkGray);
        playSpace.setOpaque(true);
        playSpace.setVisible(true);
        playSpace.setLayout(null);
        gameFrame.add(playSpace);
    }

    private static void setUpPlaySpace() {
        CardStack deck = CardStack.generateRandomDeck();
        for (int i = 0; i < 7; i++) {
            PlayStack playStack = new PlayStack(135 + (i * (105)), 20);
            for (int j = 0; j < (i + 1); j++) {
                playStack.setupPush(deck.pop());
                //playSpace.add(playStack.peek());
                playSpace.moveToFront(playStack.peek());
            }
            playStack.peek().flip();
        }
        cardBank = new CardBank();
        while (!deck.isEmpty()) {
            cardBank.push(deck.pop());
            playSpace.moveToFront(cardBank.peek());
        }
        cardRiver = new CardRiver();
    }

    public static void main(String[] args) {
        createGamePanel();
        setUpPlaySpace();
        /*CardPanel card1 = new CardPanel(Suit.CLUBS, 5, 10, 10);
        CardPanel card2 = new CardPanel(Suit.DIAMONDS, 4, 10 + 65 + 30, 10);
        CardPanel card3 = new CardPanel(Suit.HEARTS, 5, 200, 10);
        playSpace.add(card1);
        card1.flip();
        playSpace.add(card2);
        card2.flip();
        playSpace.add(card3);
        card3.flip();
        card1.redraw();
        card2.redraw();
        card3.redraw();*/
    }
}
