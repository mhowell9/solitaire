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

    public String toString() {
        return this.faceValueToString() + " of " + this.suit;
    }

    public boolean canStack(Card card) {
        switch (this.suit) {
            case CLUBS, SPADES:
                if (card.suit == Suit.CLUBS || card.suit == Suit.SPADES) return false;
            case HEARTS, DIAMONDS:
                if (card.suit == Suit.HEARTS || card.suit == Suit.DIAMONDS) return false;
        }
        return this.faceValue == card.faceValue - 1;
    }
}
