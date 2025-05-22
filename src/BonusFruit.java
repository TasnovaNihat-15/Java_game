import java.awt.*;
import java.util.Random;

public class BonusFruit {
    private int x, y, size, speed;
    private boolean visible = false;

    private final Color color = new Color(128, 0, 128);
    private Random rand;

    public BonusFruit(int panelWidth) {
        this(panelWidth, new Random());
    }

    public BonusFruit(int panelWidth, Random rand) {
        this.rand = rand;
        size = 25;
        speed = 10;
        x = rand.nextInt(panelWidth - size);
        y = -rand.nextInt(300) - size;
        visible = true;
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        if (visible) {
            g.setColor(color);
            g.fillOval(x, y, size, size);
        }
    }

    public boolean reachesBasket(Basket basket, int panelHeight) {
        boolean caught = y + size >= panelHeight - 60 &&
                x + size >= basket.getX() &&
                x <= basket.getX() + basket.getWidth();
        if (caught) {
            visible = false;
        }
        return caught;
    }

    public boolean isMissed(int panelHeight) {
        if (y > panelHeight) {
            visible = false;
            return true;
        }
        return false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}