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

/** 
 * This class delegates persistence to the datastorage layer and receives entity's from the datastorage layer.
 * 
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @version 1.0
 */

public class OrderManager {
	private ArrayList<Product> products;
	private ArrayList<Order> orders, newOrders, cancelledOrders;
	private ArrayList<OrderRow> orderRows;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<ProductIngredients> productIngredients;
	private OrderDAO orderDAO;
	private ProductDAO productDAO;
	private EmployeeDAO employeeDAO;
	private OrderRowDAO orderRowDAO;
	private ProductIngredientsDAO productIngredientsDAO;
	private IngredientDAO ingredientDAO;
	
	
	public OrderManager() {
		products = new ArrayList<Product>();
		orders = new ArrayList<Order>();
		newOrders = new ArrayList<Order>();
		cancelledOrders = new ArrayList<Order>();
		orderRows = new ArrayList<OrderRow>();
		ingredients = new ArrayList<Ingredient>();
		productIngredients = new ArrayList<ProductIngredients>();
		
		employeeDAO = new EmployeeDAO();
		orderDAO = new OrderDAO();
		productDAO = new ProductDAO();
		orderRowDAO = new OrderRowDAO();
		productIngredientsDAO = new ProductIngredientsDAO();
		ingredientDAO = new IngredientDAO();
	}
	
	/**
	 * This method calls a method from the OrderDAO to retrieve all orders from the database.
	 */
	public void retrieveOrders() {
		orders = orderDAO.retrieveOrders();
	}
	
	/**
	 * This method calls a method from the OrderDAO to retrieve all new orders from the database.
	 */
	public void retrieveNewOrders() {
		newOrders = orderDAO.retrieveNewOrders(orders);
		
		for(Order order: newOrders) {
			orders.add(order);
		}
	}
	
	/**
	 * This method calls a method from the OrderDAO to retrieve all cancelled orders from the database.
	 */
	public void retrieveCancelledOrders() {
		cancelledOrders = orderDAO.retrieveCancelledOrders(orders);
		
		for(Order order: cancelledOrders) {
			orders.remove(order);
		}
	}
	
	/**
	 * This method calls a method from the ProductIngredientsDAO to retrieve all ingredients from a specific product from the database.
	 * @param productNr The number of a product.
	 * @return The ingredients of a product.
	 */
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
	
	/**
	 * This method calls a method from the ProductDAO to retrieve all products from the database.
	 */
	public void retrieveProducts() {
		products = productDAO.retrieveProducts();		
	}
	
	/**
	 * This method calls a method from the IngredientsDAO to retrieve all ingredients from the database.
	 */
	public void retrieveIngredients() {
		ingredients = ingredientDAO.retrieveIngredients();
	}

	/**
	 * This method changes the status of an order to "accepted".
	 * @param orderNr The number of an order.
	 * @return The status of the order.
	 */
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
		
