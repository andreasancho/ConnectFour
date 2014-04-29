package connect4;
/**
 * class EvaluaTablero
 *
 * @author (Andrea Sancho Silgado)
 * @version (17.05.2009)
 */

public class AnalyzeBoard {

    private static final int FOUR = 1000000000;
    private static final int THREEE = 10000;
    private static final int THREE = 9000;
    private static final int TWO = 100;


    public AnalyzeBoard() {
    
    }
  
/**
 * Looks if any player has won (4 aligned disks from same player)
 *
 * @param  board
 * @param   disk   (player)
 * @return winner  
 */ 

    public static boolean won(Disk d, Board b) {
        boolean winner = false;
        for (int c = 0; c < Board.COLUMNS; c ++) {
            for (int r = 0; r < Board.ROWS; r ++) {
                if ((r >= 3)
                && (b.getDisk(new Position(c,r-3)) == d)
                && (b.getDisk(new Position(c,r-2)) == d) 
                && (b.getDisk(new Position(c,r-1)) == d) 
                && (b.getDisk(new Position(c,r)) == d)){
                    winner = true;
                    break;
                }
                if ((c >= 3)
                && (b.getDisk(new Position(c-3,r)) == d)
                && (b.getDisk(new Position(c-2,r)) == d)
                && (b.getDisk(new Position(c-1,r)) == d)
                && (b.getDisk(new Position(c,r)) == d)){
                    winner = true;
                    break;
                }
                if ((c >= 3) && (r >= 3)
                && (b.getDisk(new Position(c-3,r-3)) == d)
                && (b.getDisk(new Position(c-2,r-2)) == d)
                && (b.getDisk(new Position(c-1,r-1)) == d)
                && (b.getDisk(new Position(c,r)) == d)){
                    winner = true;
                    break;
                }
                if ((c < Board.COLUMNS - 3) && ( r >= 3)
                && (b.getDisk(new Position(c,r)) == d)
                && (b.getDisk(new Position(c+1,r-1)) == d)
                && (b.getDisk(new Position(c+2,r-2)) == d)
                && (b.getDisk(new Position(c+3,r-3)) == d)){
                    winner = true;
                    break;
                }
            }
        }
        return winner;
    }
    
    /**
     * This method score the board from the computer player side
     * 
     * @param board  
     * @return final score of the board
     * 
     */
    public static int analyzingBoard(Board b) {
        int scoreH = 0;
        int scoreC = 0;
        Disk dh = Disk.HUMAN;
        Disk dc = Disk.COMPUTER;
        for (int c = 0; c < Board.COLUMNS; c ++) {
            for (int r = 0; r < Board.ROWS; r ++) {
                scoreH += scorer(b, dh, c, r);
                scoreC += scorer(b, dc, c, r);
            }
        }
        //System.out.println(scoreC);
        //System.out.println(scoreH);
        return (scoreC - scoreH);
    }
 
    /**
     * @param board being analyzed
     * @param column   
     * @param row 
     * @param cDir   column direction
     * @param rDir   row direction
     * @return disks    (defines the search range)
     */    
        
