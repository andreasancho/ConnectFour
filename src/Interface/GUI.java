package Interface;
import connect4.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

/**
 *
 * @author Lab
 * @version 21.4.2009
 */
public class GUI {

    public static final String TITLE = "Connect Four";


    private JComboBox initialCombo;

    private static final String FIXED_PLAYER = "Fixed player";
    private static final String RANDOM_PLAYER = "Random player";
    private static final String MINIMAX_PLAYER_2 = "Minimax Player (2)";
    private static final String MINIMAX_PLAYER_3 = "Minimax Player (3)";
    private static final String MINIMAX_PLAYER_4 = "Minimax Player (4)";
    private static final String MINIMAX_PLAYER_5 = "Minimax Player (5)";
    private static final String MINIMAX_PLAYER_6 = "Minimax Player (6)";
    private static final String MINIMAX_PLAYER_7 = "Minimax Player (7)";


    private Player player = new MinimaxPlayer(5);
    private Board board = new Board();
    private JLabel[][] slots = new JLabel[Board.COLUMNS][Board.ROWS];
    private JButton[] buttons = new JButton[Board.COLUMNS];
    
    private ImageIcon cellIcon = null;
    private ImageIcon playerIcon = null;
    private ImageIcon computerIcon = null;

    private JFileChooser fileChooser;
    private File file;

