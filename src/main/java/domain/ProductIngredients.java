package domain;

/**
 * 
 * This class contains the attributes for the ingredients of a product and their respective getters and setters.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class ProductIngredients {

    private int ingredientNr;
    private String ingredientName;
    private int productNr;
    private int quantity;
    private String unit;

    /**
     * 
     * @param ingredientNr Number of an ingredient.
     * @param ingredientName Name of an ingredient.
     * @param productNr Number of a product.
     * @param quantity Quantity of an ingredient.
     * @param unit Unit of an ingredient.
     */
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
