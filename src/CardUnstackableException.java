/**
 * @author mhowell9
 */

public class CardUnstackableException extends RuntimeException {
    public CardUnstackableException(Card moved, Card stack) {
        super(moved + " was unable to be put on top of " + stack);
    }
}