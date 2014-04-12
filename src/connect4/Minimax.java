package connect4;

import java.util.*;

/**
 * This class computes the possible best movement for the computer player
 *
 * @author (Andrea Sancho Silgado)
 * @version (23.05.2009)
 */

public class Minimax{
    private final int MIN_SCORE = Integer.MIN_VALUE;
    private final int MAX_SCORE = Integer.MAX_VALUE;
    Board b;
    private double time0;
    private double time1;
    private int nodes = 0;


    public Minimax(Board board)  {       
        this.b = new Board(board);  
    }  
  
/**
 * Calculates the best possible move for the computer player, assuming that
 * the next player is the computer, with the given game (board)
 * 
 * @param depth
 * @return  column
 */  
    public int bestColumn(int d) throws IllegalArgumentException {
        if (d <= 0) {
            throw new IllegalArgumentException("Invalid depth");
        }
        time0 = System.currentTimeMillis();
        int c = -1;       
        int maxScore = MIN_SCORE;
        Set<Integer> moves = b.possibleMoves();
        for (Integer i: moves) {
            b.insertDisk(i, Disk.COMPUTER);
            nodes ++;
            int score = expandMinNode(d - 1);
            if (score > maxScore) {
                maxScore = score;
                c = i;
            }
            b.removeDisk(i);
        }
        time1 = System.currentTimeMillis();
        return c;
    } 

    private int expandMaxNode(int d) {
        if (d == 0) {
           int p = AnalyzeBoard.analyzingBoard(b);
           return p;
        }
        if (AnalyzeBoard.won(Disk.HUMAN, b)) {
            return MIN_SCORE;
        }
        else {
            int maxScore = MIN_SCORE;
            Set<Integer> moves = b.possibleMoves();
            for (Integer i: moves) {
                b.insertDisk(i, Disk.COMPUTER);
                nodes ++;
                int score = expandMinNode (d - 1);
                if (score > maxScore) {
                    maxScore = score;
                }
                b.removeDisk(i);
            }
            return maxScore;
        }   
    }

    private int expandMinNode (int d) {
        if (d == 0) {
           int p = AnalyzeBoard.analyzingBoard(b);
           return p;
        }
        if (AnalyzeBoard.won(Disk.COMPUTER, b)) {
            return MAX_SCORE;
        }
        else {
            int minScore = MIN_SCORE;
            Set<Integer> moves = b.possibleMoves();
            for (Integer i: moves) {
                b.insertDisk(i, Disk.HUMAN);
                nodes ++;
                int score = expandMaxNode(d - 1);
                if (score < minScore) {
                    minScore = score;
                }
                b.removeDisk(i);
            }
            return minScore;
        }
    } 
}
