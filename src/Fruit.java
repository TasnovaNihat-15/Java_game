import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Fruit {
    private int x, y;
    private final int size = 30;
    private int speed = 7;

    private Image fruitImage;
    private static final Random rand = new Random();

    // Load 5 fruit images once
    private static final Image[] IMAGES = new Image[5];
    static {
        for (int i = 0; i < 5; i++) {
            IMAGES[i] = new ImageIcon(Fruit.class.getResource("/Image/fruit" + (i + 1) + ".png")).getImage();
        }
    }

    public Fruit(int panelWidth) {
        this.x = rand.nextInt(panelWidth - size);
        this.y = -rand.nextInt(300); // Random starting height off-screen
        this.fruitImage = getRandomFruitImage();
    }

    public void move() {
        y += speed;
    }

    public void draw(Graphics g) {
        g.drawImage(fruitImage, x, y, size, size, null);
    }

    public void reset(int panelWidth) {
        this.x = rand.nextInt(panelWidth - size);
        this.y = -rand.nextInt(300); // Reset off-screen
        this.fruitImage = getRandomFruitImage();
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

    private Image getRandomFruitImage() {
        return IMAGES[rand.nextInt(IMAGES.length)];
    }
}
