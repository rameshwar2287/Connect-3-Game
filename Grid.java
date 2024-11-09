import java.io.*;
// import java.util.Arrays;

public class Grid implements Serializable {
    private Square[][] grid;
    private int rows, columns;
    private String currentPlayer;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new Square[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    public boolean dropDisc(Disc disc, int column) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column].isEmpty()) {
                grid[row][column].setDisc(disc);
                return true;
            }
        }
        return false; // Column is full
    }

    public boolean checkWin() {
        return checkRows() || checkColumns() || checkDiagonals();
    }

    // Check for a win in each row
    private boolean checkRows() {
        for (int i = 0; i < rows; i++) {
            Disc[] row = new Disc[columns];
            for (int j = 0; j < columns; j++) {
                row[j] = grid[i][j].getDisc();
            }
            if (checkLine(row)) return true;
        }
        return false;
    }

    // Check for a win in each column
    private boolean checkColumns() {
        for (int j = 0; j < columns; j++) {
            Disc[] col = new Disc[rows];
            for (int i = 0; i < rows; i++) {
                col[i] = grid[i][j].getDisc();
            }
            if (checkLine(col)) return true;
        }
        return false;
    }

    // Check for a win in diagonals (both directions)
    private boolean checkDiagonals() {
        // Check for diagonals from bottom-left to top-right
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 0; j < columns - 2; j++) {
                if (checkThree(grid[i][j], grid[i + 1][j + 1], grid[i + 2][j + 2])) {
                    return true;
                }
            }
        }

        // Check for diagonals from bottom-right to top-left
        for (int i = 0; i < rows - 2; i++) {
            for (int j = 2; j < columns; j++) {
                if (checkThree(grid[i][j], grid[i + 1][j - 1], grid[i + 2][j - 2])) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method to check if three consecutive discs are the same color
    private boolean checkThree(Square a, Square b, Square c) {
        return a.getDisc() != null && b.getDisc() != null && c.getDisc() != null &&
               a.getDisc().getColor().equals(b.getDisc().getColor()) &&
               b.getDisc().getColor().equals(c.getDisc().getColor());
    }

    // Helper method to check if a line (row or column) has three consecutive discs of the same color
    private boolean checkLine(Disc[] line) {
        int count = 0;
        String lastColor = null;

        for (Disc disc : line) {
            if (disc != null && disc.getColor().equals(lastColor)) {
                count++;
                if (count == 3) return true;
            } else {
                count = 1;
                lastColor = (disc != null) ? disc.getColor() : null;
            }
        }
        return false;
    }

    // Save the current game state
    public void saveGame(String currentPlayer) {
        this.currentPlayer = currentPlayer;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("connect3_save.dat"))) {
            out.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed to save game: " + e.getMessage());
        }
    }

    // Load a previously saved game state
    public static Grid loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("connect3_save.dat"))) {
            return (Grid) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Square[] row : grid) {
            for (Square square : row) {
                sb.append(square).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
