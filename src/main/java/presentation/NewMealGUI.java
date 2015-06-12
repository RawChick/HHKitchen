package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import domain.Product;
import businesslogic.OrderManager;


public class NewMealGUI extends JPanel{
	private OrderManager manager;
	private JFrame frame;
	private JButton backButton;
	private JLabel name, ingredient, time, price;
	private JTextField nameRight, timeRight, priceRightText;
	
	
	public NewMealGUI(OrderManager manager, JFrame frame) {
		this.manager = manager;
		this.frame = frame;
		
		createNewMealGUI();
	}
	public void createNewMealGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		String[] columnNames = {"Ingrediënt",
        "Hoeveelheid", "Eenheid"};
Object[][] data = {
	    {"Tomaat", 2,"Schijfjes" }
	};


		
		
		JTable table = new JTable(data, columnNames);
		Dimension preferredSize = new Dimension(100, 60);
		table.setPreferredSize(preferredSize);;
		
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		JPanel tablepanel = new JPanel();
		JPanel west = new JPanel();
		JPanel north = new JPanel();
		JPanel gridsouth = new JPanel();
		JPanel westsouth = new JPanel();
		JPanel innernorth = new JPanel();
		JPanel innersouth = new JPanel();
		JPanel innerwestnorth = new JPanel();
		JPanel innerwestsouth = new JPanel();
		JPanel innercenter = new JPanel();
		JPanel innercenterwest = new JPanel();
		
	
		timeRight = new JTextField();	
		name = new JLabel("Gerechtnaam:");
		nameRight = new JTextField();
		ingredient = new JLabel("Ingrediënten:");
		time = new JLabel("Bereidingstijd:");
		price = new JLabel("Prijs in centen: ");
		priceRightText = new JTextField();
		
		backButton = new JButton("Terug");
		backButton.addActionListener(buttonActionListener);
		
		north.setLayout(new BorderLayout(10,10));
		south.setLayout(new BorderLayout(10, 10));
		center.setLayout(new GridLayout(2,1));
		west.setLayout(new GridLayout(2,1));
		innercenter.setLayout(new BorderLayout(10,10));
		innercenter.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		innercenterwest.setLayout(new BorderLayout(10,10));
		innercenterwest.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		tablepanel.setLayout(new BorderLayout(10,10));
		gridsouth.setLayout(new BorderLayout(10,10));
		westsouth.setLayout(new BorderLayout(10,10));
		innersouth.setLayout(new BorderLayout(10,10));
		innersouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		innernorth.setLayout(new BorderLayout(10,10));
		innerwestnorth.setLayout(new BorderLayout(10,10));
		innerwestsouth.setLayout(new BorderLayout(10,10));
		innerwestsouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		north.add(name, BorderLayout.WEST);
		innercenterwest.add(ingredient);
		west.add(innercenterwest);
		
		south.add(backButton, BorderLayout.EAST);
		tablepanel.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);
		tablepanel.add(table, BorderLayout.CENTER);
		north.add(nameRight, BorderLayout.CENTER);
		center.add(innercenter);
		innercenter.add(tablepanel);
		gridsouth.add(innernorth, BorderLayout.NORTH);
		gridsouth.add(innersouth, BorderLayout.SOUTH);
		westsouth.add(innerwestnorth, BorderLayout.NORTH);
		westsouth.add(innerwestsouth, BorderLayout.SOUTH);
		innersouth.add(timeRight, BorderLayout.NORTH);
		
		innerwestsouth.add(time, BorderLayout.NORTH);
		innerwestsouth.add(price, BorderLayout.SOUTH);
		innersouth.add(priceRightText, BorderLayout.SOUTH);
		west.add(westsouth);
		center.add(gridsouth);
		
		add(north, BorderLayout.NORTH);
		add(west, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
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

