import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A type of DraggablePanel that represents a card in the solitaire game.
 * Automatically adds new CardPanels to playSpace.
 * @author mhowell9
 */

public class CardPanel extends DraggablePanel {

    final private Card card;
    final public static int SIZE_X = 65;
    final public static int SIZE_Y = 100;
    private CardStack stack;
    private CardPanel[] movingCards;

    // ASSETS

    final private static ImageIcon CARD_BACK = new ImageIcon(CardPanel.class.getResource("assets/card_back.png"));
    final private static ImageIcon CARD_BASE = new ImageIcon(CardPanel.class.getResource("assets/card_base.png"));
    final private static ImageIcon CLUBS = new ImageIcon(CardPanel.class.getResource("assets/CLUBS.png"));
    final private static ImageIcon DIAMONDS = new ImageIcon(CardPanel.class.getResource("assets/DIAMONDS.png"));
    final private static ImageIcon HEARTS = new ImageIcon(CardPanel.class.getResource("assets/HEARTS.png"));
    final private static ImageIcon SPADES = new ImageIcon(CardPanel.class.getResource("assets/SPADES.png"));
    final private static ImageIcon CLUBS_SMALL = new ImageIcon(CardPanel.class.getResource("assets/CLUBS_SMALL.png"));
    final private static ImageIcon DIAMONDS_SMALL = new ImageIcon(CardPanel.class.getResource("assets/DIAMONDS_SMALL.png"));
    final private static ImageIcon HEARTS_SMALL = new ImageIcon(CardPanel.class.getResource("assets/HEARTS_SMALL.png"));
    final private static ImageIcon SPADES_SMALL = new ImageIcon(CardPanel.class.getResource("assets/SPADES_SMALL.png"));

    public CardPanel(Suit suit, int faceValue) {
        this(suit, faceValue, 0, 0);
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
        // no need to draw if out of bounds
        if (this.getX() > GamePanel.WIDTH || this.getY() > GamePanel.HEIGHT) return;

        this.removeAll();
        ImageIcon baseImage = (card.isFaceUp()) ? CARD_BASE : CARD_BACK;
        JLabel base = new JLabel(baseImage);
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

    /**
     * helper method to check if a point is inside a card's bounds
     * @param point
     *      point to be checked
     * @param card
     *      the card
     * @return
     *      true if the point is inside the card's bounds
     */
    private static boolean pointInsideStackBounds(Point point, CardPanel card) {
        if (card.getX() > point.x || card.getX() + SIZE_X < point.x) return false;
        return (card.getY() <= point.y || card.getY() + SIZE_Y >= point.y);
    }

    /**
     * checks if a point is inside the bounds of a cardStack.
     * the main purpose of this is if the stack is empty
     * @param point
     *      point to be checked
     * @param pointStack
     *      anchor point of the stack
     * @return
     *      true if point is within the stacks bounds (same size as CardPanel)
     */
    private static boolean pointInsideStackBounds(Point point, Point pointStack) {
        if (pointStack.x > point.x || pointStack.x + SIZE_X < point.x) return false;
        return (pointStack.y <= point.y || pointStack.y + SIZE_Y >= point.y);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // ignore inputs on cardBank and cards not on top of cardRiver
        if (this.stack == GamePanel.cardBank) return;
        if (this.stack == GamePanel.cardRiver && this != GamePanel.cardRiver.peek()) return;

        // only allow movement of cards that are face up in PlayStacks
        if (this.stack.getClass() == PlayStack.class && !this.card.isFaceUp()) return;
        super.mouseDragged(e);

        if (this.movingCards != null) DraggableStack.moveCardsTogether(this.movingCards);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);

        // Move cards together if necessary (card pressed is not top of stack)
        if (!this.isStackTop()) {
            this.movingCards = CardStack.getCardsOnTop(this);
            if (this.movingCards == null) return;

            // Bring all the cards to the front when moving a stack
            for (CardPanel cardPanel : this.movingCards) {
                GamePanel.playSpace.moveToFront(cardPanel);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Cards are only popped from a stack if it is pushed onto another stack
        int release_x = this.getX() + e.getX(), release_y = this.getY() + e.getY();

        // Check if card is within bounds of SuitStack and is stackable
        // Should clean this up at some point
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

        // Check if card is dropped on top of play stack
        for (int i = 0; i < 7; i++) {
            if (GamePanel.playStacks[i] == this.stack) continue;

            // Allow kings to be put on top of empty stack
            // Break the loop early if conditions are not met
            if (GamePanel.playStacks[i].isEmpty()) {
                if (card.getFaceValue() != 13) continue;
                if (!pointInsideStackBounds(new Point(release_x, release_y), GamePanel.playStacks[i].getAnchor()))
                    continue;
            } else {
                if (!pointInsideStackBounds(new Point(release_x, release_y), GamePanel.playStacks[i].peek())) continue;
                if (!GamePanel.playStacks[i].peek().card.canStack(this.card)) continue;
            }

            if (movingCards == null) {
                // Singular card
                GamePanel.playStacks[i].push(getStack().pop());
            } else {
                // Stack of cards
                CardStack originStack = getStack();
                for (CardPanel card : movingCards) {
                    // Done in this way to keep order the same
                    originStack.pop();
                    GamePanel.playStacks[i].push(card);
                }
            }
        }
        stack.redrawStack();
        this.movingCards = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Used to get cards from the bank and add to the river
        if (this.stack.getClass() != CardBank.class) return;
        GamePanel.cardRiver.push(GamePanel.cardBank.pop());
        this.flip();
        this.stack.redrawStack();
    }
}
