package domain;

/**
 * 
 * This class contains the attributes for the order rows and their respective getters and setters.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class OrderRow {
	private int orderNr;
	private int productNr;
	private int amount;
	
	/**
	 * 
	 * @param orderNr Number of an order.
	 * @param productNr Number of an product.
	 * @param amount Amount of products in an order.
	 */
	public OrderRow(int orderNr, int productNr, int amount) {
		this.orderNr = orderNr;
		this.productNr = productNr;
		this.amount = amount;
	}
	
	public int getOrderNr() {
		return orderNr;
	}
	
	public int getProductNr() {
		return productNr;
	}
	
	public int getAmount() {
		return amount;
	}
}
