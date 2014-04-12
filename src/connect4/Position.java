package connect4;
/**
 *
 * @author Andrea Sancho Silgado
 * @version 23.3.2009
 */
public class Position {

    private int column;
    private int row;

    /**
     * Constructor
     *
     * @param column 
     * @param row  
     * @throws IllegalArgumentException
     */
    public Position(int c, int r) {
        if (!validPosition(c, r)) {
            throw new IllegalArgumentException(String.format("Position invalid (%d, %d)", c, r));
        }
        this.column = c;
        this.row = r;
    }

    public int getColumn() {
        return this.column;
    }

    public static boolean validColumn(int c) {
        return (c >= 0 && c < Board.COLUMNS);
    }

    public int getRow() {
        return this.row;
    }

    public static boolean validRow(int r) {
        return (r >= 0 && r < Board.ROWS);
    }

    public void setPosition(int c, int r) {
        if (!validPosition(c, r)) {
            throw new IllegalArgumentException("Invalid row " + r);
        }
        this.column = c;
        this.row = r;
    }

    public static boolean validPosition(int c, int r) {
        return (validColumn(c) && validRow(r));
    }

    public String toString() {
        return String.format("(%d, %d)", this.column, this.row);
    }
}