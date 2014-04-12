package connect4;

import java.util.*;
import java.io.*;

/**
 * Class for generating the connect
 *
 * @author (Andrea Sancho Silgado)
 * @version (14.05.2009)
 */

public class Board {

    public static final int ROWS = 6;
    public static final int COLUMNS = 8;
    private Disk[][] arrayBoard;
 
    /**
     * Constructor
     * Creates and initializes a board with all null disks.
     */
  
    public Board () { 
        this.arrayBoard = new Disk[COLUMNS][ROWS];
        for (int i = 0; i < Board.COLUMNS ; i++) {
            for (int j = 0; j < Board.ROWS ; j++) {
            	arrayBoard[i][j] = null;
            }
        }
        for (int c = 0; c < Board.COLUMNS ; c++) {
            for (int f = 1; f < Board.ROWS ; f++) {
                if ((arrayBoard[c][f-1] != null) && (arrayBoard[c][f] == null)){
                	System.out.println("Invalid board built");
                }
            }
        }
    }
 
    /**
     * Constructor for a given disks array.
     * @param disks  
     */
  
    public Board (Disk[][] disks) throws IllegalArgumentException {
        this.arrayBoard = new Disk[COLUMNS][ROWS];
        if (disks.length != Board.COLUMNS) {
            throw new IllegalArgumentException("El numero de columnas del tablero no es correcto");
        }
      
        if (disks[0].length != Board.ROWS) {
            throw new IllegalArgumentException("El numero de filas del tablero no es correcto");
        }
                             
        for (int i = 0; i < Board.COLUMNS ; i++) {
            for (int j = 1; j < Board.ROWS ; j++) {
                if ((disks[i][j-1] != null) && (disks[i][j] == null)){
                	System.out.println("Invalid board built");
                }
            }
        }
      
        for (int i = 0; i < disks.length; i ++ ) {
            for (int j = 0; j < disks[0].length; j++) {
                if ((disks.length == this.arrayBoard.length) && (disks[0].length == this.arrayBoard[0].length)) {
                this.arrayBoard[i][j] = disks[i][j];
                }
            }
        }   
    }

    /**
     * Constructor for a given board.
     * @param board 
     */
 
    public Board(Board board) {
        if( board == null) throw new NullPointerException("");
       
        for (int c = 0; c < Board.COLUMNS ; c++) {
            for (int f = 1; f < Board.ROWS ; f++) {
                if (((board.getDisk(new Position(c, f-1))) != null) && ((board.getDisk(new Position(c, f))) == null)){
                	System.out.println("Invalid board built");
                }
            }
        }
        this.arrayBoard = new Disk[COLUMNS][ROWS];
        for (int i = 0; i < Board.COLUMNS; i ++ ) {
             for (int j = 0; j < Board.ROWS; j++) {
                 this.arrayBoard[i][j] = board.getDisk(new Position(i,j));
             }
        }               
    }

    /**
     * Inserts a disk according to the game rules.
     * 
     * @param column
     * @return row     Value of the row where the disk has been inserted.
     * 				   Returns -1 if that column is full
     */
 
    public int insertDisk(int column, Disk disk) throws IllegalArgumentException {      
        int row = -1;
        if ((column >= Board.COLUMNS) && (column < 0)) {
            throw new IllegalArgumentException("Invalid Column");
        }
        for (int f = (Board.ROWS -1); f >= 0; f--) {
            if (this.arrayBoard[column][f] == null) {
                this.arrayBoard[column][f] = disk;
                row = f;
                break;
            }
        }             
        if (row == -1) {
          System.out.println("The column "  + column + " is full and the disk was not inserted.");
        }
        else if (row != -1) {
           // System.out.println("The disk has been inserted " + row + " in the column " + column);
        }
        return row;
    }

    /**
     * Removes the most exterior disk in a column
     * @param column   
     * 
     */
    public void removeDisk(int column) throws IllegalArgumentException{

        if ((column >= Board.COLUMNS) && (column < 0)) {
            throw new IllegalArgumentException("Invalid column");
        }
        if ((column < Board.COLUMNS) && (column >= 0)) {
                for (int j = 1; j < Board.ROWS ; j++) {
                    if (arrayBoard[column][j-1] != null) {
                    	arrayBoard[column][j-1] = null;
                        break;
                    } else if ((arrayBoard[column][j-1] == null) && (arrayBoard[column][j] != null)) {
                    	arrayBoard[column][j] = null;
                        break;
                    }
                }
        }
    }

    /**
     * Returns an Integer list with the possible columns in which a disk can be inserted
     *
     * @return possibleColumns 
     */
    public Set<Integer> possibleMoves () {      
        Set<Integer> possibleColumns = new HashSet<Integer>();
        for (int i = 0; i < Board.COLUMNS ; i++) {
                if (arrayBoard[i][0] == null) {
                	possibleColumns.add(i);
            }
        }         
        return possibleColumns;
    }   

