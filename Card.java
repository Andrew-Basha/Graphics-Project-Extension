import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card {
    private String suit, value, fileName;
    private BufferedImage image;
    private Rectangle hitbox;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        fileName = "card_" + suit + "_" + value + ".png";
        image = readImage();
        hitbox = new Rectangle(-10,-10,image.getWidth(),image.getHeight());
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String toString() {
        return suit + " " + value;
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage readImage() {
        try {
            BufferedImage image;
            image = ImageIO.read(new File("images/" + fileName));
            return image;
        }
        catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public void reveal() {
        fileName = "card_" + suit + "_" + value + ".png";
        image = readImage();
    }

    public void hide() {
        fileName = "card_back.png";
        image = readImage();
    }
}
