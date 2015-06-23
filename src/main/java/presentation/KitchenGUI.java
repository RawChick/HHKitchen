package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import businesslogic.OrderManager;
import domain.Order;
import domain.OrderRow;
import domain.Product;


/**
 * 
 * This class contains the kitchenGUI and creates the ChangeMealGUI, NewMealGUI and the SpecificationGUI.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @version 1.0
 * 
 */

public class KitchenGUI extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
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
        manager.retrieveIngredients();

        Timer timer = new Timer();
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
        acceptedOrderList.setFixedCellHeight(25);

        newOrderList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        newOrderList.setLayoutOrientation(JList.VERTICAL);
        newOrderList.setVisibleRowCount(5);
        newOrderList.addListSelectionListener(newOrderSelectionListener);

        acceptedOrderList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        acceptedOrderList.setLayoutOrientation(JList.VERTICAL);
        acceptedOrderList.setVisibleRowCount(5);
        acceptedOrderList.addListSelectionListener(acceptedOrderSelectionListener);
        mealList.addListSelectionListener(mealListSelectionListener);

        fillLists();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                addNewOrders();

            }
        }, 10*1000L, 10*1000L);

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

    public void addNewOrders() {
        manager.retrieveNewOrders();
        ArrayList<Order> orders = manager.getNewOrders();

        for(Order order: orders) {
        System.out.println(order.getOrderNr());
        newOrderListModel.addElement("Bestelnr: " + order.getOrderNr());
        }
    }

    public void removeCancelledOrders() {
        manager.retrieveCancelledOrders();
        ArrayList<Order> orders = manager.getCancelledOrders();

        for(Order order: orders) {
        System.out.println(order.getOrderNr());
        newOrderListModel.removeElement("Bestelnr: " + order.getOrderNr());
        }
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


        JPanel south = new JPanel();
        south.setLayout(new BorderLayout(10, 10));

        newMeal = new JButton("Nieuw");
        newMeal.addActionListener(newMealActionListener);
        changeMeal = new JButton("Wijzigen");
        changeMeal.addActionListener(changeMealActionListener);
        deleteMeal = new JButton("Verwijderen");
        deleteMeal.addActionListener(deleteMealActionListener);

        setButtonEnabled();

        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(100, 50);
        managingMealList.setMinimumSize(minimumSize);
        mealList.setMinimumSize(minimumSize);

        managingMealList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        managingMealList.setLayoutOrientation(JList.VERTICAL);
        managingMealList.setVisibleRowCount(-1);
        managingMealList.addListSelectionListener(managingMealListSelectionListener);
        fillProductLists();


        south.add(newMeal, BorderLayout.CENTER);
        south.add(changeMeal, BorderLayout.WEST);
        south.add(deleteMeal, BorderLayout.EAST);



        mealManaging.add(south, BorderLayout.SOUTH);
        mealManaging.add(managingMealPane, BorderLayout.CENTER);	
    }

    public void fillProductLists() {
        managingMealListModel.clear();

        manager.retrieveProducts();
        ArrayList<Product> products = manager.getProducts();

        for (Product product : products) {
        managingMealListModel.addElement(String.format("%02d", product.getProductNr()) + " - " + product.getName());

        }
    }

    public void setButtonEnabled() {
        acceptOrder.setEnabled(false);
        specificationMeal.setEnabled(false);
        ready.setEnabled(false);


        if(!mealList.isSelectionEmpty()){
        specificationMeal.setEnabled(true);
        }

        if(!newOrderList.isSelectionEmpty()) {

        String selectedOrder = (String) newOrderList.getSelectedValue();
        String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

        int orderNr = Integer.parseInt(nr);

        if((manager.searchOrder(orderNr)).getStatus() == 1) {
        acceptOrder.setEnabled(true);	
        } 
        }

        if(!acceptedOrderList.isSelectionEmpty()){

        String selectedOrder = (String) acceptedOrderList.getSelectedValue();
        String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

        int orderNr = Integer.parseInt(nr);

        if((manager.searchOrder(orderNr)).getStatus() == 2) {
        ready.setEnabled(true);	
        } 
        }

        if(!managingMealList.isSelectionEmpty()){
        changeMeal.setEnabled(true);
        deleteMeal.setEnabled(true);
        }

    }

    ListSelectionListener newOrderSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
            mealListModel.clear();
            acceptedOrderList.clearSelection();

            String selectedOrder = (String) newOrderList.getSelectedValue();

            if(selectedOrder != null) {
            String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

            int orderNr = Integer.parseInt(nr);

            ArrayList<OrderRow> orderRowsProducts = manager.getProducts(orderNr);

            for(OrderRow orderRow: orderRowsProducts) {
            Product product = manager.searchProduct(orderRow.getProductNr());

            mealListModel.addElement(String.format("%02d", product.getProductNr()) +" - "+orderRow.getAmount()+"x - "+product.getName()+" ("+product.getPreparationTime()+" min)");
            }
            }
            }

            setButtonEnabled();
        }
    };

    ListSelectionListener mealListSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            setButtonEnabled();
        }
    };


    ListSelectionListener acceptedOrderSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {
            mealListModel.clear();
            newOrderList.clearSelection();

            String selectedOrder = (String) acceptedOrderList.getSelectedValue();

            if(selectedOrder != null) {
            String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

            int orderNr = Integer.parseInt(nr);

            ArrayList<OrderRow> orderRowsProducts = manager.getProducts(orderNr);

            for(OrderRow orderRow: orderRowsProducts) {
            Product product = manager.searchProduct(orderRow.getProductNr());

            mealListModel.addElement(String.format("%02d", product.getProductNr()) +" - "+orderRow.getAmount()+"x - "+product.getName()+" ("+product.getPreparationTime()+" min)");
            }
            }
            }

            setButtonEnabled();
        }
    };

    ListSelectionListener managingMealListSelectionListener = new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {

            setButtonEnabled();
            }
        }
    };


    ActionListener acceptActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(!newOrderList.isSelectionEmpty()) {
            String selectedOrder = (String) newOrderList.getSelectedValue();
            String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

            int orderNr = Integer.parseInt(nr);
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
            if(!acceptedOrderList.isSelectionEmpty()) {
            String selectedOrder = (String) acceptedOrderList.getSelectedValue();
            String nr = selectedOrder.substring(selectedOrder.lastIndexOf(' ') + 1);

            int orderNr = Integer.parseInt(nr);

            boolean result = manager.readyOrder(orderNr);

            if(result == true) {
            acceptedOrderList.clearSelection();
            fillLists();
            }

            setButtonEnabled();
            }
        }
    };

    ActionListener specificationActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if(!mealList.isSelectionEmpty()) {
            String selectedMeal = (String) mealList.getSelectedValue();
            String nr = selectedMeal.substring(0, selectedMeal.indexOf(" "));
            int productNr = Integer.parseInt(nr);


            specificationMenu(productNr);
            }
        }

    };

    ActionListener newMealActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {

            newMealMenu();

        }
    };

    ActionListener changeMealActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!managingMealList.isSelectionEmpty()) {
            String selectedMeal = (String) managingMealList.getSelectedValue();
            String nr = selectedMeal.substring(0, selectedMeal.indexOf(" "));
            int productNr = Integer.parseInt(nr);

            changeMealMenu(productNr);
            }
        }
    };

    ActionListener deleteMealActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!managingMealList.isSelectionEmpty()) {

            String selectedMeal = (String) managingMealList.getSelectedValue();
            String nr = selectedMeal.substring(0, selectedMeal.indexOf(" "));
            int productNr = Integer.parseInt(nr);
            manager.deleteProduct(productNr);

            fillProductLists();
            }

        }
    };


    public void specificationMenu(int productNr) {

        frame.getContentPane().removeAll();
        frame.setTitle("Gerechtspecificaties");

        JPanel paneel = new SpecificationGUI(manager, frame, productNr);

        frame.setContentPane(paneel);
        frame.setVisible(true);
        frame.validate();
        frame.repaint();		
    }

    public void newMealMenu() {


        frame.getContentPane().removeAll();
        frame.setTitle("Gerecht toevoegen");

        JPanel paneel = new NewMealGUI(manager, frame);

        frame.setContentPane(paneel);
        frame.setVisible(true);
        frame.validate();
        frame.repaint();

    }

    public void changeMealMenu(int productNr) {


        frame.getContentPane().removeAll();
        frame.setTitle("Gerecht wijzigen");

        JPanel paneel = new ChangeMealGUI(manager, frame, productNr);

        frame.setContentPane(paneel);
        frame.setVisible(true);
        frame.validate();
        frame.repaint();
    }

}
