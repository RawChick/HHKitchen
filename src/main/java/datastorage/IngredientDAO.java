package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Ingredient;


/**
 * 
 * This class contains the methods to write and retrieve data to and from a database that have to do with the ingredients.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class IngredientDAO {
	
	/**
	 * This method updates the inventory of the kitchen.
	 * @param ingredientNr The number of an ingredient.
	 * @param quantity The quantity of an ingredient.
	 */
	public void updateInventory(int ingredientNr, int quantity) {
		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the UPDATE statement.
			
		connection.executeSQLUpdateStatement("UPDATE food SET stock = stock-"+quantity+" WHERE ID = "+ingredientNr);

			// We had a database connection opened. Since we're finished, we need to close it.
			connection.closeConnection();
		}
	}
	
	/**
	 * 
	 * @return This method retrieves the ingredients from the database.
	 */
	public ArrayList<Ingredient> retrieveIngredients() {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection.executeSQLSelectStatement("SELECT * FROM food ORDER BY name ASC");

			if (resultset != null) {
				try {
					while (resultset.next()) {
						int ingredientNrFromDb = resultset.getInt("ID");
						String ingredientNameFromDb = resultset.getString("name");
						String ingredientUnitFromDb = resultset.getString("unit");

						Ingredient ingredient = new Ingredient(ingredientNrFromDb, ingredientNameFromDb, ingredientUnitFromDb);

						ingredients.add(ingredient);
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			
			// We had a database connection opened. Since we're finished,
			// we need to close it.
			connection.closeConnection();
		}

		return ingredients;
	}
}
