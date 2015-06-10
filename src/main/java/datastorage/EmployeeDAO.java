package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import domain.Product;

public class EmployeeDAO {
	public boolean findEmployee(String employeeNumber) {
		boolean login = false;

		// First open a database connnection
		DatabaseConnection connection = new DatabaseConnection();
		if (connection.openConnection()) {
			// If a connection was successfully setup, execute the SELECT
			// statement.
			ResultSet resultset = connection
					.executeSQLSelectStatement("SELECT * FROM staff WHERE ID = "
							+ employeeNumber + " AND Group_ID = 2;");

			if (resultset != null) {
				try {
					if(resultset.next()) {
						System.out.println(resultset.getString("firstname"));
						
						login = true;
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

		return login;
	}
}
