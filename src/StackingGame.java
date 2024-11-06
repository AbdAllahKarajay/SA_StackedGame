//By Abd Allah karajay -- عبد الله قره جاي

import javax.swing.*;
import java.awt.*;

public class StackingGame extends JFrame {
    private GamePanel gamePanel;

    public StackingGame() {
        setTitle("Grid Merging Game");
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color[][] startGrid = new Color[5][5];
        startGrid[0] = new Color[]{Color.RED, Color.RED, null, null, null};
        startGrid[1] = new Color[]{null, Color.GREEN, null, Color.GRAY, Color.BLUE};
        startGrid[2] = new Color[]{Color.BLUE, Color.GREEN, null, Color.GRAY, Color.BLUE};
        startGrid[3] = new Color[]{null, Color.GRAY, null, Color.GRAY, Color.GREEN};
        startGrid[4] = new Color[]{null, Color.RED, null, null, null};

        GamePanel gamePanel = new GamePanel(5, 5, startGrid);  // 5x5 grid
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StackingGame::new);
    }
}