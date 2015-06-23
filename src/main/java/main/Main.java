package main;

import javax.swing.*;

import java.awt.*;

import presentation.LoginGUI;
import businesslogic.OrderManager;

/**
 * 
 * This class contains the main method. This method is used to create the OrderManager and the LoginGUI.
 * 
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @see presentation.LoginGUI
 * @version 1.0
 */

// Comment toegevoegd
public class Main extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        OrderManager manager = new OrderManager();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new Main();

        frame.setSize(200, 275);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Inloggen");
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        JPanel paneel = new LoginGUI(manager, frame);
        frame.setContentPane(paneel);
        frame.setVisible( true );
    }

}
