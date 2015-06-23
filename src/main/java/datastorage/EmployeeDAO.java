package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * This class contains the methods to write and retrieve data to and from a
 * database that have to do with employee's.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class EmployeeDAO {

    /**
     * This method finds an employee with the function "chefkok".
     * 
     * @param employeeNumber
     *            The number of an employee.
     * @return The name of an employee and either an exception or access to the
     *         application.
     */
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

            try {
                if (resultset.next()) {
                    login = true;
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            // else an error occurred leave 'member' to null.

            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection();
        }

        return login;
    }
}
