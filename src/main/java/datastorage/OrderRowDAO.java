package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import domain.Product;
import domain.Order;
import domain.OrderRow;

public class OrderRowDAO {
	public ArrayList<OrderRow> retrieveOrderRows(int orderNr) {
		ArrayList<OrderRow> retrievedOrderRows = new ArrayList<OrderRow>();
		
		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT * FROM dish_order_item WHERE order_ID = "+orderNr);

			if (resultset != null) {
				try {
					while (resultset.next()) {
						int dishItemIDFromDb = resultset.getInt("dish_item_ID");						
						int amountFromDb = resultset.getInt("amount");
						
						retrievedOrderRows.add(new OrderRow(orderNr, dishItemIDFromDb, amountFromDb));
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

		return retrievedOrderRows;
	}
}
