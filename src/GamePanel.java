import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

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
                checkWinState();
            }
        });
    }


    private GameGrid playerMove(int XX, int YY) {
        Color[][] newGrid = new Color[grid.rows][grid.cols];

        for (int oldY = (YY > 0 ? grid.rows - 1 : 0); oldY >= 0 && oldY < grid.rows; oldY += (YY > 0 ? -1 : 1)) {
            for (int oldX = (XX > 0 ? grid.cols - 1 : 0); oldX >= 0 && oldX < grid.cols; oldX += (XX > 0 ? -1 : 1)) {
                Color color = grid.grid[oldY][oldX];
                if (color == Color.GRAY) {
                    newGrid[oldY][oldX] = color;
                    continue;
                }
                if (color != null) {
                    int x = oldX, y = oldY;
                    while (inBounds(x + XX, y + YY)
                            && (newGrid[y + YY][x + XX] == null || newGrid[y + YY][x + XX] == color)
                    ) {
                        x += XX;
                        y += YY;
                    }
                    newGrid[y][x] = color;
                }
            }
        }
        return new GameGrid(newGrid, grid.rows, grid.cols);
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < grid.cols && y >= 0 && y < grid.rows;
    }

    private void checkWinState() {
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
