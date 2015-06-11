package businesslogic;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import datastorage.EmployeeDAO;
import datastorage.OrderDAO;
import datastorage.OrderRowDAO;
import datastorage.ProductDAO;
import domain.Ingredient;
import domain.Order;
import domain.OrderRow;
import domain.Product;
import domain.ProductIngredients;

public class OrderManager {
	private ArrayList<Product> products;
	private ArrayList<Order> orders;
	private ArrayList<OrderRow> orderRows;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<ProductIngredients> productingredients;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;
	private EmployeeDAO employeeDAO;
	private OrderRowDAO orderRowDAO;
	
	public OrderManager() {
		products = new ArrayList<Product>();
		orders = new ArrayList<Order>();
		orderRows = new ArrayList<OrderRow>();
		ingredients = new ArrayList<Ingredient>();
		productingredients = new ArrayList<ProductIngredients>();
		
		employeeDAO = new EmployeeDAO();
		orderDAO = new OrderDAO();
		productDAO = new ProductDAO();
		orderRowDAO = new OrderRowDAO();
	}
	
	public void retrieveOrders() {
		Timer timer = new Timer();
		
		orders = orderDAO.retrieveOrders();
		timer.scheduleAtFixedRate(new TimerTask() {
			  @Override
			  public void run() {
				  orders = orderDAO.retrieveOrders();		
			  }
			}, 10*1000, 10*1000);
		
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
				result = orderDAO.updateStatus(3, orderNr);
				
				if(result == true){
				order.setStatus(3);
				}
				
				System.out.println("Ordernr: "+order.getOrderNr()+", status: "+order.getStatus());
			}
		}
		return result;
	}
	
	public ArrayList<Order> getOrders() {		
		return orders;
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
}
