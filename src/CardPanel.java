import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

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
            JLabel faceValue = new JLabel(card.faceValueToCardLabel());
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
        this.revalidate();
        this.repaint();
    }

    public void flip() {
        card.flip();
        redraw();
    }

    public String toString() {
        return this.card.toString();
    }

    private static boolean pointInsideStackBounds(Point point, CardPanel card) {
        if (card.getX() > point.x || card.getX() + SIZE_X < point.x) return false;
        return (card.getY() <= point.y || card.getY() + SIZE_Y >= point.y);
    }

    private static boolean pointInsideStackBounds(Point point, Point pointStack) {
        if (pointStack.x > point.x || pointStack.x + SIZE_X < point.x) return false;
        return (pointStack.y <= point.y || pointStack.y + SIZE_Y >= point.y);
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
        int release_x = this.getX() + e.getX(), release_y = this.getY() + e.getY();
        if (movingCards == null && release_x >= 820 && release_x <= 884) {
            if (release_y >= 20 && release_y <= 119) {
                if (GamePanel.heartStack.canStackCard(this)) GamePanel.heartStack.push(getStack().pop());
            }
            if (release_y >= 150 && release_y <= 249) {
                if (GamePanel.diamondStack.canStackCard(this)) GamePanel.diamondStack.push(getStack().pop());
            }
            if (release_y >= 280 && release_y <= 379) {
                if (GamePanel.clubStack.canStackCard(this)) GamePanel.clubStack.push(getStack().pop());
            }
            if (release_y >= 410 && release_y <= 509) {
                if (GamePanel.spadeStack.canStackCard(this)) GamePanel.spadeStack.push(getStack().pop());
            }
        }
        for (int i = 0; i < 7; i++) {
            if (GamePanel.playStacks[i] == this.stack) continue;
            if (GamePanel.playStacks[i].isEmpty()) {
                if (card.getFaceValue() != 13) continue;
                if (!pointInsideStackBounds(new Point(release_x, release_y), GamePanel.playStacks[i].getAnchor()))
                    continue;
            } else {
                if (!pointInsideStackBounds(new Point(release_x, release_y), GamePanel.playStacks[i].peek())) continue;
                if (!GamePanel.playStacks[i].peek().card.canStack(this.card)) continue;
            }

            if (movingCards == null) {
                GamePanel.playStacks[i].push(getStack().pop());
            } else {
                CardStack originStack = getStack();
                for (CardPanel card : movingCards) {
                    originStack.pop();
                    GamePanel.playStacks[i].push(card);
                }
            }
        }
        if (this.stack != GamePanel.cardBank) this.stack.redrawStack();
        this.movingCards = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.stack.getClass() != CardBank.class) return;
        GamePanel.cardRiver.push(GamePanel.cardBank.pop());
        this.flip();
        this.stack.redrawStack();
    }
}
