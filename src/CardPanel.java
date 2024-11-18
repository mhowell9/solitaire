public class CardPanel extends DraggablePanel {

    final private Card card;

    public CardPanel(Suit suit, int faceValue) {
        this.card = new Card(suit, faceValue);
    }
}
