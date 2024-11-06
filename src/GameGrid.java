import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GameGrid {
    public Color[][] grid;
    public final int rows, cols;
    public Map<Color, Integer> colorCount;

    public GameGrid(Color[][] grid, int rows,int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = grid;
        colorCount = computeColorCount();
    }
    private Map<Color, Integer> computeColorCount() {
        Map<Color, Integer> colorCount = new HashMap<>();
        for (Color[] c : grid) {
            for (Color cell : c) {
                if (cell != null) {
                    colorCount.computeIfAbsent(cell, color -> 0);
                    colorCount.computeIfPresent(cell, (color, integer) -> integer + 1);
                }
            }
        }
        return colorCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameGrid gameGrid = (GameGrid) o;
        return rows == gameGrid.rows && cols == gameGrid.cols && Arrays.deepEquals(grid, gameGrid.grid) && Objects.equals(colorCount, gameGrid.colorCount);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols, colorCount);
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }

    @Override
    public String toString() {
        return "GameGrid{" +
                "grid=" + Arrays.toString(grid) +
                ", rows=" + rows +
                ", cols=" + cols +
                ", colorCount=" + colorCount +
                '}';
    }
}
