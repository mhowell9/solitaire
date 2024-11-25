import java.awt.*;

/**
 * Represents the pile of cards that cards are drawn from in solitaire
 * @author mhowell9
 */

public class CardBank extends CardStack {

    private static final int POS_X = 20;
    private static final int POS_Y = 20;

    public CardBank() {
        super(new Point(POS_X, POS_Y), 24);
    }

    @Override
    public void push(CardPanel card) {
        super.push(card);
        card.setLocation(POS_X, POS_Y);
    }

    @Override
    public void redrawStack() {
        if (top == 0) return;
        for (int i = 0; i < top; i++) {
            this.cards[i].setLocation(POS_X, POS_Y);
        }
    }
}
