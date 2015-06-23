package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.ProductIngredients;


/**
 * 
 * This class contains the methods to write and retrieve data to and from a database that have to do with the ingredients of a product.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class ProductIngredientsDAO {
	/**
	 * This method retrieves the ingredients belonging to a specific product.
	 * @param productNr The number of a product.
	 * @return The ingredients that belong to a product.
	 */
	public ArrayList<ProductIngredients> retrieveIngredients(int productNr) {
		ArrayList<ProductIngredients> newIngredients = new ArrayList<ProductIngredients>();

		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT "
							+ "food.name, "
							+ "food.id, "
							+ "ingredient.food_ID, "
							+ "ingredient.quantity, "
							+ "ingredient.unit, "
							+ "ingredient.menu_item_ID "
							+ "FROM ingredient, food "
							+ "WHERE ingredient.menu_item_ID = "+productNr+" "
								+ "AND ingredient.food_ID = food.ID");

			if (resultset != null) {
				try {
					while (resultset.next()) {
					
						
						int ingredientNrFromDb = resultset.getInt("food_ID");
						String ingredientNameFromDb = resultset.getString("name");
						int quantityFromDb = resultset.getInt("quantity");
						String unitFromDb = resultset.getString("unit");
						
						ProductIngredients newIngredient = new ProductIngredients(ingredientNrFromDb, ingredientNameFromDb, productNr, quantityFromDb, unitFromDb);
						
						newIngredients.add(newIngredient);
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}

			// We had a database connection opened. Since we're finished,
			// we need to close it.
			connection.closeConnection();
		}

		return newIngredients;
	}
	
	/**
	 * This method updates the ingredients belonging to a specific product.
	 * @param productNr The number of a product.
	 * @param ingredientNr The number of an ingredient.
	 * @param name The name of an ingredient.
	 * @param quantity The quantity of an ingredient.
	 */
	public void updateIngredientSpecs(int productNr, int ingredientNr, String name, int quantity) {
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			try {
				connection.executeSQLUpdateStatement("UPDATE ingredient SET quantity = "+quantity+" WHERE food_ID = "+productNr);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
			
			try {            
	            connection.executeSQLUpdateStatement("UPDATE food SET name = '"+name+"' WHERE ID = "+ingredientNr);
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
			
			connection.closeConnection();
		}
	}
	
	/**
	 * This method adds ingredients to a specific product.
	 * @param ingredientNr The number of an ingredient.
	 * @param productNr The number of a product.
	 * @param quantity The quantity of an ingredient.
	 * @param unit The unit of an ingredient.
	 */
	public void insertProductIngredients(int ingredientNr, int productNr, long quantity, String unit) {
		DatabaseConnection connection = new DatabaseConnection();
		
		if(connection.openConnection()) {
			connection.executeSQLInsertStatement("INSERT INTO ingredient (`food_ID`, `menu_item_ID`, `quantity`, `unit`) VALUES ("+ingredientNr+", "+productNr+", "+quantity+", '"+unit+"')");
			
			connection.closeConnection();
		}
	}
}
