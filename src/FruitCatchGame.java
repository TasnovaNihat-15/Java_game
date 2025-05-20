import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FruitCatchGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 500;
    private final int HEIGHT = 700;

    private boolean waitingForNextLevel = false;

    private int highScore = 0;
    private final String HIGH_SCORE_FILE = "highscore.txt";

    private boolean paused = false;

    private Timer timer;

    private Basket basket;
    ArrayList<Fruit> fruits = new ArrayList<>();
    private ArrayList<Bomb> bombs = new ArrayList<>();
    private Random rand;

    private int score = 0;
    private int missedFruits = 0;
    private boolean gameOver = false;

    private int level = 1;
    private int nextLevelScore = 30;

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
        loadHighScore();
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

        for (Bomb bomb : bombs) {
            bomb.draw(g);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Level: " + level, 20, 60);
        g.drawString("High Score: " + highScore, 20, 90);

        String missedText = "Missed: " + missedFruits + "/5";
        FontMetrics metrics = g.getFontMetrics();
        int missedWidth = metrics.stringWidth(missedText);
        g.drawString(missedText, WIDTH - missedWidth - 20, 30);

        if (paused && !gameOver && !waitingForNextLevel) {
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.setColor(Color.BLUE);
            g.drawString("PAUSED", WIDTH / 2 - 100, HEIGHT / 2 - 40);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.BLACK);
            g.drawString("Press 'R' to Resume the Game", WIDTH / 2 - 130, HEIGHT / 2 + 10);
        }
        if (waitingForNextLevel) {
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.setColor(Color.GREEN);
            g.drawString("Level " + (level) + " Starting Soon!", WIDTH / 2 - 140, HEIGHT / 2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.BLACK);
            g.drawString("Press 'N' to start Level " + level, WIDTH / 2 - 110, HEIGHT / 2 + 20);
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
    }
    private void loadHighScore() {
        try {
            java.io.File file = new java.io.File(HIGH_SCORE_FILE);
            if (file.exists()) {
                java.util.Scanner scanner = new java.util.Scanner(file);
                if (scanner.hasNextInt()) {
                    highScore = scanner.nextInt();
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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


                    if (score >= nextLevelScore && !waitingForNextLevel) {
                        level++;
                        nextLevelScore += 50;
                        waitingForNextLevel = true;
                        paused = false;
                        timer.stop();
                        increaseDifficulty();
                    }
                } else if (fruit.isMissed(HEIGHT)) {
                    missedFruits++;
                    fruit.reset(WIDTH);
                    if (missedFruits >= 5) {
                        gameOver = true;
                        timer.stop();
                    }
                }
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
            if (score > highScore) {
                highScore = score;
                saveHighScore();
            }


            repaint();
        }
    }

    private void increaseDifficulty() {
        for (Fruit fruit : fruits) {
            fruit.increaseSpeed();
        }
    }

    private void saveHighScore() {
            try {
                java.io.PrintWriter writer = new java.io.PrintWriter(HIGH_SCORE_FILE);
                writer.println(highScore);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }



    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (waitingForNextLevel && e.getKeyCode() == KeyEvent.VK_N) {
            waitingForNextLevel = false;
            paused = false;

            timer.start();
            repaint();
            return;
        }
        if (!gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) basket.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) basket.moveRight();
            if (e.getKeyCode() == KeyEvent.VK_P){
                paused = true;
                timer.stop();
               repaint();

            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                paused = false;
                timer.start();
                repaint();

            }

        }

        if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
            resetGame();
        }
    }

    private void resetGame() {
        score = 0;
        missedFruits = 0;
        level = 1;
        nextLevelScore = 30;
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
        paused = false;
        timer.start();
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
