import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class CardPanel extends DraggablePanel {

    final private Card card;
    final public static int SIZE_X = 65;
    final public static int SIZE_Y = 100;
    private Point origin;
    private CardStack stack;
    private CardPanel[] movingCards;

    final private static ImageIcon CARD_BACK = new ImageIcon("assets/card_back.png");
    final private static ImageIcon CARD_BASE = new ImageIcon("assets/card_base.png");
    final private static ImageIcon CLUBS = new ImageIcon("assets/CLUBS.png");
    final private static ImageIcon DIAMONDS = new ImageIcon("assets/DIAMONDS.png");
    final private static ImageIcon HEARTS = new ImageIcon("assets/HEARTS.png");
    final private static ImageIcon SPADES = new ImageIcon("assets/SPADES.png");
    final private static ImageIcon CLUBS_SMALL = new ImageIcon("assets/CLUBS_SMALL.png");
    final private static ImageIcon DIAMONDS_SMALL = new ImageIcon("assets/DIAMONDS_SMALL.png");
    final private static ImageIcon HEARTS_SMALL = new ImageIcon("assets/HEARTS_SMALL.png");
    final private static ImageIcon SPADES_SMALL = new ImageIcon("assets/SPADES_SMALL.png");

    public CardPanel(Suit suit, int faceValue) {
        this(suit, faceValue, 0, 0);
    }

    public CardPanel(Suit suit, int faceValue, Point point) {
        this(suit, faceValue, point.x, point.y);
    }

    public CardPanel(Suit suit, int faceValue, int posX, int posY) {
        this.card = new Card(suit, faceValue);
        this.stack = null;
        this.setLayout(null);
        this.setBounds(posX, posY, SIZE_X, SIZE_Y);
        this.redraw();
        this.setOpaque(false);
        this.setVisible(true);
        GamePanel.playSpace.add(this);
    }

    public Card getCard() {
        return this.card;
    }

    public boolean isStackTop() {
        if (this.stack == null) return true;
        return this.stack.peek() == this;
    }

    public CardStack getStack() {
        return this.stack;
    }

    public void setStack(CardStack stack) {
        this.stack = stack;
    }

    public void redraw() {
        this.removeAll();
        ImageIcon image = (card.isFaceUp()) ? CARD_BASE : CARD_BACK;
        JLabel base = new JLabel(image);
        base.setBounds(0, 0, SIZE_X, SIZE_Y);
        base.setOpaque(false);

        if (card.isFaceUp()) {
            ImageIcon suitIcon, smallIcon;
            switch (card.getSuit()) {
                case CLUBS -> {
                    suitIcon = CLUBS;
                    smallIcon = CLUBS_SMALL;
                }
                case HEARTS -> {
                    suitIcon = HEARTS;
                    smallIcon = HEARTS_SMALL;
                }
                case SPADES -> {
                    suitIcon = SPADES;
                    smallIcon = SPADES_SMALL;
                }
                case DIAMONDS -> {
                    suitIcon = DIAMONDS;
                    smallIcon = DIAMONDS_SMALL;
                }
                case null, default -> throw new IllegalStateException("");
            }
            JLabel faceValue = new JLabel(String.valueOf(card.faceValueToChar()));
            faceValue.setBounds(4, 0, SIZE_X - 4, 20);
            faceValue.setFont(new Font(faceValue.getFont().getName(), Font.BOLD, 20));
            if (card.getSuit() == Suit.DIAMONDS || card.getSuit() == Suit.HEARTS) faceValue.setForeground(Color.RED);
            faceValue.setVisible(true);
            this.add(faceValue);

            JLabel small = new JLabel(smallIcon);
            small.setBounds(SIZE_X - 22, 2 , 20, 20);
            small.setVisible(true);
            this.add(small);

            JLabel suit = new JLabel(suitIcon);
            suit.setBounds(0, SIZE_Y - 65, 65, 65);
            suit.setVisible(true);
            this.add(suit);
        }
        this.add(base);
    }

    public void flip() {
        card.flip();
        redraw();
    }

    public String toString() {
        return this.card.toString();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.stack == GamePanel.cardBank) return;
        if (this.stack == GamePanel.cardRiver && this != GamePanel.cardRiver.peek()) return;
        if (this.stack.getClass() == PlayStack.class && !this.card.isFaceUp()) return;
        super.mouseDragged(e);
        if (this.movingCards != null) DraggableStack.moveCardsTogether(this.movingCards);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.origin = getLocation();
        super.mousePressed(e);
        if (!this.isStackTop()) {
            this.movingCards = CardStack.getCardsOnTop(this);
            if (this.movingCards == null) return;
            for (CardPanel cardPanel : this.movingCards) {
                GamePanel.playSpace.moveToFront(cardPanel);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.movingCards = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.stack.getClass() != CardBank.class) return;
        GamePanel.cardRiver.push(this);
        this.flip();
    }
}
