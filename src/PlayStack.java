/**
 * Subclass of CardStack that represents a stack in the playSpace
 * @author mhowell9
 */

public class PlayStack extends CardStack{

    public PlayStack(int x, int y) {
        super(x, y);
    }

    @Override
    public CardPanel pop() {
        CardPanel card = super.pop();
        // flip a card that is face down if it is on top
        if (!isEmpty() && !this.peek().getCard().isFaceUp()) this.peek().flip();
        return card;
    }

    @Override
    public void push(CardPanel card) {
        // should never throw since it is checked else where, but you never know
        if (!super.isEmpty() && !super.peek().getCard().canStack(card.getCard()))
            throw new CardUnstackableException(card.getCard(), super.peek().getCard());
        super.push(card);
    }
}
