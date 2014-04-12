package Interface;

import javax.swing.*;

/**
 * Graphic Interface of the Connect-4 game
 *
 * @author Lab
 * @version 23.3.2009
 */
public class GraphicApp
        extends JApplet {

    public void init() {
        new GUI(this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(GUI.TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new GUI(frame);
    }
}