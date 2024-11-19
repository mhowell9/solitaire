import java.awt.*;

public class PlayStack extends CardStack{

    public PlayStack(int x, int y) {
        this(new Point(x, y));
    }

    public PlayStack(Point point) {
        super(point);
    }

    @Override
    public void push(CardPanel card) {
        if (!super.peek().getCard().canStack(card.getCard()))
            throw new CardUnstackableException(card.getCard(), super.peek().getCard());
        super.push(card);
    }
}
