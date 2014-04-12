package connect4;
import java.util.Random;

/**
 * Implements the behaviour of the minimax player.
 */
public class MinimaxPlayer implements Player {

    private Random random;
    private final int depth;

    public MinimaxPlayer(int d) {
        random = new Random(System.currentTimeMillis());
        this.depth = d;
    }

    public int decideColumn(Board b){
        Minimax minimax = new Minimax(b);
        int c = minimax.bestColumn(this.depth);
        while (c < 0 || b.getFreeRow(c) < 0) {
            c = random.nextInt(Board.COLUMNS);
        }
        return c;
    }
}