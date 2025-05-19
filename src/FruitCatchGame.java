import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FruitCatchGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 700;

    private boolean paused = false;

    private Timer timer;
    private Basket basket;
    ArrayList<Fruit> fruits = new ArrayList<>();

    private int score = 0;
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private int bombTimer = 0;

    private boolean gameOver = false;
    private Random rand;
    private int missedFruits = 0;

    private int nextSpeedIncreaseAt = 50;

    public FruitCatchGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        rand = new Random();
        basket = new Basket(200, WIDTH);
        for (int i = 0; i < 3; i++) {
            fruits.add(new Fruit(WIDTH));
        }

        for (int i = 0; i < 2; i++) {
            bombs.add(new Bomb(WIDTH));
        }


        timer = new Timer(20, this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        basket.draw(g, HEIGHT);
        for (Fruit fruit : fruits) {
            fruit.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        String missedText = "Missed: " + missedFruits + "/5";
        FontMetrics metrics = g.getFontMetrics();
        int missedWidth = metrics.stringWidth(missedText);
        g.drawString(missedText, WIDTH - missedWidth - 20, 30);
        if (paused && !gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.BLUE);
            g.drawString("PAUSED", WIDTH / 2 - 80, HEIGHT / 2);
        }


        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.setColor(Color.RED);
            g.drawString("GAME OVER", WIDTH / 2 - 120, HEIGHT / 2 - 40);

            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.setColor(Color.BLACK);
            g.drawString("Your Score: " + score, WIDTH / 2 - 80, HEIGHT / 2);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press ENTER to Restart", WIDTH / 2 - 100, HEIGHT / 2 + 40);
        }
        for (Bomb bomb : bombs) {
            bomb.draw(g);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !paused) {
            for (Fruit fruit : fruits) {
                fruit.move();

                if (fruit.reachesBasket(basket, HEIGHT)) {
                    score += 5;
                    fruit.reset(WIDTH);
                } else if (fruit.isMissed(HEIGHT)) {
                    missedFruits++;
                    fruit.reset(WIDTH);

                    if (missedFruits >= 5) {
                        gameOver = true;
                        timer.stop();
                    }
                }
            }
            if (score >= nextSpeedIncreaseAt) {
                for (Fruit fruit : fruits) {
                    fruit.increaseSpeed();
                }


                nextSpeedIncreaseAt += 50;
            }
            for (Bomb bomb : bombs) {
                bomb.move();

                if (bomb.reachesBasket(basket, HEIGHT)) {
                    gameOver = true;
                    timer.stop();
                } else if (bomb.isOffScreen(HEIGHT)) {
                    bomb.reset();
                }
            }
            repaint();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) basket.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) basket.moveRight();
            if (e.getKeyCode() == KeyEvent.VK_P) {
                paused = true;
            } else if (e.getKeyCode() == KeyEvent.VK_R) {
                paused = false;
            }
        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            resetGame();
        }
    }

    private void resetGame() {
        score = 0;
        missedFruits = 0;
        basket.reset();
        for (Fruit fruit : fruits) {
            fruit.reset(WIDTH);
            fruit.resetSpeed();
        }
        for (Bomb bomb : bombs) {
            bomb.reset();


            while (bomb.reachesBasket(basket, HEIGHT)) {
                bomb.reset();
            }
        }
        gameOver = false;
        timer.start();
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}