    private GUI(Container container) {
        cellIcon = setImage("celda.jpg");
        playerIcon = setImage("fichaverde.jpg");
        computerIcon = setImage("ficharoja.jpg");

        JPanel rejilla = new JPanel();
        rejilla.setLayout(new GridLayout(Board.ROWS + 1, Board.COLUMNS));
        for (int fila = 0; fila < Board.ROWS; fila++) {
            for (int columna = 0; columna < Board.COLUMNS; columna++) {
                JLabel celda = new JLabel();
                celda.setHorizontalAlignment(SwingConstants.CENTER);
                celda.setBorder(new LineBorder(java.awt.Color.BLACK));
                celda.setIcon(cellIcon);
                slots[columna][fila] = celda;
                rejilla.add(celda);
            }
        }
        for (int c = 0; c < Board.COLUMNS; c++) {
            buttons[c] = new JButton(String.valueOf(c));
            buttons[c].addMouseListener(new Connect4Listener(c));
            rejilla.add(buttons[c]);
        }
        JPanel aux = new JPanel();
        aux.add(rejilla);
        container.add(aux, BorderLayout.CENTER);

        JPanel este = new JPanel();
        este.setBackground(java.awt.Color.BLACK);
        container.add(este, BorderLayout.EAST);

        JPanel oeste = new JPanel();
        oeste.setBackground(java.awt.Color.BLACK);
        container.add(oeste, BorderLayout.WEST);

        String[] initial = new String[]{
                "Begins " + Disk.HUMAN,
                "Begins " + Disk.COMPUTER
        };
        initialCombo = new JComboBox(initial);
        initialCombo.setSelectedIndex(0);
        initialCombo.setMaximumSize(initialCombo.getMinimumSize());
        initialCombo.addActionListener(new NewAction());

        String[] jugadoresTipo = {
                FIXED_PLAYER,
                RANDOM_PLAYER,
                MINIMAX_PLAYER_2,
                MINIMAX_PLAYER_3,
                MINIMAX_PLAYER_4,
                MINIMAX_PLAYER_5,
                MINIMAX_PLAYER_6,
                MINIMAX_PLAYER_7
        };
        JComboBox comboJugadores = new JComboBox(jugadoresTipo);
        comboJugadores.setSelectedIndex(5);
        comboJugadores.setMaximumSize(comboJugadores.getMinimumSize());
        comboJugadores.addActionListener(new PlayerListener());

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new LoadAction());
        toolBar.add(new SaveAction());
        toolBar.add(initialCombo);
        toolBar.add(comboJugadores);
        container.add(toolBar, BorderLayout.NORTH);
    }

    /**
     * Constructor.
     *
     * @param applet Applet.
     */
    public GUI(JApplet applet) {
        this(applet.getContentPane());
    }

    /**
     * Constructor.
     *
     * @param frame Pantalla en consola.
     */
    public GUI(JFrame frame) {
        this(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates new game.
     */
    private void newGame() {
        Board b = new Board();
        for (int row = 0; row < Board.ROWS; row++) {
            for (int c = 0; c < Board.COLUMNS; c++) {
                slots[c][row].setIcon(cellIcon);
            }
        }
        if (initialCombo.getSelectedIndex() == 1) {
            Player ran = new RandomPlayer();
            int c = ran.decideColumn(b);
            int r = b.insertDisk(c, Disk.COMPUTER);
            slots[c][r].setIcon(computerIcon);
        }
    }

    /**
     * Establish image.
     *
     * @param file.
     * @returns icon
     */
    private ImageIcon setImage(String f) {
        try {
        	java.net.URL url = this.getClass().getResource(f);
            return new ImageIcon(url);
        } catch (Exception e) {
            System.err.printf("Could not load %s%s%s%n",
                    getClass().getPackage().getName(),
                    System.getProperty("file.separator"),
                    f);
            return null;
        }
    }

    /**
     * Shows winner
     *
     * @param Disk winner.
     */
    private void showWinner(Disk d) {
        JFrame window = new JFrame(TITLE);
        JOptionPane.showMessageDialog(window,
                "WINS " + d.name(),
                TITLE,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Shows the error
     *
     * @param error message
     */
    private void showError(String message) {
        JFrame window = new JFrame(TITLE);
        JOptionPane.showMessageDialog(window,
                message,
                TITLE,
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Listener.
     */
    private class Connect4Listener
            extends MouseInputAdapter {
        private final int column;

        public Connect4Listener(int c) {
            this.column = c;
        }

        /**
         * Receives the event
         *
         * @param event
         */
        public void mouseClicked(MouseEvent event) {
            try {
                int humanRow = board.getFreeRow(column);
                if (humanRow < 0) {
                    showError(String.format("Full column: %d", column));
                    return;
                }
                if (setDisk(column, humanRow, Disk.HUMAN))
                    return;

                int computerColumn;
                int computerRow;
                do {
                	computerColumn = player.decideColumn(board);
                	computerRow = board.getFreeRow(computerColumn);
                } while (computerRow < 0);
                setDisk(computerColumn, computerRow, Disk.COMPUTER);
            } catch (Exception e) {
                e.printStackTrace();
                showError(e.getMessage());
            }
        }

        /**
         * Tries to insert a disk in the board
         *
         * @param column
         * @param row    
         * @param disk
         * @return TRUE if game is over
         */
        private boolean setDisk(int c, int r, Disk d) {
            board.insertDisk(c, d);
            Icon icon = d == Disk.HUMAN ? playerIcon : computerIcon;
            slots[c][r].setIcon(icon);
            if (AnalyzeBoard.won(d, board)) {
                showWinner(d);
                newGame();
                return true;
            }
            if (board.isFull()) {
                showError("Tablero lleno: no gana nadie");
                newGame();
                return true;
            }
            return false;
        }
    }

    /**
     * Player interpreter
     */
    private class PlayerListener implements ActionListener {

        /**
         * Responsible for event
         * Changes game'level and starts new game
         *
         * @param event 
         */
        public void actionPerformed(ActionEvent event) {
            JComboBox comboBox = (JComboBox) event.getSource();
            String playerName = (String) comboBox.getSelectedItem();
            if (playerName.equals(FIXED_PLAYER)) {
                player = new FixedPlayer();
            } else if (playerName.equals(RANDOM_PLAYER)) {
            	player = new RandomPlayer();
            } else if (playerName.equals(MINIMAX_PLAYER_2)) {
            	player = new MinimaxPlayer(2);
            } else if (playerName.equals(MINIMAX_PLAYER_3)) {
            	player = new MinimaxPlayer(3);
            } else if (playerName.equals(MINIMAX_PLAYER_4)) {
            	player = new MinimaxPlayer(4);
            } else if (playerName.equals(MINIMAX_PLAYER_5)) {
            	player = new MinimaxPlayer(5);
            } else if (playerName.equals(MINIMAX_PLAYER_6)) {
            	player = new MinimaxPlayer(6);
            } else if (playerName.equals(MINIMAX_PLAYER_7)) {
            	player = new MinimaxPlayer(7);
            } else {
            	player = new MinimaxPlayer(5);
            }
            newGame();
        }
    }

    /**
     * New game button interpreter
     */
    private class NewAction
            extends AbstractAction {
        /**
         * Constructor.
         */
        public NewAction() {
            super("New");
        }

        /**
         * Responsible of cliking.
         *
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            newGame();
        }
    }

    /**
     * load button interpreter.
     */
    private class LoadAction
            extends AbstractAction {
        /**
         * Constructor.
         */
        public LoadAction() {
            super("Load");
        }

        /**
         * Responsible for the clicking
         *
         * @param event
         */
        public void actionPerformed(ActionEvent event) {
            try {
                if (fileChooser == null)
                    fileChooser = new JFileChooser();
                if (file != null)
                    fileChooser.setSelectedFile(file);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    board.load(file);
                    for (int c = 0; c < Board.COLUMNS; c++) {
                        for (int r = 0; r < Board.ROWS; r++) {
                            Disk d = board.getDisk(new Position(c, r));
                            if (d == null)
                                slots[c][r].setIcon(cellIcon);
                            else if (d == Disk.HUMAN)
                                slots[c][r].setIcon(playerIcon);
                            else if (d == Disk.COMPUTER)
                                slots[c][r].setIcon(computerIcon);
                        }
                    }
                }
            } catch (Exception exception) {
                showError(exception.toString());
            }
        }
    }

    /**
     * Save button interpreter
     */
    private class SaveAction
            extends AbstractAction {
        /**
         * Constructor.
         */
        public SaveAction() {
            super("Save");
        }

        /**
         * Se hace cargo de la pulsacion.
         *
         * @param event evento que dispara la accion.
         */
        public void actionPerformed(ActionEvent event) {
            try {
                if (fileChooser == null)
                    fileChooser = new JFileChooser();
                if (file != null)
                    fileChooser.setSelectedFile(file);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    board.save(file);
                }
            } catch (Exception exception) {
                showError(exception.toString());
            }
        }
    }
}