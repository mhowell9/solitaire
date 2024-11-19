import java.awt.*;

public class DraggableStack {

    public static void moveCardsTogether(CardPanel[] cards) {
        Point anchor = cards[0].getLocation();
        int i = 1;
        while (i < cards.length && cards[i] != null) {
            cards[i].setLocation(anchor.x, anchor.y + (i * CardStack.OFFSET));
            i++;
        }
    }
}
