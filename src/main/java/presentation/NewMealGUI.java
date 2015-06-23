package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businesslogic.OrderManager;
import domain.Ingredient;


/**
 * 
 * This class contains the NewMealGUI and creates the kitchenGUI.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @version 1.0
 * 
 */

public class NewMealGUI extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private OrderManager manager;
    private JFrame frame;
    private JButton backButton, updateButton;
    private DefaultListModel ingredientListModel = new DefaultListModel();
    private JList ingredientList = new JList(ingredientListModel);
    private JScrollPane scrollPane = new JScrollPane(ingredientList);
    private JLabel name, description, time, price, category, panelTitle, ingredientTitle;
    private JTextField nameRight, timeRight, priceRight, descriptionRight;
    private String[] menuItems = { "1 Voorgerechten", "2 Soepen", "3 Hoofdgerechten", "4 Nagerechten", "6 Erbij", "7 Salades", "8 Pizza" };
    private JComboBox menuList = new JComboBox(menuItems);
    private List<ArrayList> allElements = new ArrayList<ArrayList>();
    private Font font1 = new Font("Arial", Font.BOLD, 20);

    public NewMealGUI(OrderManager manager, JFrame frame) {
        this.manager = manager;
        this.frame = frame;


        createNewMealGUI();
    }

    public void createNewMealGUI() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel north = new JPanel();
        JPanel northNorth = new JPanel();
        JPanel northCenter = new JPanel();
        JPanel south = new JPanel();
        JPanel southNorth = new JPanel();
        JPanel southCenter = new JPanel();
        JPanel southSouth = new JPanel();
        JPanel southSouthEast = new JPanel();
        JPanel southSouthWest = new JPanel();

        north.setLayout(new BorderLayout(5, 5));
        northNorth.setLayout(new FlowLayout(FlowLayout.LEADING));
        northNorth.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        northCenter.setLayout(new GridLayout(5, 2, 5, 5));

        south.setLayout(new BorderLayout(5, 5));
        southNorth.setLayout(new FlowLayout(FlowLayout.LEADING));
        southCenter.setLayout(new GridLayout(1, 1));
        southSouth.setLayout(new BorderLayout(5, 5));
        southSouthEast.setLayout(new FlowLayout(FlowLayout.TRAILING));
        southSouthWest.setLayout(new FlowLayout(FlowLayout.LEADING));

        southNorth.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        panelTitle = new JLabel("Gerecht toevoegen");
        panelTitle.setFont(font1);

        ingredientTitle = new JLabel("Kies ingrediënten");
        ingredientTitle.setFont(font1);

        name = new JLabel("Gerechtnaam:");
        description = new JLabel("Beschrijving:");
        time = new JLabel("Bereidingstijd:");
        price = new JLabel("Prijs in centen:");
        category = new JLabel("Catgeorie:");

        nameRight = new JTextField();
        descriptionRight = new JTextField();
        timeRight = new JTextField();
        priceRight = new JTextField();

        backButton = new JButton("Terug");
        backButton.addActionListener(buttonActionListener);

        updateButton = new JButton("Verder");
        updateButton.addActionListener(updateActionListener);

        ArrayList<Ingredient> ingredients = manager.getIngredients();

        for(Ingredient ingredient: ingredients) {
        ingredientListModel.addElement(String.format("%02d", ingredient.getIngredientNr())+" - "+ingredient.getName());
        }

        northNorth.add(panelTitle);
        northCenter.add(name);
        northCenter.add(nameRight);
        northCenter.add(description);
        northCenter.add(descriptionRight);
        northCenter.add(time);
        northCenter.add(timeRight);
        northCenter.add(price);
        northCenter.add(priceRight);
        northCenter.add(category);
        northCenter.add(menuList);

        southNorth.add(ingredientTitle);

        southCenter.add(scrollPane);

        southSouthEast.add(updateButton);
        southSouthWest.add(backButton);

        southSouth.add(southSouthEast, BorderLayout.EAST);
        southSouth.add(southSouthWest, BorderLayout.WEST);

        south.add(southNorth, BorderLayout.NORTH);
        south.add(southCenter, BorderLayout.CENTER);
        south.add(southSouth, BorderLayout.SOUTH);

        north.add(northNorth, BorderLayout.NORTH);
        north.add(northCenter, BorderLayout.CENTER);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
    }

    public JPanel generateIngredientPanel(int newProductNr, List selectedIngredients) {
        JPanel panel = new JPanel();

        String[] units = { "cl", "gram", "kg", "liter", "ml", "pak", "plak", "stuk" };

        int size = selectedIngredients.size();

        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel north = new JPanel();
        JPanel center = new JPanel();
        JPanel centerInner = new JPanel();
        JPanel south = new JPanel();

        north.setLayout(new FlowLayout(FlowLayout.LEADING));
        center.setLayout(new FlowLayout(FlowLayout.LEADING));

        int width = panel.getWidth();

        center.setSize(width, 100);
        centerInner.setLayout(new GridLayout(size+1, 3, 5, 5));
        centerInner.setSize(width, 100);
        south.setLayout(new FlowLayout(FlowLayout.TRAILING));

        panelTitle = new JLabel("Ingrediënten specificeren");
        panelTitle.setFont(font1);

        centerInner.add(new JLabel("Naam"));
        centerInner.add(new JLabel("Hoeveelheid"));
        centerInner.add(new JLabel("Eenheid"));

        for(Object object: selectedIngredients) {
        String[] ingredientName = object.toString().split(" - ");
        int ingredientNr = Integer.parseInt(ingredientName[0]);

        JLabel label = new JLabel(ingredientName[1]);
        label.setFont(new Font("Arial", Font.PLAIN, 12));

        centerInner.add(label);
        JTextField textField = new JTextField(10);
        JComboBox comboBox = new JComboBox(units);

        centerInner.add(textField);
        centerInner.add(comboBox);

        ArrayList<Object> ingredient = new ArrayList<Object>();

        ingredient.add(ingredientNr);
        ingredient.add(newProductNr);
        ingredient.add(textField);
        ingredient.add(comboBox);

        allElements.add(ingredient);
        }

        JButton save = new JButton("Opslaan");
        save.addActionListener(saveIngredientSpecs);

        south.add(save);

        north.add(panelTitle);

        center.add(centerInner);

        panel.add(north, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(south, BorderLayout.SOUTH);

        return panel;
    }

    ActionListener buttonActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            frame.getContentPane().removeAll();
            frame.setTitle("Keuken");
            JPanel paneel = new KitchenGUI(manager, frame);
            frame.setContentPane(paneel);
            frame.validate();
            frame.repaint();
        }
    };

    ActionListener saveIngredientSpecs = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ArrayList<ArrayList> allValues = new ArrayList<ArrayList>();

            for(ArrayList AL: allElements) {
            ArrayList<String> value = new ArrayList<String>();

            String ingredientNr = AL.get(0).toString();
            String productNr = AL.get(1).toString();

            JTextField field = (JTextField) AL.get(2);
            JComboBox box = (JComboBox) AL.get(3);

            String quantity = field.getText();
            String unit = box.getSelectedItem().toString();

            value.add(ingredientNr);
            value.add(productNr);
            value.add(quantity);
            value.add(unit);

            allValues.add(value);
            }

            manager.insertProductIngredients(allValues);

            JOptionPane.showMessageDialog(null, "Het product is toegevoegd aan het assortiment.");

            frame.getContentPane().removeAll();
            frame.setTitle("Keuken");
            JPanel paneel = new KitchenGUI(manager, frame);
            frame.setContentPane(paneel);
            frame.validate();
            frame.repaint();
        }
    };

    ActionListener updateActionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String selectedMenu = menuList.getSelectedItem().toString();
            String nr = selectedMenu.substring(0, selectedMenu.indexOf(" "));
            int menuID = Integer.parseInt(nr); 

            String nameValue = nameRight.getText();
            String descriptionValue = descriptionRight.getText();
            long priceValue = 0;
            long prepTimeValue = 0;

            if(!"".equals(timeRight.getText())) {
            prepTimeValue = Long.parseLong(timeRight.getText());
            }

            if(!"".equals(priceRight.getText())) {
            priceValue = Long.parseLong(priceRight.getText());
            }

            if(!"".equals(nameValue) && !"".equals(descriptionValue) && !"".equals(timeRight) && !"".equals(priceRight)) {
            int newProductNr = manager.createNewProduct(priceValue, menuID, nameValue, descriptionValue, prepTimeValue);

            List objs = ingredientList.getSelectedValuesList();

            frame.getContentPane().removeAll();

            JPanel panel = generateIngredientPanel(newProductNr, objs);

            frame.setContentPane(panel);
            frame.validate();
            frame.repaint();
            }
        }
    };
}
