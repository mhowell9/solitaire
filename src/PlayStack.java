public class PlayStack extends CardStack{

    public PlayStack() {
        super();
    }

    @Override
    public void push(Card card) {
        if (!super.peek().canStack(card)) throw new CardUnstackableException(card, super.peek());
        super.push(card);
    }
}
