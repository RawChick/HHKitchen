package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import businesslogic.OrderManager;



public class NewMealGUI extends JPanel {
	private OrderManager manager;
	private JFrame frame;
	private JButton backButton, updateButton;
	private int productNr;
	private JLabel name, ingredient, time, price;
	private JTextField nameRight, timeRight, priceRightText;
	String columnNames[] = {"Nr", "Ingredient", "Hoeveelheid", "Eenheid"};
	String[] menuItems = { "1 Voorgerechten", "2 Soepen", "3 Hoofdgerechten", "4 Nagerechten", "6 Erbij", "7 Salades", "8 Pizza" };
	DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column == 0 || column == 3) {
				return false;
			} else {
				return true;
			}
	    }
	};
	
	JTable table = new JTable(tableModel);
	JComboBox menuList = new JComboBox(menuItems);
	
	public NewMealGUI(OrderManager manager, JFrame frame) {
		this.manager = manager;
		this.frame = frame;
		
		createNewMealGUI();
	}

	public void createNewMealGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
			
	/*	for(ProductIngredients productIngredient: productIngredients) {
			int ingredientNr = productIngredient.getIngredientNr();
			String ingredientName = productIngredient.getIngredientName();
			int ingredientQuantity = productIngredient.getQuantity();
			String ingredientUnit = productIngredient.getUnit();
			
			Object[] data = {ingredientNr, ingredientName, ingredientQuantity, ingredientUnit};
			
			tableModel.addRow(data);
		}
		
	*/	table.getModel().addTableModelListener(tableModelListener);

		JPanel center = new JPanel();
		JPanel south = new JPanel();
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
		JPanel tablePanel = new JPanel();

	

		timeRight = new JTextField("");
		name = new JLabel("Gerechtnaam:");
		nameRight = new JTextField("");
		ingredient = new JLabel("Ingrediënten:");
		time = new JLabel("Bereidingstijd:");
		price = new JLabel("Prijs in centen: ");


		priceRightText = new JTextField("");

		backButton = new JButton("Terug");
		backButton.addActionListener(buttonActionListener);
		
		updateButton = new JButton("Opslaan");
		updateButton.addActionListener(updateActionListener);

		north.setLayout(new BorderLayout(10, 10));
		south.setLayout(new BorderLayout(10, 10));
		center.setLayout(new GridLayout(2, 1));
		west.setLayout(new GridLayout(2, 1));
		innercenter.setLayout(new BorderLayout(10, 10));
		innercenter.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		innercenterwest.setLayout(new BorderLayout(10, 10));
		innercenterwest.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
		tablePanel.setLayout(new BorderLayout(10,10));
		gridsouth.setLayout(new BorderLayout(10, 10));
		westsouth.setLayout(new BorderLayout(10, 10));
		innersouth.setLayout(new BorderLayout(10, 10));
		innersouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		innernorth.setLayout(new BorderLayout(10, 10));
		innerwestnorth.setLayout(new BorderLayout(10, 10));
		innerwestsouth.setLayout(new BorderLayout(10, 10));
		innerwestsouth.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		north.add(name, BorderLayout.WEST);
		innercenterwest.add(ingredient);
		west.add(innercenterwest);
north.add(menuList, BorderLayout.SOUTH);
		south.add(updateButton, BorderLayout.WEST);
		south.add(backButton, BorderLayout.EAST);
		north.add(nameRight, BorderLayout.CENTER);
		center.add(innercenter);
		tablePanel.add(table.getTableHeader(), BorderLayout.BEFORE_FIRST_LINE);
		tablePanel.add(table, BorderLayout.CENTER);
		innercenter.add(tablePanel);
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
			createKitchenGUI();		
			
			System.out.println("Geannuleerd");
			}
	};
	
	ActionListener updateActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String selectedMenu = menuList.getSelectedItem().toString();
			String nr = selectedMenu.substring(0, selectedMenu.indexOf(" "));
			int menuID = Integer.parseInt(nr); 
			String name = nameRight.getText();
			long prepTime = Long.parseLong(timeRight.getText());
			long price = Long.parseLong(priceRightText.getText());
			
		manager.newProduct(name, price, menuID, prepTime);
		
		createKitchenGUI();
			
			
		}
	};
	
	
	TableModelListener tableModelListener = new TableModelListener() {
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				int col = table.getSelectedColumn();
				int row = table.getSelectedRow();
				
				manager.updateIngredientSpecs(table, col, row, productNr);
			}
		}
	};
	
	public void createKitchenGUI(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		frame.getContentPane().removeAll();
		frame.setTitle("Keuken");
		JPanel paneel = new KitchenGUI(manager, frame);
		frame.setContentPane(paneel);
		frame.validate();
		frame.repaint();
	}
}
