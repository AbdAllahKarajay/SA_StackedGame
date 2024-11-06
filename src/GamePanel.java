import javax.swing.*;
import javax.swing.plaf.DesktopIconUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePanel extends JPanel {
    private final int cellSize = 60;
    private GameGrid grid;

    public GamePanel(int rows, int cols, Color[][] startGrid) {

        this.grid = new GameGrid(startGrid, rows, cols);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            this.grid = new GameGrid(startGrid, rows, cols);
            repaint();
        });
        resetButton.setFocusable(false);
        add(resetButton);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> grid = playerMove(0, -1);
                    case KeyEvent.VK_DOWN -> grid = playerMove(0, 1);
                    case KeyEvent.VK_LEFT -> grid = playerMove(-1, 0);
                    case KeyEvent.VK_RIGHT -> grid = playerMove(1, 0);
                }
                repaint();
                checkWinCondition();
            }
        });
    }


    private GameGrid playerMove(int dx, int dy) {
        Color[][] newGrid = new Color[grid.rows][grid.cols];

        for (int y = (dy > 0 ? grid.rows - 1 : 0); y >= 0 && y < grid.rows; y += (dy > 0 ? -1 : 1)) {
            for (int x = (dx > 0 ? grid.cols - 1 : 0); x >= 0 && x < grid.cols; x += (dx > 0 ? -1 : 1)) {
                Color color = grid.grid[y][x];
                if (color == Color.GRAY) {
                    newGrid[y][x] = color;
                    continue;
                }
                if (color != null) {
                    int newX = x, newY = y;
                    while (isInBounds(newX + dx, newY + dy)
                            && (newGrid[newY + dy][newX + dx] == null || newGrid[newY + dy][newX + dx] == color)
                    ) {
                        newX += dx;
                        newY += dy;
                    }
                    newGrid[newY][newX] = color;
                }
            }
        }
        return new GameGrid(newGrid, grid.rows, grid.cols);
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < grid.cols && y >= 0 && y < grid.rows;
    }

    private void checkWinCondition() {
        if (grid.colorCount.entrySet().stream().allMatch(
                entry -> entry.getValue() == 1 || entry.getKey() == Color.GRAY)) {
            JOptionPane.showMessageDialog(this, "You win!");
        }
    }

    private List<GameGrid> predict() {
        return List.of(
                playerMove(0, -1),
                playerMove(0, 1),
                playerMove(-1, 0),
                playerMove(1, 0)
        );
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int base = 100;
        for (int y = 0; y < grid.rows; y++) {
            for (int x = 0; x < grid.cols; x++) {
                if (grid.grid[y][x] != null) {
                    g.setColor(grid.grid[y][x]);
                    g.fillRect(x * cellSize + base, y * cellSize + base, cellSize, cellSize);
                }
                g.setColor(Color.BLACK);
                g.drawRect(x * cellSize + base, y * cellSize + base, cellSize, cellSize);
            }
        }
    }
}
