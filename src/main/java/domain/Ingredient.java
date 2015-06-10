package domain;

public class Ingredient {
	private int ingredientNr;
	private String name;

	public Ingredient(int ingredientNr, String name){
		this.name = name;
		this.ingredientNr = ingredientNr;

	}
	
	public String getName(){
		return name;
	}
		
	public int getIngredientNr(){
		return ingredientNr;
	}
	
}
