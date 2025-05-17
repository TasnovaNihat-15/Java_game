import java.awt.*;

public class Basket {

    private int x;
    private final int startX;
    private final int width = 100;
    private final int height = 30;
    private final int panelWidth;

    public Basket(int startX, int panelWidth) {
        this.x = startX;
        this.startX=startX;
        this.panelWidth = panelWidth;
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
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, panelHeight - 60, width, height);
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
