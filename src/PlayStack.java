import java.awt.*;

public class PlayStack extends CardStack{

    public PlayStack(int x, int y) {
        super(x, y);
    }

    public PlayStack(Point point) {
        super(point);
    }

    @Override
    public CardPanel pop() {
        CardPanel card = super.pop();
        if (!isEmpty() && !this.peek().getCard().isFaceUp()) this.peek().flip();
        return card;
    }

    @Override
    public void push(CardPanel card) {
        if (!super.isEmpty() && !super.peek().getCard().canStack(card.getCard()))
            throw new CardUnstackableException(card.getCard(), super.peek().getCard());
        super.push(card);
    }
}
