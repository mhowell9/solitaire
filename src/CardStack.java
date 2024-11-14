public class CardStack {

    private Card[] cards;
    private int top;

    public CardStack() {
        this.cards = new Card[13];
        this.top = 0;
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public Card peek() {
        return this.cards[this.top - 1];
    }

    public Card pop() {
        this.top--;
        Card card = this.cards[this.top];
        this.cards[this.top] = null;
        return card;
    }

    public void push(Card card) {
        this.cards[this.top] = card;
        this.top++;
    }
}