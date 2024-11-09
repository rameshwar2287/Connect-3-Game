public class Row {
    private Square[] squares;

    public Row(int columns) {
        squares = new Square[columns];
        for (int i = 0; i < columns; i++) {
            squares[i] = new Square();
        }
    }

    public Square[] getSquares() {
        return squares;
    }
}
