package domain;

/**
 * 
 * This class contains the attributes for the ingredients and their respective getters and setters.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class Ingredient {
    private int ingredientNr;
    private String name;
    private String unit;

    /**
     * 
     * @param ingredientNr Number of the ingredient.
     * @param name Name of the ingredient.
     * @param unit Unit of the ingredient.
     */
    public Ingredient(int ingredientNr, String name, String unit){
        this.name = name;
        this.ingredientNr = ingredientNr;
        this.unit = unit;
    }

    public int getIngredientNr(){
        return ingredientNr;
    }

    public String getName(){
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
