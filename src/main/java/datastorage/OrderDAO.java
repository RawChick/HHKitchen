package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import domain.Product;
import domain.Order;

public class OrderDAO {
	public ArrayList<Order> retrieveOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();

		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT * FROM dish_order WHERE status = 1 OR status = 2");

			if (resultset != null) {
				try {
					while (resultset.next()) {
						int orderIDFromDb = resultset.getInt("ID");
						int tableIDFromDb = resultset.getInt("table_ID");
						
						int statusFromDb = resultset.getInt("status");
						/*date dateFromDb = resultset.getDate("date_placed");*/

						Order order = new Order(tableIDFromDb, orderIDFromDb, statusFromDb);
						
						orders.add(order);
					}
				} catch (SQLException e) {
					System.out.println(e);
					Order order = null;
				}
			}
			// else an error occurred leave 'member' to null.

			// We had a database connection opened. Since we're finished,
			// we need to close it.
			connection.closeConnection();
		}

		return orders;
	}
	
	public ArrayList<Order> retrieveNewOrders(ArrayList<Order> orders) {
		ArrayList<Order> newOrders = new ArrayList<Order>();
		
		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT * FROM dish_order WHERE status = 1");

			if (resultset != null) {
				try {
					while (resultset.next()) {
						boolean sameOrder = false;
						
						int orderIDFromDb = resultset.getInt("ID");
						int tableIDFromDb = resultset.getInt("table_ID");
						
						int statusFromDb = resultset.getInt("status");
						/*date dateFromDb = resultset.getDate("date_placed");*/
						
						Order newOrder = new Order(tableIDFromDb, orderIDFromDb, statusFromDb);
						
						for(Order order: orders) {
							if(sameOrder == false) {
								if(order.getOrderNr() != orderIDFromDb && order.getStatus() != 1) {
									sameOrder = false;
								} else {
									sameOrder = true;
								}
							}
						}
						
						if(sameOrder == false) {
							newOrders.add(newOrder);
						}
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

		return newOrders;
	}
	
	public boolean updateStatus(int status, int orderNr) {
		boolean result = false;
		
		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			result = connection
					.executeSQLUpdateStatement("UPDATE dish_order SET status = "+status+" WHERE ID = "+orderNr+";");

			// We had a database connection opened. Since we're finished,
			// we need to close it.
			connection.closeConnection();
		}

		return result;
	}
}
