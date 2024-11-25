/**
 * Class that holds the card data of a CardPanel for solitaire.
 * faceValue is represented by an integer from 1-13 following the order ace-king respectively.
 * Cards are by default face down.
 * @author mhowell9
 */

public class Card {

    private boolean faceUp;
    final private Suit suit;
    final private int faceValue;

    public Card(Suit suit, int faceValue) {
        if (faceValue < 1 || faceValue > 13) throw new IllegalArgumentException("face value must be between 1 and 13");
        this.suit = suit;
        this.faceValue = faceValue;
        this.faceUp = false;
    }

    public void flip() {
        this.faceUp = !this.faceUp;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public int getFaceValue() {
        return this.faceValue;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public String faceValueToString() {
        if (this.faceValue >= 2 && this.faceValue <= 10) return String.valueOf(this.faceValue);
        return switch (this.faceValue) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> throw new IllegalStateException("Face value is invalid: " + this.faceValue);
        };
    }

    public String faceValueToCardLabel() {
        if (this.faceValue >= 2 && this.faceValue <= 10) return Integer.toString(this.faceValue);
        return switch (this.faceValue) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> throw new IllegalStateException("Face value is invalid: " + this.faceValue);
        };
    }

    public String toString() {
        return this.faceValueToString() + " of " + this.suit;
    }

    /**
     * Checks if a card can be stacked on top of another for the purposes of PlayStack logic
     * @param other
     *      the card you are trying to stack a card on top of
     * @return
     *      true if the card is stackable
     */
    public boolean canStack(Card other) {
        return switch (this.suit) {
            case CLUBS, SPADES -> {
                if (other.suit == Suit.CLUBS || other.suit == Suit.SPADES) yield false;
                yield this.faceValue - 1 == other.faceValue;
            }
            case HEARTS, DIAMONDS -> {
                if (other.suit == Suit.HEARTS || other.suit == Suit.DIAMONDS) yield false;
                yield this.faceValue - 1 == other.faceValue;
            }
        };
    }
}
