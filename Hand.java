import java.util.ArrayList;

public class Hand {
    private static Deck deck;
    private ArrayList<Card> hand;
    private boolean isVisible;
    public Hand(boolean visible) {
        hand = new ArrayList<>();
        isVisible = visible;
        for (int i = 0; i < 5; i++) {
            hand.add(deck.drawCard());
            if (!isVisible) {
                makeHandInvisible();
            }
        }
    }

    public static void createDeck() {
        deck = new Deck();
        deck.shuffleDeck();
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    public static Deck getDeck(){
        return deck;
    }

    public void makeHandInvisible() {
        for (int i = 0; i < hand.size(); i++) {
            hand.get(i).setFileName("card_back.png");
            hand.get(i).setImage(hand.get(i).readImage());
        }
    }

    public void drawCard() {
        if (!deck.getDeck().isEmpty()) {
            hand.add(deck.drawCard());
            if (!isVisible) {
                makeHandInvisible();
            }
        }
    }
}
