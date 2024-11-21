import java.awt.*;

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
}