		for(OrderRow orderRow: orderRows) {
			if(orderRow.getOrderNr() == orderNr) {
				int productID = orderRow.getProductNr();
				int amount = orderRow.getAmount();
				int ingredientID, quantity;
				
				for(ProductIngredients pI: productIngredients) {
					if(pI.getProductNr() == productID) {
						ingredientID = pI.getIngredientNr();
						quantity = amount*pI.getQuantity();
					
						ingredientDAO.updateInventory(ingredientID, quantity);
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * This method changes the status of an order to "ready".
	 * @param orderNr The number of an order.
	 * @return The status of the order.
	 */
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
	
	/**
	 * @return This method gets a list of all the loaded orders.
	 */
	public ArrayList<Order> getOrders() {		
		return orders;
	}
	
	/**
	 * @return This method gets a list of all the newly loaded orders.
	 */
	public ArrayList<Order> getNewOrders() {
		return newOrders;
	}
	
	/**
	 * @return This method gets a list of all the cancelled loaded orders.
	 */
	public ArrayList<Order> getCancelledOrders() {
		return cancelledOrders;
	}
	
	/**
	 * 
	 * @return This method gets a list of all the loaded products.
	 */
	public ArrayList<Product> getProducts() {
		return products;
	}
	
	/**
	 * 
	 * @return This method gets a list of all the loaded ingredients.
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
	
	/**
	 * This method adds a product to the list of products.
	 * @param product Contains all attributes of a product.
	 */
	public void addProduct(Product product) {
		products.add(product);
	}
	
	
	/**
	 * This method gets a list of all the loaded products within an order row.
	 * @param orderNr The number of an order.
	 * @return All products from an order row.
	 */
	public ArrayList<OrderRow> getProducts(int orderNr) {
		ArrayList<OrderRow> orderRowsProducts = new ArrayList<OrderRow>();
		
		boolean result = false;
		
		for(OrderRow orderRow: orderRows) {
			if(orderRow.getOrderNr() == orderNr) {
				orderRowsProducts.add(orderRow);
				
				result = true;
			}
		}
		
		if(result == false) {
			ArrayList<OrderRow> retrievedOrderRows = orderRowDAO.retrieveOrderRows(orderNr);			
			
			for(OrderRow orderRow: retrievedOrderRows) {
				if(orderRow.getOrderNr() == orderNr) {
					orderRowsProducts.add(orderRow);
					
					orderRows.add(orderRow);
				}
			}
		}
		
		return orderRowsProducts;
	}
	
	/**
	 * 
	 * @param productNr
	 * @return
	 */
	public Product searchProduct(int productNr) {
		Product product = null;
		
		for(Product p: products) {
			if(p.getProductNr() == productNr) {
				product = p;
			}
		}
		
		return product;
	}
	
	/**
	 * 
	 * @param orderNr
	 * @return
	 */
	public Order searchOrder(int orderNr) {
		Order order = null;
		
		for(Order o: orders) {
			if(o.getOrderNr() == orderNr) {
				order = o;
			}
		}
		
		return order;
	}
	
	/**
	 * This method calls a method from the EmployeeDAO to find an entered employeeNumber in the database.
	 * @param employeeNumber The number of an employee.
	 * @return True or false depending on the success of the method.
	 */
	public boolean findEmployee(String employeeNumber) {
		boolean login = employeeDAO.findEmployee(employeeNumber);
		
		return login;
	}
	
	/**
	 * This method calls a method from the ProductDAO to update a product in the database.
	 * @param productNr The number of the product.
	 * @param name The name of the product.
	 * @param prepTime The preparation time of the product.
	 * @param price The price of the product.
	 * @return True or false depending on the success of the method.
	 */
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
	
	/**
	 * This method calls a method from the ProductDAO to create a new product in the database.
	 * @param priceValue The price of the product.
	 * @param menuID The "category" of the product.
	 * @param nameValue The name of the product.
	 * @param descriptionValue The description of the product.
	 * @param prepTimeValue The preperation time of the product.
	 * @return A new product number.
	 */
	public int createNewProduct(long priceValue, int menuID, String nameValue, String descriptionValue, long prepTimeValue) {
		int newProductNr = productDAO.createNewProduct(priceValue, menuID, nameValue, descriptionValue, prepTimeValue);
		
		products.add(new Product(newProductNr, nameValue, priceValue, prepTimeValue));
		
		return newProductNr;
	}
	
	
	/**
	 * This method calls a method from the ProductDAO to delete a product from the database.
	 * @param productNr A number to indicate which product is meant.
	 * @return True or false depending on the success of the method.
	 */
	public boolean deleteProduct(int productNr){
		boolean result = productDAO.removeProduct(productNr);
		
		if(result == true){
			System.out.println("Product verwijderd.");
		}
		return result;
	}
	
	/**
	 * This method updates the ingredients for a specific product.
	 * @param productNr A number to indicate which product is meant.
	 */
	public void updateIngredientSpecs(JTable table, int row, int productNr) {
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
	
	/**
	 * This method adds ingredients to a product.
	 * @param ingredient
	 */
	public void insertProductIngredients(ArrayList<ArrayList> ingredient) {
		int ingredientNr = 0;
		int productNr = 0;
		long quantity = 0;
		String unit = null;
		
		for(ArrayList AL: ingredient) {			
			ingredientNr = Integer.parseInt(AL.get(0).toString());
			productNr = Integer.parseInt(AL.get(1).toString());
			quantity = Long.parseLong(AL.get(2).toString());
			unit = AL.get(3).toString();
			
			productIngredientsDAO.insertProductIngredients(ingredientNr, productNr, quantity, unit);
		}
	}
}
