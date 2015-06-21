package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.Product;
import domain.ProductIngredients;
import businesslogic.OrderManager;

/**
 * 
 * This class contains the SpecificationGUI and creates the kitchenGUI.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @version 1.0
 * 
 */

public class SpecificationGUI extends JPanel{
	private OrderManager manager;
	private JFrame frame;
	private JButton backButton;
	private int productNr;
	private JLabel name, ingredient, time, nameRight, timeRight;
	
	
	public SpecificationGUI(OrderManager manager, JFrame frame, int productNr) {
		this.manager = manager;
		this.frame = frame;
		this.productNr = productNr;
		
		createSpecificationGUI();
	}
	
	public void createSpecificationGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		String columnNames[] = {"Ingredient", "Hoeveelheid", "Eenheid"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
		    }
		};
		
		JTable table = new JTable(tableModel);
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		
		ArrayList<ProductIngredients> productIngredients = manager.retrieveIngredients(productNr);
		
		for(ProductIngredients productIngredient: productIngredients) {
			String ingredientName = productIngredient.getIngredientName();
			int ingredientQuantity = productIngredient.getQuantity();
			String ingredientUnit = productIngredient.getUnit();
			
			Object[] data = {ingredientName, ingredientQuantity, ingredientUnit};
			
			tableModel.addRow(data);
		}
		
		Dimension preferredSize = new Dimension(100, 60);
		table.setPreferredSize(preferredSize);
		
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		JPanel tablepanel = new JPanel();
		JPanel west = new JPanel();
		
		Product product = manager.searchProduct(productNr);
		long prepTime = product.getPreparationTime();
		timeRight = new JLabel(prepTime + " minuten");
		
		name = new JLabel("Gerechtnaam:");
		nameRight = new JLabel(product.getName());
		ingredient = new JLabel("Ingrediënten:");
		time = new JLabel("Bereidingstijd:");
		
		
		backButton = new JButton("Terug");
		backButton.addActionListener(buttonActionListener);
		
			
		south.setLayout(new BorderLayout(10, 10));
		center.setLayout(new GridLayout(3,1));
		west.setLayout(new GridLayout(3,1));
		tablepanel.setLayout(new BorderLayout(10,10));
		
		west.add(name);
		west.add(ingredient);
		west.add(time);
		south.add(backButton, BorderLayout.EAST);
		tablepanel.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);
		tablepanel.add(table, BorderLayout.CENTER);
		center.add(nameRight);
		center.add(tablepanel);
		center.add(timeRight);
		
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
	
	
