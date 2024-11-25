/**
 * Subclass of CardStack that is used for stacking cards in order to win the solitaire game
 * @author mhowell9
 */

public class SuitStack extends CardStack {

    private final Suit suit;
    private final int POS_X;
    private final int POS_Y;

    public SuitStack(int x, int y, Suit suit) {
        super(x, y);
        this.POS_X = x;
        this.POS_Y = y;
        this.suit = suit;
    }

    @Override
    public void push(CardPanel card) {
        super.push(card);
        if (top == 13) GamePanel.checkForWin();
    }

    /**
     * helper method that checks if a card is able to be stacked on the SuitStack
     * @param card
     *      card to check
     * @return
     *      true if card is the same suit as stack and is the next faceValue in the sequence
     */
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
            this.cards[i].setLocation(POS_X, POS_Y);
        }
    }
}
