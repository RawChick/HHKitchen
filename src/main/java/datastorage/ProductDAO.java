package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import domain.Order;
import domain.Product;

public class ProductDAO {
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

	public boolean removeProduct(int productNr) {
		boolean result = false;

		//if (productToBeRemoved != null) {
			// First open the database connection.
			DatabaseConnection connection = new DatabaseConnection();
			if (connection.openConnection()) {
				// Execute the delete statement using the membership number to
				// identify the member row.
				result = connection.executeSQLUpdateStatement("DELETE FROM dish_menu_item WHERE ID = "
								+ productNr + ";");

				// Finished with the connection, so close it.
				connection.closeConnection();
			}
			// else an error occurred leave 'member' to null.
	

		return result;
	}

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
	
	public boolean newProduct(String name, long price, int menuID, long prepTime) {
		boolean result = false;
		
		DatabaseConnection connection = new DatabaseConnection();
		if(connection.openConnection()) {
		
			
		result = connection.executeSQLUpdateStatement("INSERT INTO `dish_menu_item`(`price`, `menu_ID`, `name`, `cooking_minutes`) VALUES ("+price+", "+menuID+", '"+name+"', "+prepTime+");");			
			connection.closeConnection();
		}		
		
		return result;
	}
	
}
