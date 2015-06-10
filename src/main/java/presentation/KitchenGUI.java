package presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import domain.Order;
import domain.Product;
import businesslogic.OrderManager;

public class KitchenGUI extends JPanel {
	private OrderManager manager;
	private JFrame frame;
	
	private DefaultListModel newOrderListModel = new DefaultListModel();
	private DefaultListModel acceptedOrderListModel = new DefaultListModel();
	private DefaultListModel mealListModel = new DefaultListModel();
	private DefaultListModel managingMealListModel = new DefaultListModel();
	
	private JList newOrderList = new JList(newOrderListModel);
	private JList acceptedOrderList = new JList(acceptedOrderListModel);
	private JList mealList = new JList(mealListModel);
	private JList managingMealList = new JList(managingMealListModel);
	
	private JScrollPane newOrderPane = new JScrollPane(newOrderList);
	private JScrollPane acceptedOrderPane = new JScrollPane(acceptedOrderList);
	private JScrollPane mealPane = new JScrollPane(mealList);
	private JScrollPane managingMealPane = new JScrollPane(managingMealList);
	
	private JButton acceptOrder, specificationMeal, ready, newMeal, changeMeal, deleteMeal;
	
	public KitchenGUI(OrderManager manager, JFrame frame) {
		this.manager = manager;
		this.frame = frame;
		
		createKitchenGUI();
	}
	
	public void createKitchenGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JTabbedPane tabbedPane = new JTabbedPane();

		JComponent orderOverview = new JPanel();
		
		JComponent mealManaging = new JPanel();
		
		createOrderOverview(orderOverview);

		createMealManaging(mealManaging);
		
		tabbedPane.addTab("Bestellingsoverzicht", orderOverview);
		tabbedPane.addTab("Gerechtenbeheer", mealManaging);
		
