package connect4;
import java.util.Random;

/**
 * Computer player that makes random moves
 */
public class RandomPlayer implements Player {
	
    private Random random;

    public RandomPlayer() {
        random = new Random(System.currentTimeMillis());
    }

    public int decideColumn(Board b) {
        int c;
        do {
            c = random.nextInt(Board.COLUMNS);
        } while (b.getFreeRow(c) < 0);
        return c;
    }
}