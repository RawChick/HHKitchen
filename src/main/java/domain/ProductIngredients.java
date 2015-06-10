package domain;

public class ProductIngredients {

	private Product product;
	private Ingredient ingredient;
	private int amount;
	private String unit;

	public ProductIngredients(Product product, Ingredient ingredient, int amount, String unit){

		this.product = product;
		this.ingredient = ingredient;
		this.amount = amount;
		this.unit = unit;
	
	}
	
	public int getIngredientNr(){
		return ingredient.getIngredientNr();
	}
	
	
	public int getAmount(){
		return amount;
	}
	
	public String getUnit(){
		return unit;
	}
	

	
	
}
