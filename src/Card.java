public class Card {

    private boolean faceUp;
    final private Suit suit;
    final private int faceValue;

    public Card(Suit suit, int faceValue) {
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

    public String faceValueToString() throws IllegalStateException {
        if (this.faceValue >= 2 && this.faceValue <= 10) return String.valueOf(this.faceValue);
        return switch (this.faceValue) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> throw new IllegalStateException("Face value is invalid: " + this.faceValue);
        };
    }

    public char faceValueToChar() throws IllegalStateException {
        if (this.faceValue >= 2 && this.faceValue <= 10) return Integer.toString(this.faceValue).charAt(0);
        return switch (this.faceValue) {
            case 1 -> 'A';
            case 11 -> 'J';
            case 12 -> 'Q';
            case 13 -> 'K';
            default -> throw new IllegalStateException("Face value is invalid: " + this.faceValue);
        };
    }

    public String toString() {
        return this.faceValueToString() + " of " + this.suit;
    }

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
