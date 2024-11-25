import java.awt.*;

/**
 * Represents the stack of cards from the card bank that are "in play"
 * @author mhowell9
 */

public class CardRiver extends CardStack {

    private static final int POS_X = 20;
    private static final int POS_Y = 150;

    public CardRiver() {
        super(new Point(POS_X, POS_Y), 24);
    }

    @Override
    public void push(CardPanel card) {
        super.push(card);
    }

    @Override
    public CardPanel peek() {
        if (this.isEmpty()) return null;
        return super.peek();
    }

    public void pushToBank() {
        while (!this.isEmpty()) {
            GamePanel.cardBank.push(this.pop());
            GamePanel.cardBank.peek().flip();
        }
    }

    /**
     * Because of the unique conditions of the visual representation of this stack, it is drawn
     * a few different ways. The main idea is only the top 3 cards are displayed. The stack is
     * always shown at the anchor, so the offset for the top is different depending on how many
     * cards are in the stack
     */
    @Override
    public void redrawStack() {
        // Clean up if I get the chance
        switch (top) {
            case (0): return;
            case (1): {cards[0].setLocation(POS_X, POS_Y); GamePanel.playSpace.moveToFront(cards[0]); return;}
            case (2): {
                cards[0].setLocation(POS_X, POS_Y); GamePanel.playSpace.moveToFront(cards[0]);
                cards[1].setLocation(POS_X, POS_Y + OFFSET); GamePanel.playSpace.moveToFront(cards[1]);
                return;
            }
            default: {
                for (int i = 0; i < (top - 2); i++) {
                    cards[i].setLocation(POS_X, POS_Y);
                }
                GamePanel.playSpace.moveToFront(cards[top - 3]);
                cards[top - 2].setLocation(POS_X, POS_Y + OFFSET); GamePanel.playSpace.moveToFront(cards[top - 2]);
                cards[top - 1].setLocation(POS_X, POS_Y + (OFFSET * 2)); GamePanel.playSpace.moveToFront(cards[top - 1]);
            }
        }
    }
}
