package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.OrderRow;


/**
 * 
 * This class contains the methods to write and retrieve data to and from a database that have to do with the products from an order.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class OrderRowDAO {

    /**
     * 
     * @param orderNr The number of an order.
     * @return The products from a specific order.
     */
    public ArrayList<OrderRow> retrieveOrderRows(int orderNr) {
        ArrayList<OrderRow> retrievedOrderRows = new ArrayList<OrderRow>();

        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {
        // If a connection was successfully setup, execute the SELECT
        // statement.
        ResultSet resultset = connection
                .executeSQLSelectStatement("SELECT dish_order_item.*, "
                        + "dish_menu_item.cooking_minutes, "
                        + "dish_menu_item.ID "
                        + "FROM dish_order_item, dish_menu_item "
                        + "WHERE dish_menu_item.ID = dish_order_item.dish_item_ID "
                        + "AND dish_order_item.order_ID = "+orderNr+" "
                        + "ORDER BY dish_menu_item.cooking_minutes DESC");

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
