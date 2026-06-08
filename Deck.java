import java.awt.*;
import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    public Deck() {
        deck.add(new Card("spades","A"));
        deck.add(new Card("hearts","A"));
        deck.add(new Card("clubs","A"));
        deck.add(new Card("diamonds","A"));
        for (int i = 2; i < 10; i++) {
            deck.add(new Card("spades","0" + i));
            deck.add(new Card("hearts","0" + i));
            deck.add(new Card("clubs","0" + i));
            deck.add(new Card("diamonds","0" + i));
        }
        deck.add(new Card("spades","10"));
        deck.add(new Card("hearts","10"));
        deck.add(new Card("clubs","10"));
        deck.add(new Card("diamonds","10"));
        deck.add(new Card("spades","J"));
        deck.add(new Card("hearts","J"));
        deck.add(new Card("clubs","J"));
        deck.add(new Card("diamonds","J"));
        deck.add(new Card("spades","Q"));
        deck.add(new Card("hearts","Q"));
        deck.add(new Card("clubs","Q"));
        deck.add(new Card("diamonds","Q"));
        deck.add(new Card("spades","K"));
        deck.add(new Card("hearts","K"));
        deck.add(new Card("clubs","K"));
        deck.add(new Card("diamonds","K"));
        deck.add(new Card("joker","black"));
        deck.add(new Card("joker","red"));
    }
    public void resetHitbox(Card card) {
        Rectangle hitbox = new Rectangle(-10,-10, card.getImage().getWidth(), card.getImage().getHeight());
        card.setHitbox(hitbox);
    }
    public void shuffleDeck() {
        int originalDeckSize = deck.size();
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        for (int i = 0; i < originalDeckSize; i++) {
            int cardNum = (int) (Math.random() * deck.size());
            shuffledDeck.add(deck.get(cardNum));
            deck.remove(cardNum);
        }
        deck = shuffledDeck;
    }
    public ArrayList<Card> getDeck() {
        return deck;
    }
    public Card drawCard() {
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public void placeOnBottomDeck(Card card) {
        deck.add(card);
        resetHitbox(card);
    }

    public Card topCard() {
        return deck.get(0);
    }
}
