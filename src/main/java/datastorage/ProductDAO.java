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

	/**
	 * Removes the given member from the database.
	 * 
	 * @param memberToBeRemoved
	 *            an object of the Member class representing the member to be
	 *            removed.
	 * 
	 * @return true if execution of the SQL-statement was successful, false
	 *         otherwise.
	 */
	public boolean removeProduct(Product productToBeRemoved) {
		boolean result = false;

		if (productToBeRemoved != null) {
			// First open the database connection.
			DatabaseConnection connection = new DatabaseConnection();
			if (connection.openConnection()) {
				// Execute the delete statement using the membership number to
				// identify the member row.
				result = connection.executeSQLUpdateStatement("DELETE FROM Product WHERE ProductNumber = "
								+ productToBeRemoved.getProductNr() + ";");

				// Finished with the connection, so close it.
				connection.closeConnection();
			}
			// else an error occurred leave 'member' to null.
		}

		return result;
	}

}
