package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import domain.Product;
import businesslogic.OrderManager;


public class SpecificationGUI extends JPanel{
	private OrderManager manager;
	private JFrame frame;
	private JButton backButton;
	private String productName;
	private JLabel name, ingredient, time, nameRight, timeRight;
	
	
	public SpecificationGUI(OrderManager manager, JFrame frame, String productName) {
		this.manager = manager;
		this.frame = frame;
		this.productName = productName;
		
		createSpecificationGUI();
	}
	public void createSpecificationGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		String[] columnNames = {"Ingrediënt",
        "Hoeveelheid", "Eenheid"};
Object[][] data = {
	    {"Tomaat", 2,"Schijfjes" },
	    {"Sla", 20, "Gram"},
	    {"Kaas", 1, "Plakje"},
	    {"Ham", 2, "Plakjes"},
	    {"Komkommer", 4 , "Schijfjes"}
	};


		
		
		JTable table = new JTable(data, columnNames);
		Dimension preferredSize = new Dimension(100, 60);
		table.setPreferredSize(preferredSize);;
		
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		JPanel productOverview = new JPanel();
		JPanel west = new JPanel();
		JPanel innersouth = new JPanel();
		JPanel southRight = new JPanel();
		
		Product product = manager.searchProduct(productName);
		long prepTime = product.getPreparationTime();
		timeRight = new JLabel(prepTime + " minuten");
		
		name = new JLabel("Gerechtnaam:");
		nameRight = new JLabel(productName);
		ingredient = new JLabel("Ingrediënten:");
		time = new JLabel("Bereidingstijd:");
		
		
		backButton = new JButton("Terug");
		backButton.addActionListener(buttonActionListener);
		
		west.setLayout(new BorderLayout(10,10));
		
		productOverview.setLayout(new BorderLayout(50,118));
		productOverview.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 20));
		
		innersouth.setLayout(new BorderLayout(10,10));
		
		south.setLayout(new BorderLayout(10, 40));
		
		southRight.setLayout(new BorderLayout(10, 10));
		southRight.setBorder(BorderFactory.createEmptyBorder(0, 0, 35, 0));
		
		center.setLayout(new BorderLayout(50, 0));
			
		
		add(west, BorderLayout.WEST);
		add(productOverview, BorderLayout.CENTER);
		
		productOverview.add(center, BorderLayout.CENTER);
		productOverview.add(southRight, BorderLayout.SOUTH);
		productOverview.add(nameRight, BorderLayout.NORTH);
		
		south.add(innersouth, BorderLayout.SOUTH);
		
		innersouth.add(time, BorderLayout.NORTH);
		innersouth.add(backButton, BorderLayout.SOUTH);
		
		west.add(south, BorderLayout.SOUTH);
		west.add(name, BorderLayout.NORTH);
		west.add(ingredient, BorderLayout.CENTER);
		
		southRight.add(timeRight, BorderLayout.NORTH);
		
		center.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);
		center.add(table, BorderLayout.CENTER);
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
	
	
