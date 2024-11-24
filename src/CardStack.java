import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class CardStack {

    public static final int OFFSET = 25;
    protected CardPanel[] cards;
    protected int top;
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

    public CardStack(Point point, int size) {
        this.cards = new CardPanel[size];
        this.top = 0;
        this.anchor = point;
    }

    public CardStack(CardPanel[] cards, Point point) {
        this(point, 52);
        this.push(cards);
    }

    public boolean isEmpty() {
        return this.top == 0;
    }

    public CardPanel peek() {
        if (isEmpty()) throw new CardStackEmptyException("Attempt to access top of empty stack");
        return this.cards[this.top - 1];
    }

    public CardPanel pop() {
        if (isEmpty()) throw new CardStackEmptyException("Attempt to remove item from empty stack");
        this.top--;
        CardPanel card = this.cards[this.top];
        this.cards[this.top] = null;
        redrawStack();
        return card;
    }

    public void push(CardPanel card) {
        this.cards[this.top] = card;
        card.setStack(this);
        redrawStack();
        GamePanel.playSpace.moveToFront(card);
        this.top++;
    }

    public final void forcePush(CardPanel card) {
        this.cards[this.top] = card;
        card.setStack(this);
        card.setLocation(anchor.x, anchor.y + (top * OFFSET));
        GamePanel.playSpace.moveToFront(card);
        this.top++;
    }

    public void push(CardPanel[] cards) {
        for (CardPanel card : cards) {
            push(card);
        }
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

    public static CardStack generateRandomDeck() {
        ArrayList<CardPanel> cardDeck = new ArrayList<CardPanel>();
        for (int i = 0; i < 52; i++) {
            cardDeck.add(new CardPanel(Suit.values()[i / 13], (i % 13) + 1));
        }
        Collections.shuffle(cardDeck);
        return new CardStack(cardDeck.toArray(new CardPanel[52]), new Point(0, 1000));
    }

    public void setAnchor(Point point) {
        this.anchor = point;
    }

    public void setAnchor(int x, int y) {
        this.anchor = new Point(x, y);
    }

    public Point getAnchor() {
        return this.anchor;
    }

    public void redrawStack() {
        if (top == 0) return;
        for (int i = 0; i < top; i++) {
            this.cards[i].setLocation(anchor.x, anchor.y + (i * OFFSET));
        }
    }

    @Override
    public String toString() {
        return "CardStack at " + anchor;
    }
}