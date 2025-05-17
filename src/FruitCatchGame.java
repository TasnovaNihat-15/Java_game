import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class FruitCatchGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 700;

    private Timer timer;
    private Basket basket;
    private Fruit fruit;

    private int score = 0;
    private boolean gameOver = false;
    private Random rand;

    public FruitCatchGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        rand = new Random();
        basket = new Basket(200, WIDTH);
        fruit = new Fruit(rand.nextInt(WIDTH - 30), 0);

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.PINK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        basket.draw(g, HEIGHT);
        fruit.draw(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", WIDTH / 2 - 120, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            fruit.move();

            if (fruit.reachesBasket(basket, HEIGHT)) {
                score++;
                fruit.reset(rand.nextInt(WIDTH - fruit.getSize()));

                if (score % 5 == 0) fruit.increaseSpeed();
            }

            if (fruit.isMissed(HEIGHT)) {
                gameOver = true;
                timer.stop();
            }

            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) basket.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) basket.moveRight();
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            resetGame();
        }
    }

    private void resetGame() {
        score = 0;
        basket.reset();
        fruit.reset(rand.nextInt(WIDTH - fruit.getSize()));
        fruit.resetSpeed();
        gameOver = false;
        timer.start();
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}