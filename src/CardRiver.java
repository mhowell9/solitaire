import java.awt.*;

public class CardRiver extends CardStack {

    private static final int POS_X = 20;
    private static final int POS_Y = 150;

    public CardRiver() {
        super(new Point(POS_X, POS_Y), 24);
    }

    @Override
    public void push(CardPanel card) {
        super.push(card);
        redrawStack();
    }

    @Override
    public CardPanel peek() {
        if (this.isEmpty()) return null;
        return super.peek();
    }

    public void pushToBank() {
        while (!this.isEmpty()) {
            GamePanel.cardBank.push(this.pop());
        }
    }

    @Override
    public void redrawStack() {
        if (this.top < 4) return;
        for (int i = 0; i < (top - 2); i++) {
            cards[i].setLocation(POS_X, POS_Y);
        }
        GamePanel.playSpace.moveToFront(cards[top - 3]);
        cards[top - 2].setLocation(POS_X, POS_Y + OFFSET);
        GamePanel.playSpace.moveToFront(cards[top - 2]);
        cards[top - 1].setLocation(POS_X, POS_Y + (OFFSET * 2));
        GamePanel.playSpace.moveToFront(cards[top - 1]);
    }
}
