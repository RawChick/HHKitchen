package datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Order;

/**
 * 
 * This class contains the methods to write and retrieve data to and from a
 * database that have to do with the orders.
 * 
 * @author Wesley Heesters
 * @author Renée Vroedsteijn
 * @author Thomas Roovers
 * @see businesslogic.OrderManager
 * @version 1.0
 * 
 */

public class OrderDAO {

    /**
     * 
     * @return This method retrieves the orders from the database.
     */
    public List<Order> retrieveOrders() {
        List<Order> orders = new ArrayList<Order>();

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

                        Order order = new Order(tableIDFromDb, orderIDFromDb, statusFromDb);

                        orders.add(order);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    orders = null;
                }
            }
            // else an error occurred leave 'member' to null.

            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection();
        }

        return orders;
    }

    /**
     * 
     * @param orders
     *            An arraylist with orders.
     * @return All new orders from the database.
     */
    public List<Order> retrieveNewOrders(List<Order> orders) {
        List<Order> newOrders = new ArrayList<Order>();

        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {
            // If a connection was successfully setup, execute the SELECT
            // statement.
            ResultSet resultset = connection.executeSQLSelectStatement("SELECT * FROM dish_order WHERE status = 1");

            if (resultset != null) {
                try {
                    while (resultset.next()) {
                        boolean sameOrder = false;

                        int orderIDFromDb = resultset.getInt("ID");
                        int tableIDFromDb = resultset.getInt("table_ID");
                        int statusFromDb = resultset.getInt("status");

                        // Maak tijdelijke nieuw order-object aan
                        Order newOrder = new Order(tableIDFromDb,
                                orderIDFromDb, statusFromDb);

                        // Loop door bestaande orders, om te kijken of de order
                        // uit de database al bekend is in de keuken, zo niet,
                        // dan sameOrder false houden.
                        for (Order order : orders) {
                            if (sameOrder == false) {
                                if (order.getOrderNr() == orderIDFromDb
                                        && order.getStatus() == 1) {
                                    sameOrder = true;
                                } else {
                                    sameOrder = false;
                                }
                            }
                        }

                        // Als nieuwe order nog niet in de order-ArrayList zit,
                        // dan toevoegen
                        if (sameOrder == false) {
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

    /**
     * 
     * @param orders
     *            An arraylist with orders.
     * @return All cancelled orders from the database.
     */
    public List<Order> retrieveCancelledOrders(List<Order> orders) {
        List<Order> cancelledOrders = new ArrayList<Order>();

        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {
            // If a connection was successfully setup, execute the SELECT
            // statement.
            ResultSet resultset = connection
                    .executeSQLSelectStatement("SELECT * FROM dish_order WHERE status = 5");

            if (resultset != null) {
                try {
                    while (resultset.next()) {
                        boolean sameOrder = false;

                        int orderIDFromDb = resultset.getInt("ID");
                        int tableIDFromDb = resultset.getInt("table_ID");
                        int statusFromDb = resultset.getInt("status");

                        // Maak tijdelijke nieuw order-object aan
                        Order cancelledOrder = new Order(tableIDFromDb,
                                orderIDFromDb, statusFromDb);

                        // Loop door bestaande orders, om te kijken of de order
                        // uit de database al bekend is in de keuken, zo niet,
                        // dan sameOrder false houden.
                        for (Order order : orders) {
                            if (sameOrder == false) {
                                if (order.getOrderNr() == orderIDFromDb) {
                                    sameOrder = true;
                                } else {
                                    sameOrder = false;
                                }
                            }
                        }

                        // Als nieuwe order nog niet in de order-ArrayList zit,
                        // dan toevoegen
                        if (sameOrder == true) {
                            cancelledOrders.add(cancelledOrder);
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

        return cancelledOrders;
    }

    /**
     * 
     * @param status
     *            The status of an order.
     * @param orderNr
     *            The number of an order.
     * @return True or false depending on the success of the method.
     */
    public boolean updateStatus(int status, int orderNr) {
        boolean result = false;

        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if (connection.openConnection()) {
            // If a connection was successfully setup, execute the SELECT
            // statement.
            result = connection
                    .executeSQLUpdateStatement("UPDATE dish_order SET status = "
                            + status + " WHERE ID = " + orderNr + ";");

            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection();
        }

        return result;
    }
}
