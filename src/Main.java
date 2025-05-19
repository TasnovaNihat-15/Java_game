import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("🍎 Fruit Catch Game");
        FruitCatchGame gamePanel = new FruitCatchGame();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        gamePanel.requestFocusInWindow();
    }
}
