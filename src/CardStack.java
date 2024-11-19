import java.awt.*;

public class CardStack {

    public static final int OFFSET = 25;
    private CardPanel[] cards;
    private int top;
    private Point anchor;
    private static final int SIZE = 13;

    public CardStack(int x, int y) {
        this(new Point(x, y));
    }

    public CardStack(Point point) {
        this.cards = new CardPanel[SIZE];
        this.top = 0;
        this.anchor = point;
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public CardPanel peek() {
        return this.cards[this.top - 1];
    }

    public CardPanel pop() {
        this.top--;
        CardPanel card = this.cards[this.top];
        this.cards[this.top] = null;
        return card;
    }

    public void push(CardPanel card) {
        this.cards[this.top] = card;
        card.setStack(this);
        card.setLocation(anchor.x, anchor.y + (top * OFFSET));
        this.top++;
    }

    public static CardPanel[] getCardsOnTop(CardPanel card) {
        CardStack cardStack = card.getStack();
        if (cardStack == null) return null;
        if (card.isStackTop()) return null;
        int cardIndex = 0;
        for (int i = 0; i < cardStack.top; i++) {
            if (cardStack.cards[i] == card) {cardIndex = i; break;}
        }
        CardPanel[] returnArr = new CardPanel[cardStack.top - cardIndex];
        int newArrIndex = 0;
        for (int i = cardIndex; i < cardStack.top; i++) {
            returnArr[newArrIndex] = cardStack.cards[i];
            newArrIndex++;
        }
        return returnArr;
    }

    public void setAnchor(Point point) {
        this.anchor = point;
    }

    public void setAnchor(int x, int y) {
        this.anchor = new Point(x, y);
    }

    public void redrawStack() {
        if (top == 0) return;
        for (int i = 0; i < top; i++) {
            this.cards[i].setLocation(anchor.x, anchor.y + (i * OFFSET));
        }
    }
}