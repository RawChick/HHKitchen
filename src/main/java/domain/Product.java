package domain;

/**
 * 
 * This class contains the attributes for the products and their respective
 * getters and setters.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class Product {
    private int productNr;
    private String name;
    private long price;
    private long preparationTime;

    /**
     * 
     * @param productNr
     *            Number of a product.
     * @param name
     *            Name of a product.
     * @param price
     *            Price of a product.
     * @param preparationTime
     *            Preparation time of a product.
     */
    public Product(int productNr, String name, long price, long preparationTime) {
        this.productNr = productNr;
        this.name = name;
        this.price = price;
        this.preparationTime = preparationTime;
    }

    public long getPreparationTime() {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPrepTime(long prepTime) {
        this.preparationTime = prepTime;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
