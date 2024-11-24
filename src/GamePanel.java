import javax.swing.*;
import java.awt.*;

public class GamePanel {

    private static JFrame gameFrame;
    public static JLayeredPane playSpace;
    public static CardBank cardBank;
    public static CardRiver cardRiver;
    public static SuitStack heartStack;
    public static SuitStack diamondStack;
    public static SuitStack clubStack;
    public static SuitStack spadeStack;
    public static PlayStack[] playStacks;
    public static final int WIDTH = 905;
    public static final int HEIGHT = 530;

    public static final ImageIcon BACKGROUND = new ImageIcon("assets/game_board.png");

    private static void createGamePanel() {
        gameFrame = new JFrame("Solitaire");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(WIDTH + 16, HEIGHT + 39);
        gameFrame.setResizable(false);
        gameFrame.setLayout(null);
        gameFrame.setVisible(true);

        playSpace = new JLayeredPane();
        playSpace.setBounds(0, 0, WIDTH, HEIGHT);
        playSpace.setVisible(true);
        playSpace.setLayout(null);
        JLabel background = new JLabel(BACKGROUND);
        background.setBounds(0, 0, WIDTH, HEIGHT);
        background.setVisible(true);
        gameFrame.add(playSpace);

        CardBankClickable bankClickable = new CardBankClickable();
        bankClickable.setOpaque(false);
        playSpace.add(bankClickable);
        playSpace.add(background);
    }

    private static void setUpPlaySpace() {
        CardStack deck = CardStack.generateRandomDeck();
        playStacks = new PlayStack[7];
        for (int i = 0; i < 7; i++) {
            playStacks[i] = new PlayStack(135 + (i * (95)), 20);
            for (int j = 0; j < (i + 1); j++) {
                playStacks[i].forcePush(deck.pop());
                //playSpace.add(playStack.peek());
                playSpace.moveToFront(playStacks[i].peek());
            }
            playStacks[i].peek().flip();
        }
        cardBank = new CardBank();
        while (!deck.isEmpty()) {
            cardBank.push(deck.pop());
            playSpace.moveToFront(cardBank.peek());
        }
        cardRiver = new CardRiver();
        heartStack = new SuitStack(820, 20, Suit.HEARTS);
        diamondStack = new SuitStack(820, 150, Suit.DIAMONDS);
        clubStack = new SuitStack(820, 280, Suit.CLUBS);
        spadeStack = new SuitStack(820, 410, Suit.SPADES);
    }

    public static void main(String[] args) {
        createGamePanel();
        setUpPlaySpace();
    }

    public static void checkForWin() {
        System.out.println("Checking win");
        if (heartStack.top != 13) return;
        if (diamondStack.top != 13) return;
        if (clubStack.top != 13) return;
        if (spadeStack.top != 13) return;
        win();
    }

    public static void win() {
        System.out.println("win");
        gameFrame.remove(playSpace);
        JPanel winPanel = new JPanel();
        winPanel.setOpaque(true);
        winPanel.setBackground(Color.black);
        winPanel.setBounds(0, 0, WIDTH, HEIGHT);
        winPanel.setLayout(new GridBagLayout());
        winPanel.setVisible(true);
        JLabel winText = new JLabel("You win!!!");
        winText.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        winText.setForeground(Color.white);

        gameFrame.add(winPanel);
        winPanel.add(winText);

        gameFrame.revalidate();
        gameFrame.repaint();
    }
}
