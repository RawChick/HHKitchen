package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import businesslogic.OrderManager;


public class ChangeMealGUI extends JPanel{
	private OrderManager manager;
	private JFrame frame;
	private JButton backButton;

	
	
	public ChangeMealGUI(OrderManager manager, JFrame frame, int productNr) {
		this.manager = manager;
		this.frame = frame;
		
		createChangeMealGUI();
	}
	public void createChangeMealGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout(10, 40));
		
		backButton = new JButton("Terug");
		backButton.addActionListener(buttonActionListener);
		
		south.add(backButton);
		add(south, BorderLayout.SOUTH);
	}
	
	
	ActionListener buttonActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			
			frame.getContentPane().removeAll();
			frame.setTitle("Keuken");
			JPanel paneel = new KitchenGUI(manager, frame);
			frame.setContentPane(paneel);
			frame.validate();
			frame.repaint();
		}
	};
}

