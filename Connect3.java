import java.util.Scanner;

public class Connect3 {
    private static final int ROWS = 5;
    private static final int COLUMNS = 6;
    private static Grid grid;
    private static String currentPlayer;

    public Connect3() {
        grid = new Grid(ROWS, COLUMNS);
        currentPlayer = "Red"; // Red goes first by default
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        // Load saved game or start a new one
        System.out.println("Do you want to load a saved game? (yes/no)");
        String loadResponse = scanner.nextLine();
        if (loadResponse.equalsIgnoreCase("yes")) {
            grid = Grid.loadGame();
            if (grid != null) {
                currentPlayer = grid.getCurrentPlayer();
                System.out.println("Game loaded successfully!");
            } else {
                System.out.println("No saved game found. Starting a new game.");
                grid = new Grid(ROWS, COLUMNS);
                currentPlayer = "Red";
            }
        } else {
            grid = new Grid(ROWS, COLUMNS);
        }

        // Main game loop
        while (true) {
            System.out.println(grid);
            System.out.println("Current Player: " + currentPlayer);
            System.out.print("Enter column (1-6) or 'save' to save game: ");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("save")) {
                grid.saveGame(currentPlayer);
                System.out.println("Game saved!");
                break;
            }

            int column;
            try {
                column = Integer.parseInt(input) - 1;
                if (column < 0 || column >= COLUMNS) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            if (grid.dropDisc(new Disc(currentPlayer), column)) {
                if (grid.checkWin()) {
                    System.out.println(grid);
                    System.out.println(currentPlayer + " wins!");
                    break;
                }
                currentPlayer = currentPlayer.equals("Red") ? "Green" : "Red";
            } else {
                System.out.println("Column is full! Try another one.");
            }
        }
        scanner.close();
    }
}
