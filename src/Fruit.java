import java.awt.*;

public class Fruit {
    private int x, y;
    private final int size = 30;
    private int speed = 5;

    public Fruit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, size, size);
    }

    public void reset(int newX) {
        this.x = newX;
        this.y = 0;
    }

    public boolean reachesBasket(Basket basket, int panelHeight) {
        return y + size >= panelHeight - 60 &&
                x + size >= basket.getX() &&
                x <= basket.getX() + basket.getWidth();
    }

    public boolean isMissed(int panelHeight) {
        return y > panelHeight;
    }

    public void increaseSpeed() {
        speed++;
    }

    public void resetSpeed() {
        speed = 5;
    }

    public int getSize() {
        return size;
    }
}