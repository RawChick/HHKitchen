package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Product;


/**
 * 
 * This class contains the methods to write and retrieve data to and from a database that have to do with the products.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class ProductDAO {
	
	/**
	 * 
	 * @return This method retrieves all products from the database.
	 */
	public ArrayList<Product> retrieveProducts() {
		ArrayList<Product> products;
		products = new ArrayList<Product>();

		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT * FROM dish_menu_item");

			if (resultset != null) {
				try {
					while (resultset.next()) {
						int productNrFromDb = resultset.getInt("ID");
						String productNameFromDb = resultset.getString("name");
						long priceFromDb = resultset.getLong("price");
						long timeFromDb = resultset.getLong("cooking_minutes");
						
						products.add(new Product(productNrFromDb, productNameFromDb, priceFromDb, timeFromDb));
					}
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			// else an error occurred leave 'member' to null.

			// We had a database connection opened. Since we're finished,
			// we need to close it.
			connection.closeConnection();
		}

		return products;
	}

	/**
	 * This method removes a product from the database.
	 * @param productNr The number of a product.
	 * @return True or false depending on the success of the method.
	 */
	public boolean removeProduct(int productNr) {
		boolean result = false;

		//if (productToBeRemoved != null) {
			// First open the database connection.
			DatabaseConnection connection = new DatabaseConnection();
			if (connection.openConnection()) {
				// Execute the delete statement using the membership number to
				// identify the member row.
				result = connection.executeSQLDeleteStatement("DELETE FROM dish_menu_item WHERE ID = "
								+ productNr + ";");

				// Finished with the connection, so close it.
				connection.closeConnection();
			}
			// else an error occurred leave 'member' to null.
	

		return result;
	}

	/**
	 * This method updates a product in the database.
	 * @param productNr The number of a product.
	 * @param name The name of a product.
	 * @param price The price of a product.
	 * @param prepTime The preparation time of a product.
	 * @return True or false depending on the success of the method.
	 */
	public boolean updateProduct(int productNr, String name, long price, long prepTime) {
		boolean result = false;
		
		DatabaseConnection connection = new DatabaseConnection();
		if(connection.openConnection()) {
			result = connection.executeSQLUpdateStatement("UPDATE dish_menu_item SET "
					+ "price = "+price+", "
					+ "name = '"+name+"', "
					+ "cooking_minutes = "+prepTime+" "
					+ "WHERE ID = "+productNr+";");
			
			connection.closeConnection();
		}		
		
		return result;
	}
	
	/**
	 * This method adds a new product to the database.
	 * @param price The price of a product.
	 * @param menuID The "category" of a product.
	 * @param name The name of a product.
	 * @param description The description of a product.
	 * @param prepTime The preparation time of a product.
	 * @return True or false depending on the success of the method.
	 */
	public int createNewProduct(long price, int menuID, String name, String description, long prepTime) {
		int productNr = 0;
		
		DatabaseConnection connection = new DatabaseConnection();
		
		if(connection.openConnection()) {			
			productNr = connection.executeSQLInsertStatement("INSERT INTO `dish_menu_item`(`price`, `menu_ID`, `name`, `description`, `cooking_minutes`) VALUES ("+price+", "+menuID+", '"+name+"', '"+description+"', "+prepTime+");", 1);			
		}
		
		connection.closeConnection();
		
		return productNr;
	}
	
}
