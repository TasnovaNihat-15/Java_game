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

        g.drawImage(basketImage, x, panelHeight - 50, width, height, null);
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
    public Rectangle getBounds(int panelHeight) {
        return new Rectangle(x, panelHeight - 50, width, height);
    }

}
