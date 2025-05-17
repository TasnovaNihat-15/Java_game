import java.awt.*;
import javax.swing.*;

public class Basket {

    private int x;
    private final int startX;
    private final int width = 100;
    private final int height = 50;
    private final int panelWidth;
    private final Image basketImage;

    public Basket(int startX, int panelWidth) {
        this.x = startX;
        this.startX = startX;
        this.panelWidth = panelWidth;

        // Load the image
        basketImage = new ImageIcon(getClass().getResource("/Image/Basket.png")).getImage();
    }

    public void moveLeft() {
        x -= 20;
        if (x < 0) x = 0;
    }

    public void moveRight() {
        x += 20;
        if (x > panelWidth - width) x = panelWidth - width;
    }

    public void draw(Graphics g, int panelHeight) {
        // Draw only the basket image
        g.drawImage(basketImage, x, panelHeight - 60, width, height, null);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void reset() {
        this.x = startX;
    }
}
