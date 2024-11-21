public class SuitStack extends CardStack {

    private final Suit suit;

    public SuitStack(int x, int y, Suit suit) {
        super(x, y);
        this.suit = suit;
    }

    @Override
    public void push(CardPanel card) {
        if (card.getCard().getSuit() != this.suit)
            throw new CardUnstackableException(card + " does not match the suit " + this.suit);
        super.push(card);
    }
}
