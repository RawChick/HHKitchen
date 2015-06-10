package presentation;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import businesslogic.OrderManager;

public class LoginGUI extends JPanel {
	private OrderManager manager;
	private JFrame frame;
	
	// Attributen voor het Loginscreen
	private JTextField employeeCode;
	private JButton login;
	private JButton[] numbers = new JButton[10];
	private int i;
	
	public LoginGUI(OrderManager manager, JFrame frame) {
		this.manager = manager;
		this.frame = frame;
		
		createLoginGUI();
	}
	
	public void createLoginGUI() {
		setLayout(new BorderLayout(10, 10));
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel north = new JPanel();
		JPanel north_inner = new JPanel();
		JPanel south = new JPanel();
		
		north.setLayout(new BorderLayout(10, 10));
		north.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		north_inner.setLayout(new GridLayout(3, 3));
		
		south.setLayout(new FlowLayout(FlowLayout.TRAILING));
		
		employeeCode = new JTextField(10);
		login = new JButton("Inloggen");
		login.addActionListener(loginActionEvent);
		
		north.add(employeeCode, BorderLayout.NORTH);
		
		for(i = 0; i < 9; i++) {
			numbers[i] = new JButton(("789456123").substring(i, i+1));
			
			north_inner.add(numbers[i]);
			
			numbers[i].addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
					employeeCode.setText(employeeCode.getText() + e.getActionCommand());
	            }
	        });
		}
		
		numbers[9] = new JButton("0");
		numbers[9].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				employeeCode.setText(employeeCode.getText() + e.getActionCommand());
            }
        });
		
		north.add(north_inner, BorderLayout.CENTER);
		north.add(numbers[9], BorderLayout.SOUTH);
		
		south.add(login);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
	}
	
	ActionListener loginActionEvent = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			/*if(employeeCode.getText().equals("1234")) {
				alterInterface();
			}*/
			
			String employeeNumber = employeeCode.getText();
			
			boolean login = manager.findEmployee(employeeNumber);
			
			if(login == true) {
				alterInterface();
			} else {
				JOptionPane.showMessageDialog(null, "U bent niet bevoegd om in te loggen, probeer het alstublieft opnieuw.", "Onjuiste bevoegdheden", JOptionPane.ERROR_MESSAGE);
					employeeCode.setText("");
			}
		}
	};
	
	public void alterInterface() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.getContentPane().removeAll();
		frame.setTitle("Keuken");
		frame.setSize(500, 500);
		
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		JPanel paneel = new KitchenGUI(manager, frame);
		
		frame.setContentPane(paneel);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
	}
}
