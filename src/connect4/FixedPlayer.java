package connect4;


/**
 * This a computer player that always makes the same move
 * 
 * @author Andrea Sancho Silgado
 *
 */
public class FixedPlayer implements Player {

    private int column = -1;

    public int decideColumn(Board b) {
        do {
            column++;
            if (column >= Board.COLUMNS)
                column = 0;
        } while (b.getFreeRow(column) < 0);
        return column;
    }
}