    /** 
     * Returns the disk in the given position
     * 
     * @param position 
     * @return Disk 
     */
    public Disk getDisk(Position p) throws IllegalArgumentException {
        if (!((p.getColumn() < Board.COLUMNS) && (p.getColumn() >= 0)) ||
        (!((p.getRow() < Board.ROWS) && (p.getRow() >= 0)))) {
            throw new IllegalArgumentException("Invalid position");
        }
        return arrayBoard[p.getColumn()][p.getRow()];
    }

    /**
     * Locates the free position(the row) in the given column
     * @param  column  
     * @return  row   
     */
    public int getFreeRow(int column) throws IllegalArgumentException {
        int row = -1;
        if (!(( column < Board.COLUMNS ) && ( column >= 0))) {
            throw new IllegalArgumentException("Invalid column");
        }
        else {
            for (int i = 1; i < Board.ROWS ; i ++) {
                if ((arrayBoard[column][i-1] == null) && (!(arrayBoard[column][i] == null))){
                    row = (i-1);
                }
                if (( i == (Board.ROWS-1)) && (arrayBoard[column][i] == null)) {
                    row = i;
                }
            }
        }
        return row;
    }

    /**
     * Checks if the board is full
     *
     *@return TRUE if full board 
     */
    public boolean isFull() {
       boolean fullBoard = false;
       for (int i = 0; i < Board.COLUMNS ; i ++) {
           for (int j = 0; j < Board.ROWS ; j ++) {
               if (arrayBoard[i][j] == null) {
                   fullBoard = false;
                   i = Board.COLUMNS;
                   break;
                }
                if (!(arrayBoard[i][j] == null)) {
                    fullBoard = true;
                }
            }
        }
        return fullBoard;       
    }

   /**
    * Print board
    *
    * @override     toString in class java.lang.Object
    *
    * @return textBoard
    *
    */
   public String toString() {
        String textBoard = "";  
        for (int i = 0; i < Board.ROWS ; i ++) {
            for (int j = 0; j < Board.COLUMNS; j ++) {
                if (arrayBoard[j][i] == null) {
                	textBoard = textBoard + "-";
                } else {
                    if (arrayBoard[j][i] == Disk.HUMAN) {
                    	textBoard = textBoard + "h";
                    }
                    else if (arrayBoard[j][i] == Disk.COMPUTER) {
                    	textBoard = textBoard + "o";
                    }
                }
                if (j == (Board.COLUMNS - 1)) {
                	textBoard = textBoard + " ";
                }
            }
        }
        return textBoard;             
    }

    public int hashCode() {
        return Arrays.deepHashCode(arrayBoard);
    }

    public boolean equals(Object o) {
        return (this.hashCode() == o.hashCode());
    }  
 
 /**
  * Loads a board from a string with disks representation
  *
  * @param stringBoard
  */
 
    public void loadBoard(String stringBoard) throws IllegalArgumentException {
      
        String noSpace = stringBoard.replaceAll(" ","");
        if ((noSpace.length()) != (Board.ROWS * Board.COLUMNS)) {
            System.out.println("The String parameter contains elements that won't be used");
        }
        String newString = "";
        for (int i = 0; i< noSpace.length(); i++) {
            if (( noSpace.charAt(i) == 'h') || ( noSpace.charAt(i) == 'H')) {
                newString += "H";
            }
            if (( noSpace.charAt(i) == 'o') || ( noSpace.charAt(i) == 'O')) {
                newString += "O";
            }
            if ( noSpace.charAt(i) == '-') {
                newString += "-";
            }
        }
        if ((newString.length()) != (Board.ROWS * Board.COLUMNS)) {
            throw new IllegalArgumentException("Invalid dimension for the String parameter");
        }
        else {
            for(int i = 0; i < Board.COLUMNS; i++) {          
                for(int j = 0; j < Board.ROWS; j++){
                    char c = newString.charAt(i + (j*Board.COLUMNS));              
                    //Character.toLowerCase(c);              
                    switch(c){
                        case 'H':
                        this.arrayBoard[i][j] = Disk.HUMAN;
                        break;                    
                        case 'O':
                        this.arrayBoard[i][j] = Disk.COMPUTER;
                        break;
                        case '-':
                        this.arrayBoard[i][j] = null;
                        break;
                        default:
                        throw new IllegalArgumentException("The charcater " + Character.toString(c) + "is invalid or couldn't be recognised");
                    }
                }
            }
            for (int k = 0; k < Board.COLUMNS ; k++) {
                for (int m = 1; m < Board.ROWS ; m++) {
                    if ((arrayBoard[k][m-1] != null) && (arrayBoard[k][m].equals(null))){
                        System.out.println("Invalid board built");
                    }
                }
            }
        }
    }
    
 /**
  * Saves actual game
  *
  * @param file
  */
    public void save(File file) throws IOException {
        FileWriter w = new FileWriter(file);
        w.write(this.toString());
        w.close();
    }  
 
 /** 
  * Loads an existing game
  * @param file 
  */
    public void load(File file) throws IOException {
        String futureBoard = "";
        FileReader r = new FileReader(file);
        char[] c = new char[1024];
        while (true) {
            int read = r.read(c);
            if (read < 0) {
                break;
            }
            futureBoard += new String(c, 0, read);
        }    
        this.loadBoard(futureBoard);
        r.close();
    }
}