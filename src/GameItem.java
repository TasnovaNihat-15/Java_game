
import java.awt.*;

public abstract class GameItem {
    protected int x, y, size, speed;

    public abstract void move();
    public abstract void draw(Graphics g);
    public abstract void reset(int panelWidth);
    public abstract boolean reachesBasket(Basket basket, int panelHeight);
    public abstract boolean isMissed(int panelHeight);

    public int getX() { return x; }
    public int getY() { return y; }
    public int getSize() { return size; }
}
