public class SuitStack extends CardStack {

    private final Suit suit;
    private final int x;
    private final int y;

    public SuitStack(int x, int y, Suit suit) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.suit = suit;
    }

    @Override
    public void push(CardPanel card) {
        super.push(card);
        if (top == 13) GamePanel.checkForWin();
    }

    public boolean canStackCard(CardPanel card) {
        if (card.getCard().getSuit() != this.suit) return false;
        if (isEmpty() && card.getCard().getFaceValue() == 1) return true;
        if (isEmpty()) return false;
        return (card.getCard().getFaceValue() == peek().getCard().getFaceValue() + 1);
    }

    @Override
    public void redrawStack() {
        if (top == 0) return;
        for (int i = 0; i < top; i++) {
            this.cards[i].setLocation(x, y);
        }
    }
}