    private static Disk[] disker(Board b, int c, int r, int cDir, int rDir) {
        Disk[] disks = new Disk[4];
        for (int n = 0, nc = c, nr = r;  
        (n < disks.length) && (Position.validPosition(nc, nr)); 
        n ++, nc += cDir, nr += rDir) {
            disks[n] = b.getDisk(new Position(nc, nr));
        }
        return disks;
    }
            
    
    /**
     * @param board  
     * @param disk     (player)
     * @param column
     * @param row
     * 
     * @return score
     */   
    private static int scorer(Board b, Disk d, int c, int r) {
        int score = 0;
        int i = 0;
        //...................................ROWS..........................................        
        if (c < (Board.COLUMNS - 3)) {
            Disk[] w = (disker(b, c, r, 1, 0));
            if ((w[i]==d)&&(w[i+1]==d)&&(w[i+2]==d)&&(w[i+3]==d)){
                score += FOUR;
            }
            else if ((w[i]==null)&&(w[i+1]==d)&&(w[i+2]==d)&&(w[i+3]==d)){
                score += THREEE;
            }
            else if ((w[i]==d)&&(w[i+1]==null)&&(w[i+2]==d)&&(w[i+3]==d)){
                score += THREE;
            }
            else if ((w[i]==null)&&(w[i+1]==null)&&(w[i+2]==d)&&(w[i+3]==d)){
                score += TWO;
            }
            else if ((w[i]==null)&&(w[i+1]==d)&&(w[i+2]==null)&&(w[i+3]==d)){
                score += TWO;
            }
            else if ((w[i]==null)&&(w[i+1]==d)&&(w[i+2]==d)&&(w[i+3]==null)){
                score += TWO;
            }
        }
        if (c >= 3) {
            Disk[] x = (disker(b, c, r, -1, 0));
            if ((x[i]==null)&&(x[i+1]==d)&&(x[i+2]==d)&&(x[i+3]==d)){
                score += THREEE;
            }
            else if ((x[i]==d)&&(x[i+1]==null)&&(x[i+2]==d)&&(x[i+3]==d)){
                score += THREE;
            }
            else  if ((x[i]==null)&&(x[i+1]==null)&&(x[i+2]==d)&&(x[i+3]==d)){
                score += TWO;
            }
            else  if ((x[i]==null)&&(x[i+1]==d)&&(x[i+2]==null)&&(x[i+3]==d)){
                score += TWO;
            }
            else if ((x[i]==d)&&(x[i+1]==null)&&(x[i+2]==null)&&(x[i+3]==d)){
                score += TWO;
            }
        }        
        //...................................COLUMNS..........................................
        if (r < (Board.ROWS - 3)) {
            Disk[] y = (disker(b, c, r, 0, 1));
            if ((y[i]==d)&&(y[i+1]==d)&&(y[i+2]==d)&&(y[i+3]==d)){
                score += FOUR;
            }
            else if ((y[i]==null)&&(y[i+1]==d)&&(y[i+2]==d)&&(y[i+3]==d)){
                score += THREEE;
            }
            else if ((y[i]==null)&&(y[i+1]==null)&&(y[i+2]==d)&&(y[i+3]==d)){
                score += TWO;
            }
            else if ((y[i]==d)&&(y[i+1]==d)&&(y[i+2]==d)&&(y[i+3]==null)){
                score += THREEE;
            }
            else if ((y[i]==d)&&(y[i+1]==d)&&(y[i+2]==null)&&(y[i+3]==null)){
                score += TWO;
            }
        }
       //...................................DIAGONALS..........................................
       if ((c < (Board.COLUMNS-3))&&(r < (Board.ROWS-3))) {
            Disk[] z = (disker(b, c, r, 1, 1));
            if ((z[i]==d)&&(z[i+1]==d)&&(z[i+2]==d)&&(z[i+3]==d)){
                score += FOUR;
            }
            else if ((z[i]==null)&&(z[i+1]==d)&&(z[i+2]==d)&&(z[i+3]==d)){
            	score += THREEE;
            }
            else if ((z[i]==d)&&(z[i+1]==null)&&(z[i+2]==d)&&(z[i+3]==d)){
            	score += THREE;
            }
            else if ((z[i]==d)&&(z[i+1]==d)&&(z[i+2]==d)&&(z[i+3]==null)){
            	score += THREEE;
            }
            else if ((z[i]==d)&&(z[i+1]==d)&&(z[i+2]==null)&&(z[i+3]==d)){
            	score += THREE;
            }
            else if ((z[i]==null)&&(z[i+1]==null)&&(z[i+2]==d)&&(z[i+3]==d)){
            	score += TWO;
            }
            else if ((z[i]==null)&&(z[i+1]==d)&&(z[i+2]==null)&&(z[i+3]==d)){
            	score += TWO;
            }
            else if ((z[i]==null)&&(z[i+1]==d)&&(z[i+2]==d)&&(z[i+3]==null)){
            	score += TWO;
            }
            else if ((z[i]==d)&&(z[i+1]==d)&&(z[i+2]==null)&&(z[i+3]==null)){
            	score += TWO;
            }
            else if ((z[i]==d)&&(z[i+1]==null)&&(z[i+2]==d)&&(z[i+3]==null)){
            	score += TWO;
            }
            else if ((z[i]==d)&&(z[i+1]==null)&&(z[i+2]==null)&&(z[i+3]==d)){
            	score += TWO;
            }
        }
        if ((c >= 3)&&(r < (Board.ROWS-3))) {
            Disk[] v = (disker(b, c, r, -1, 1));
            if ((v[i]==d)&&(v[i+1]==d)&&(v[i+2]==d)&&(v[i+3]==d)){
                score += FOUR;
            }
            else if ((v[i]==null)&&(v[i+1]==d)&&(v[i+2]==d)&&(v[i+3]==d)){
            	score += THREEE;
            }
            else if ((v[i]==d)&&(v[i+1]==null)&&(v[i+2]==d)&&(v[i+3]==d)){
            	score += THREE;
            }
            else if ((v[i]==d)&&(v[i+1]==d)&&(v[i+2]==d)&&(v[i+3]==null)){
            	score += THREEE;
            }
            else if ((v[i]==d)&&(v[i+1]==d)&&(v[i+2]==null)&&(v[i+3]==d)){
            	score += THREE;
            }
            else if ((v[i]==null)&&(v[i+1]==null)&&(v[i+2]==d)&&(v[i+3]==d)){
            	score += TWO;
            }
            else if ((v[i]==null)&&(v[i+1]==d)&&(v[i+2]==null)&&(v[i+3]==d)){
            	score += TWO;
            }
            else if ((v[i]==null)&&(v[i+1]==d)&&(v[i+2]==d)&&(v[i+3]==null)){
            	score += TWO;
            }
            else if ((v[i]==d)&&(v[i+1]==d)&&(v[i+2]==null)&&(v[i+3]==null)){
            	score += TWO;
            }
            else if ((v[i]==d)&&(v[i+1]==null)&&(v[i+2]==d)&&(v[i+3]==null)){
            	score += TWO;
            }
            else if ((v[i]==d)&&(v[i+1]==null)&&(v[i+2]==null)&&(v[i+3]==d)){
            	score += TWO;
            }
        }
        return score;
    }
}    
