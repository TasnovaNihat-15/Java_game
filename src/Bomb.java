import java.awt.*;
import java.util.Random;

public class Bomb {
    private int x, y, size, speed;
    private int panelWidth;
    private Random rand = new Random();


    public Bomb(int panelWidth) {
        this.panelWidth = panelWidth;
        reset();
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, size, size);
        g.setColor(Color.RED);
        g.drawLine(x + size / 2, y + size / 2, x + size / 2, y + size);
    }

    public void reset() {
        x = rand.nextInt(panelWidth - 30);
        y = -rand.nextInt(300) - 200; // Spawn off-screen
        size = 20;
        speed = 5 + rand.nextInt(3);
    }

    public boolean reachesBasket(Basket basket, int panelHeight) {
        Rectangle bombRect = new Rectangle(x, y, size, size);
        return bombRect.intersects(basket.getBounds(panelHeight));
    }

    public boolean isOffScreen(int panelHeight) {
        return y > panelHeight;
    }
}

