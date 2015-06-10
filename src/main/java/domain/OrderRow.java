package domain;

public class OrderRow {
	private int orderNr;
	private int productNr;
	private int amount;
	
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
