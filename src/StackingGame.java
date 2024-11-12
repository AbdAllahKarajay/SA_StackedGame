//By Abd Allah karajay -- عبد الله قره جاي

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

public class StackingGame extends JFrame {
    private GamePanel gamePanel;

    public StackingGame() {
        setTitle("Grid Merging Game");
        setSize(550, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter: COLS ROWS");
        int cols = scanner.nextInt();
        int rows = scanner.nextInt();
        Color[][] startGrid = new Color[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.out.println("Enter Colors of " + (i+1) + " row: ");
            for (int j = 0; j < cols; j++) {
                System.out.print(" "+ (j+1)+": ");
                String input = scanner.next();
                startGrid[i][j] = colorFromString(input);
            }
        }
        System.out.println(Arrays.deepToString(startGrid));


        GamePanel gamePanel = new GamePanel(rows, cols, startGrid);  // 5x5 grid
        add(gamePanel);
        setVisible(true);
    }

    private Color colorFromString(String input) {
        switch (input.toUpperCase()){
            case "RED" -> {
                return Color.RED;
            }
            case "GREEN" -> {
                return Color.GREEN;
            }case "BLUE" -> {
                return Color.BLUE;
            }case "PINK" -> {
                return Color.PINK;
            }case "GRAY" -> {
                return Color.GRAY;
            }case "WALL" -> {
                return Color.GRAY;
            }
            default -> {
                return null;
            }
        }
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(StackingGame::new);
    }
}