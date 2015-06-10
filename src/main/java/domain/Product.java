package domain;

public class Product {
	private int productNr;
	private String name;
	private long price;
	private long preparationTime;
	
	public Product(int productNr, String name, long price, long preparationTime) {
		this.productNr = productNr;
		this.name = name;
		this.price = price;
		this.preparationTime = preparationTime;
	}
	
	
	public long getPreparationTime(){
		return preparationTime;
	}
	
	public int getProductNr() {
		return productNr;
	}
	
	public String getName() {
		return name;
	}
	
	public long getPrice() {
		return price;
	}
}
