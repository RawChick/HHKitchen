package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;

import domain.ProductIngredients;

public class ProductIngredientsDAO {
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
						boolean sameIngredient = false;
						
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
}
