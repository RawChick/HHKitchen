package businesslogic;

import java.util.ArrayList;
import javax.swing.JTable;

import datastorage.EmployeeDAO;
import datastorage.IngredientDAO;
import datastorage.OrderDAO;
import datastorage.OrderRowDAO;
import datastorage.ProductDAO;
import datastorage.ProductIngredientsDAO;
import domain.Ingredient;
import domain.Order;
import domain.OrderRow;
import domain.Product;
import domain.ProductIngredients;

public class OrderManager {
	private ArrayList<Product> products;
	private ArrayList<Order> orders, newOrders;
	private ArrayList<OrderRow> orderRows;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<ProductIngredients> productIngredients;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;
	private EmployeeDAO employeeDAO;
	private OrderRowDAO orderRowDAO;
	private ProductIngredientsDAO productIngredientsDAO;
	
	public OrderManager() {
		products = new ArrayList<Product>();
		orders = new ArrayList<Order>();
		newOrders = new ArrayList<Order>();
		orderRows = new ArrayList<OrderRow>();
		ingredients = new ArrayList<Ingredient>();
		productIngredients = new ArrayList<ProductIngredients>();
		
		employeeDAO = new EmployeeDAO();
		orderDAO = new OrderDAO();
		productDAO = new ProductDAO();
		orderRowDAO = new OrderRowDAO();
		productIngredientsDAO = new ProductIngredientsDAO();
	}
	
	public void retrieveOrders() {
		orders = orderDAO.retrieveOrders();
	}
	
	public void retrieveNewOrders() {
		newOrders = orderDAO.retrieveNewOrders(orders);
		
		for(Order order: newOrders) {
			orders.add(order);
		}
	}
	
	public ArrayList<ProductIngredients> retrieveIngredients(int productNr) {
		ArrayList<ProductIngredients> rowIngredients = new ArrayList<ProductIngredients>();
		boolean foundProductIngredients = false;
		
		for(ProductIngredients productIngredient: productIngredients) {
			if(productIngredient.getProductNr() == productNr) {
				rowIngredients.add(productIngredient);
				
				foundProductIngredients = true;
			}
		}
		
		if(foundProductIngredients == false) {
			rowIngredients = productIngredientsDAO.retrieveIngredients(productNr);
			
			for(ProductIngredients pI: rowIngredients) {
				productIngredients.add(pI);
			}
		}
		
		return rowIngredients;		
	}
	
	public void retrieveProducts() {
		products = productDAO.retrieveProducts();		
	}
	
	public boolean acceptOrder(int orderNr) {
		boolean result = false;
		
		for(Order order: orders) {
			if(order.getOrderNr() == orderNr) {
				result = orderDAO.updateStatus(2, orderNr);
				
				if(result == true) {
					order.setStatus(2);
				}
				
				System.out.println("Ordernr: "+order.getOrderNr()+", status: "+order.getStatus());
			}
		}
		
		return result;
	}
	
	public boolean readyOrder(int orderNr) {
		boolean result = false;
		for(Order order: orders) {
			if(order.getOrderNr() == orderNr) {
				result = orderDAO.updateStatus(4, orderNr);
				
				if(result == true){
					order.setStatus(4);
				}
				
				System.out.println("Ordernr: "+order.getOrderNr()+", status: "+order.getStatus());
			}
		}
		return result;
	}
	
	public ArrayList<Order> getOrders() {		
		return orders;
	}
	
	public ArrayList<Order> getNewOrders() {
		return newOrders;
	}
	

	
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	public void addProduct(Product product) {
		products.add(product);
	}
	
	public ArrayList<Product> getProducts(int orderNr) {
		ArrayList<Product> orderProducts = new ArrayList<Product>();
		
		boolean result = false;
		
		for(OrderRow orderRow: orderRows) {
			if(orderRow.getOrderNr() == orderNr) {
				Product product = searchProduct(orderRow.getProductNr());
				
				orderProducts.add(product);
				
				result = true;
			}
		}
		
		if(result == false) {
			ArrayList<OrderRow> retrievedOrderRows = orderRowDAO.retrieveOrderRows(orderNr);			
			
			for(OrderRow orderRow: retrievedOrderRows) {
				if(orderRow.getOrderNr() == orderNr) {
					Product product = searchProduct(orderRow.getProductNr());
					
					orderProducts.add(product);
					
					orderRows.add(orderRow);
				}
			}
		}
		
		return orderProducts;
	}
		
	public Product searchProduct(int productNr) {
		Product product = null;
		
		for(Product p: products) {
			if(p.getProductNr() == productNr) {
				product = p;
			}
		}
		
		return product;
	}
	
	public Order searchOrder(int orderNr) {
		Order order = null;
		
		for(Order o: orders) {
			if(o.getOrderNr() == orderNr) {
				order = o;
			}
		}
		
		return order;
	}
	
	public boolean findEmployee(String employeeNumber) {
		boolean login = employeeDAO.findEmployee(employeeNumber);
		
		return login;
	}
	
	public boolean updateProduct(int productNr, String name, long prepTime, long price) {
		boolean result = productDAO.updateProduct(productNr, name, price, prepTime);
		
		if(result == true) {
			for(Product p: products) {
				if(p.getProductNr() == productNr) {
					p.setName(name);
					p.setPrepTime(prepTime);
					p.setPrice(price);
				}
			}
			System.out.println("Product gewijzigd.");
		}
		
		return result;
	}
	
	public boolean newProduct(String name, long price, int menuID, long prepTime) {
		boolean result = productDAO.newProduct(name, price, menuID, prepTime);
		
		if(result == true) {
			for(Product p: products) {
				
					p.setName(name);
					p.setPrepTime(prepTime);
					p.setPrice(price);
					
			}
			System.out.println("Product aangemaakt.");
		}
		
		return result;
	}
	
	public boolean deleteProduct(int productNr){
		boolean result = productDAO.removeProduct(productNr);
		
		if(result == true){
			System.out.println("Product verwijderd.");
		}
		return result;
	}
	
	public void updateIngredientSpecs(JTable table, int col, int row, int productNr) {
		int ingredientNr = Integer.parseInt(table.getValueAt(row, 0).toString());
		String name = (String) table.getValueAt(row, 1);        
		int quantity = Integer.parseInt(table.getValueAt(row, 2).toString());
		
		productIngredientsDAO.updateIngredientSpecs(productNr, ingredientNr, name, quantity);
		
		for(ProductIngredients pI: productIngredients) {
			if(pI.getIngredientNr() == ingredientNr) {
				pI.setName(name);
				pI.setQuantity(quantity);
			}
		}
	}
}
