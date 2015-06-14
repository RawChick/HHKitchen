package domain;

public class ProductIngredients {

	private int ingredientNr;
	private String ingredientName;
	private int productNr;
	private int quantity;
	private String unit;

	public ProductIngredients(int ingredientNr, String ingredientName, int productNr, int quantity, String unit){
		this.productNr = productNr;
		this.ingredientName = ingredientName;
		this.ingredientNr = ingredientNr;
		this.quantity = quantity;
		this.unit = unit;
	}
	
	public int getIngredientNr(){
		return ingredientNr;
	}
	
	public String getIngredientName() {
		return ingredientName;
	}
	
	public int getProductNr(){
		return productNr;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setName(String name) {
		this.ingredientName = name;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