		add(tabbedPane);
	}
	
	public void createOrderOverview(JComponent orderOverview) {
		manager.retrieveOrders();
		manager.retrieveProducts();
		
		orderOverview.setLayout(new BorderLayout(10, 10));
		orderOverview.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout(10, 10));
		
		JPanel west = new JPanel();
		west.setLayout(new BorderLayout(10, 10));
		
		JPanel westNorth = new JPanel();
		JPanel westCenter = new JPanel();
		
		westNorth.setLayout(new BoxLayout(westNorth, BoxLayout.PAGE_AXIS));
		westCenter.setLayout(new BoxLayout(westCenter, BoxLayout.Y_AXIS));
		
		acceptOrder = new JButton("Accepteren");
		acceptOrder.addActionListener(acceptActionListener);
		specificationMeal = new JButton("Gerechtspecificaties");
		specificationMeal.addActionListener(specificationActionListener);
	
		ready = new JButton("Gereed");
		ready.addActionListener(readyActionListener);
		setButtonEnabled();
		
		newOrderList.setFixedCellHeight(25);
		newOrderList.setFixedCellWidth(100);
		acceptedOrderList.setFixedCellHeight(25);
		acceptedOrderList.setFixedCellWidth(100);
		
		newOrderList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		newOrderList.setLayoutOrientation(JList.VERTICAL);
		newOrderList.setVisibleRowCount(5);
		newOrderList.addListSelectionListener(newOrderSelectionListener);
		
		acceptedOrderList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		acceptedOrderList.setLayoutOrientation(JList.VERTICAL);
		acceptedOrderList.setVisibleRowCount(5);
		acceptedOrderList.addListSelectionListener(acceptedOrderSelectionListener);
		
		fillLists();
		
		westNorth.add(new JLabel("Nieuw:"));
		westNorth.add(newOrderPane);
		
		westCenter.add(new JLabel("Geaccepteerd:"));
		westCenter.add(acceptedOrderPane);
		
		west.add(westNorth, BorderLayout.NORTH);
		west.add(westCenter, BorderLayout.CENTER);
		
		south.add(acceptOrder, BorderLayout.WEST);
		south.add(specificationMeal, BorderLayout.EAST);
		south.add(ready, BorderLayout.CENTER);
		
		orderOverview.add(west, BorderLayout.WEST);
		orderOverview.add(mealPane, BorderLayout.CENTER);		
		orderOverview.add(south, BorderLayout.SOUTH);
	}
	
	public void fillLists() {
		newOrderListModel.clear();
		acceptedOrderListModel.clear();
		
		ArrayList<Order> orders = manager.getOrders();
		
		for(Order order: orders) {
			if(order.getStatus() == 1) {
				newOrderListModel.addElement("Bestelnr: " + order.getOrderNr());
			} else if(order.getStatus() == 2){
				acceptedOrderListModel.addElement("Bestelnr: " + order.getOrderNr());
			}
		}
	}
	
	
	public void createMealManaging(JComponent mealManaging) {
		
		
		mealManaging.setLayout(new BorderLayout(10, 10));
		mealManaging.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
		 JScrollPane scrollableList = new JScrollPane(managingMealList);
		
		JPanel south = new JPanel();
		south.setLayout(new BorderLayout(10, 10));
		
		newMeal = new JButton("Nieuw");
		newMeal.addActionListener(newMealActionListener);
		changeMeal = new JButton("Wijzigen");
		changeMeal.addActionListener(changeMealActionListener);
		deleteMeal = new JButton("Verwijderen");
		deleteMeal.addActionListener(deleteMealActionListener);
	
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(100, 50);
		managingMealList.setMinimumSize(minimumSize);
		mealList.setMinimumSize(minimumSize);
		
		managingMealList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		managingMealList.setLayoutOrientation(JList.VERTICAL);
		managingMealList.setVisibleRowCount(-1);
		//managingMealList.addListSelectionListener(selectionListener);
		
				
		
		south.add(newMeal, BorderLayout.CENTER);
		south.add(changeMeal, BorderLayout.WEST);
		south.add(deleteMeal, BorderLayout.EAST);
			
		
		
		mealManaging.add(south, BorderLayout.SOUTH);
		mealManaging.add(scrollableList, BorderLayout.CENTER);	
	}
	
	public void setButtonEnabled() {
		acceptOrder.setEnabled(false);
		specificationMeal.setEnabled(false);
		ready.setEnabled(false);
		
		if(!newOrderList.isSelectionEmpty()) {
			String selectedOrder = (String) newOrderList.getSelectedValue();
			String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);
			
			int orderNr = Integer.parseInt(nr);
			
			specificationMeal.setEnabled(true);
			if((manager.searchOrder(orderNr)).getStatus() == 1) {
				acceptOrder.setEnabled(true);	
			} else if((manager.searchOrder(orderNr)).getStatus() == 1) {
				ready.setEnabled(true);
			}
		}
	}
	
	ListSelectionListener newOrderSelectionListener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				mealListModel.clear();
				
				String selectedOrder = (String) newOrderList.getSelectedValue();
				
				if(selectedOrder != null) {
					String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);
					
					int orderNr = Integer.parseInt(nr);
					
					ArrayList<Product> orderProducts = manager.getProducts(orderNr);
					
					for(Product product: orderProducts) {
						mealListModel.addElement(product.getName());
					}
				}
			}
			
			setButtonEnabled();
		}
	};
	
	ListSelectionListener acceptedOrderSelectionListener = new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (e.getValueIsAdjusting() == false) {
				mealListModel.clear();
				
				String selectedOrder = (String) acceptedOrderList.getSelectedValue();
				
				if(selectedOrder != null) {
					String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);
					
					int orderNr = Integer.parseInt(nr);
					
					ArrayList<Product> orderProducts = manager.getProducts(orderNr);
					
					for(Product product: orderProducts) {
						mealListModel.addElement(product.getName());
					}
				}
			}
			
			setButtonEnabled();
		}
	};
	
	ActionListener acceptActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!newOrderList.isSelectionEmpty()) {
				String selectedOrder = (String) newOrderList.getSelectedValue();
				String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);
				
				int orderNr = Integer.parseInt(nr);
				System.out.println(orderNr);
				boolean result = manager.acceptOrder(orderNr);
				
				if(result == true) {
					newOrderList.clearSelection();
					fillLists();
				}
				
				setButtonEnabled();
			}
		}
	};
	
	ActionListener readyActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!newOrderList.isSelectionEmpty()) {
				String selectedOrder = (String) newOrderList.getSelectedValue();
				String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);
				
				int orderNr = Integer.parseInt(nr);
				System.out.println(orderNr);
				manager.readyOrder(orderNr);
				
				setButtonEnabled();
			}
		}
	};

	ActionListener specificationActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!mealList.isSelectionEmpty()) {
				String selectedMeal = (String) mealList.getSelectedValue();
				String productName = selectedMeal.substring(selectedMeal.lastIndexOf(' ') + 1);
				
				specificationMenu(productName);
			}
		}
		
	};
	
	ActionListener newMealActionListener = new ActionListener(){
	public void actionPerformed(ActionEvent e){
		if(!mealList.isSelectionEmpty()) {
			String selectedMeal = (String) mealList.getSelectedValue();
			String productName = selectedMeal.substring(selectedMeal.lastIndexOf(' ') + 1);
			
			newMealMenu(productName);
		}
	}
	};
	
	ActionListener changeMealActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(!mealList.isSelectionEmpty()) {
				String selectedMeal = (String) mealList.getSelectedValue();
				String productName = selectedMeal.substring(selectedMeal.lastIndexOf(' ') + 1);
				
				changeMealMenu(productName);
			}
		}
		};
		
		ActionListener deleteMealActionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
			};
	
	
	public void specificationMenu(String productName) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.getContentPane().removeAll();
		frame.setTitle("Gerechtspecificaties");
				
		JPanel paneel = new SpecificationGUI(manager, frame, productName);
		
		frame.setContentPane(paneel);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();		
	}
	
	public void newMealMenu(String productName) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				
				frame.getContentPane().removeAll();
				frame.setTitle("Gerecht toevoegen");
						
				JPanel paneel = new SpecificationGUI(manager, frame, productName);
				
				frame.setContentPane(paneel);
				frame.setVisible(true);
				frame.validate();
				frame.repaint();
	
	
	}
	
	public void changeMealMenu(String productName) {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				
				frame.getContentPane().removeAll();
				frame.setTitle("Gerecht wijzigen");
						
				JPanel paneel = new SpecificationGUI(manager, frame, productName);
				
				frame.setContentPane(paneel);
				frame.setVisible(true);
				frame.validate();
				frame.repaint();
	
				
	}
				
}
