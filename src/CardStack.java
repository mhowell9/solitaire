import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class using a stack data structure to store (and display) CardPanels.
 * Default maximum cards in the stack is 13
 * @author mhowell9
 */

public class CardStack {

    public static final int OFFSET = 25;
    protected CardPanel[] cards;
    protected int top;
    private final Point anchor; // position of the CardStack in a component
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

    /**
     * creates a new CardStack using an array of CardPanels with a size of 52.
     * This is mainly used just when initializing the deck of cards
     * @param cards
     *      array of cards
     * @param point
     *      anchor point for the stack
     */
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
        GamePanel.playSpace.moveToFront(card);
        this.top++;
        redrawStack();
    }

    /**
     * identical to push() but doesn't get overwritten by subclasses.
     * main purpose is for initializing the PlayStacks at the start of the game
     * @param card
     *      card to be pushed to the stack
     */
    public final void forcePush(CardPanel card) {
        this.cards[this.top] = card;
        card.setStack(this);
        GamePanel.playSpace.moveToFront(card);
        this.top++;
        redrawStack();
    }

    /**
     * Push an array of CardPanels onto a stack, calls push() for each CardPanel in array
     * @param cards
     *      array of CardPanels
     */
    public void push(CardPanel[] cards) {
        for (CardPanel card : cards) {
            push(card);
        }
    }

    /**
     * returns an array of CardPanels that is on top of a CardPanel in a stack.
     * Non-destructive
     * @param card
     *      card to be checked
     * @return
     *      array of CardPanels on top of card, inclusive. null if there are no cards on top
     */
    public static CardPanel[] getCardsOnTop(CardPanel card) {
        CardStack cardStack = card.getStack();

        // return early if conditions are not satisfied
        if (cardStack == null) return null;
        if (card.isStackTop()) return null;

        int cardIndex = 0;
        // find index of card
        for (int i = 0; i < cardStack.top; i++) {
            if (cardStack.cards[i] == card) {cardIndex = i; break;}
        }

        // always returns an array of the exact size needed
        CardPanel[] returnArr = new CardPanel[cardStack.top - cardIndex];
        int newArrIndex = 0;

        // copy the cards on top (inclusive) to a new array
        for (int i = cardIndex; i < cardStack.top; i++) {
            returnArr[newArrIndex] = cardStack.cards[i];
            newArrIndex++;
        }
        return returnArr;
    }

    /**
     * Method that creates a CardStack of a full shuffled deck.
     * The CardStack is created out of bounds, so they do not need to be rendered
     * @return
     *      CardStack of size 52 containing a full shuffled deck
     */
    public static CardStack generateRandomDeck() {
        ArrayList<CardPanel> cardDeck = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            cardDeck.add(new CardPanel(Suit.values()[i / 13], (i % 13) + 1));
        }
        Collections.shuffle(cardDeck);
        return new CardStack(cardDeck.toArray(new CardPanel[52]), new Point(0, 1000));
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