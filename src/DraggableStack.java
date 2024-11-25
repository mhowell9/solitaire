import java.awt.*;

/**
 * Helper class to allow a stack of cards to be moved together
 * @author mhowell9
 */

public class DraggableStack {

    public static void moveCardsTogether(CardPanel[] cards) {
        // get position of first card, then set location of other cards with offset
        Point anchor = cards[0].getLocation();
        int i = 1;
        while (i < cards.length && cards[i] != null) {
            cards[i].setLocation(anchor.x, anchor.y + (i * CardStack.OFFSET));
            i++;
        }
    }
}
