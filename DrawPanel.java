import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JPanel;
import java.io.File;
import java.awt.*;

class DrawPanel extends JPanel implements MouseListener {

    private Hand playerHand;
    private Hand enemyHand;
    private Card currentCard;
    private boolean playerTurn;
    Rectangle button1 = new Rectangle(235, 262, 100, 40);

    public DrawPanel() {
        Hand.createDeck();
        playerHand = new Hand(true);
        enemyHand = new Hand(false);
        currentCard = Hand.getDeck().drawCard();
        if (Math.random() >= .5) {
            playerTurn = true;
        } else {
            playerTurn = false;
        }
        this.addMouseListener(this);
    }

    static boolean gameOver = false;

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 30;
        for (int i = 0; i < playerHand.getHand().size() || i < enemyHand.getHand().size(); i += 2) {
            if (i < playerHand.getHand().size()) {
                g.drawImage(playerHand.getHand().get(i).getImage(), x, 400, null);
                Rectangle hitbox = new Rectangle(x, 400, playerHand.getHand().get(i).getImage().getWidth(), playerHand.getHand().get(i).getImage().getHeight());
                playerHand.getHand().get(i).setHitbox(hitbox);
                g.drawRect(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
            }
            if (i + 1 < playerHand.getHand().size()) {
                g.drawImage(playerHand.getHand().get(i + 1).getImage(), x, 405 + playerHand.getHand().get(i).getImage().getHeight(), null);
                Rectangle hitbox = new Rectangle(x, 405 + playerHand.getHand().get(i).getImage().getHeight(), playerHand.getHand().get(i + 1).getImage().getWidth(), playerHand.getHand().get(i + 1).getImage().getHeight());
                playerHand.getHand().get(i + 1).setHitbox(hitbox);
                g.drawRect(hitbox.x,hitbox.y,hitbox.width,hitbox.height);
            }
            if (i < enemyHand.getHand().size()) {
                g.drawImage(enemyHand.getHand().get(i).getImage(), x, 100, null);
            }
            if (i + 1 < enemyHand.getHand().size()) {
                g.drawImage(enemyHand.getHand().get(i + 1).getImage(), x, 95 - enemyHand.getHand().get(i).getImage().getHeight(), null);
            }
            if (!playerHand.getHand().isEmpty()) {
                x += playerHand.getHand().getFirst().getImage().getWidth() + 5;
            } else if (!enemyHand.getHand().isEmpty()) {
                x += enemyHand.getHand().getFirst().getImage().getWidth() + 5;
            }
        }
        g.drawRect(button1.x, button1.y, button1.width, button1.height);
        g.drawString("Draw Card", 255, 287);
        if (playerTurn) {
            g.drawString("Player Turn", 350, 287);
        } else {
            g.drawString("Enemy Turn", 350, 287);
        }
        if (playerHand.getHand().isEmpty()) {
            g.drawString("Player Wins!", 120, 360);
            for (int i = 0; i < enemyHand.getHand().size(); i++) {
                enemyHand.getHand().get(i).reveal();
            }
        } else if (enemyHand.getHand().isEmpty()) {
            g.drawString("Enemy Wins!", 120, 210);
        }
        if (!Hand.getDeck().getDeck().isEmpty()) {
            try {
                g.drawImage(ImageIO.read(new File("images/card_back.png")), 50, 250, null);
            } catch (IOException e) {
                System.out.println("IOException e");
            }
        }
        g.drawImage(currentCard.getImage(), 140, 250, null);
    }

    public boolean playerPlay(int x) {
        if (x < playerHand.getHand().size() && x > -1) {
            Hand.getDeck().placeOnBottomDeck(currentCard);
            currentCard = playerHand.getHand().get(x);
            currentCard.reveal();
            playerHand.getHand().remove(x);
            return true;
        }
        return false;
    }

    public boolean enemyPlay(int x) {
        if (x < enemyHand.getHand().size() && x > -1) {
            Hand.getDeck().placeOnBottomDeck(currentCard);
            currentCard = enemyHand.getHand().get(x);
            currentCard.reveal();
            enemyHand.getHand().remove(x);
            return true;
        }
        return false;
    }

    public int playableCard(Hand hand) {
        for (int i = 0; i < hand.getHand().size(); i++) {
            if (hand.getHand().get(i).getSuit().equals(currentCard.getSuit()) || hand.getHand().get(i).getValue().equals(currentCard.getValue()) || hand.getHand().get(i).getSuit().equals("joker") || currentCard.getSuit().equals("joker")) {
                return i;
            }
        }
        return -1;
    }

    public int goodCard(Hand hand, Hand otherHand) {
        for (int i = 0; i < hand.getHand().size(); i++) {
            for (int j = 0; j < hand.getHand().size(); j++) {
                if ((hand.getHand().get(i).getSuit().equals(currentCard.getSuit()) && (hand.getHand().get(i).getSuit().equals(hand.getHand().get(j).getSuit()) && i != j)) || (hand.getHand().get(i).getValue().equals(currentCard.getValue()) && (hand.getHand().get(i).getValue().equals(hand.getHand().get(j).getValue()) && i != j))) {
                    return i;
                }
            }
        }
        for (int i = 0; i < hand.getHand().size(); i++) {
            if (hand.getHand().get(i).getSuit().equals(currentCard.getSuit()) || hand.getHand().get(i).getValue().equals(currentCard.getValue())) {
                return i;
            }
        }
        for (int i = 0; i < hand.getHand().size(); i++) {
            if ((hand.getHand().get(i).getSuit().equals("joker") && (((otherHand.getHand().size() > 2 && Math.random() >= .25)) || hand.getHand().size() == 1)) || currentCard.getSuit().equals("joker")) {
                return i;
            }
        }
        return -1;
    }

    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (playerTurn) {
                Point p = e.getPoint();
                if (button1.contains(p)) {
                    playerHand.drawCard();
                    playerTurn = !playerTurn;
                }
                for (int i = 0; i < playerHand.getHand().size(); i++) {
                    if (playerHand.getHand().get(i).getHitbox().contains(p)) {
                        if (playerHand.getHand().get(i).getSuit().equals(currentCard.getSuit()) || playerHand.getHand().get(i).getValue().equals(currentCard.getValue()) || playerHand.getHand().get(i).getSuit().equals("joker") || currentCard.getSuit().equals("joker")) {
                            playerPlay(i);
                        } else {
                            playerHand.drawCard();
                        }
                        playerTurn = !playerTurn;
                    }
                }
            } else {
                if (!enemyPlay(goodCard(enemyHand, playerHand))) {
                    enemyHand.drawCard();
                }
                playerTurn = !playerTurn;
            }
        }
        if (playerHand.getHand().isEmpty() || enemyHand.getHand().isEmpty()) {
            gameOver = true;
        }
    }

    public void mouseReleased(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
}