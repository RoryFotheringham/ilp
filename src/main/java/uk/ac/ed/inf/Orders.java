package uk.ac.ed.inf;

import com.what3words.javawrapper.What3WordsV3;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Orders Class - reads order data for a given date from the database into an appropriate class structure.
 */
public class Orders {
    private static final String W3W_API_KEY = "NDH4FINL";
    ArrayList<OrderDetails> ordersList;
    String portDB;
    String portWeb;
    String machineName;
    java.sql.Date date;
    Connection conn;

    /**
     * The only constructor for Orders class
     *
     * @param machineName Name of database server
     * @param portDB Port of Database Server
     * @param portWeb Port of Web Server
     * @param date The date that the orders are to be gathered from
     * @param itemMap Passed from Menus, maps the order number to item object
     * @throws SQLException ...
     */
    public Orders(String machineName, String portWeb, String portDB, java.sql.Date date, HashMap<String, Item> itemMap) throws SQLException {
      // this.ordersList = readOrders();
       this.machineName = machineName;
       this.portDB = portDB;
       this.portWeb = portWeb;
       this.date = date;
       this.conn = getConnection();
       ResultSet rsOrders = readOrders();
       this.ordersList = createOrdersList(rsOrders, itemMap);
       closeConnection(this.conn);
    }

    /**
     * Closes the database connection, catches a sql exeption
     * @param conn connection
     */
    private void closeConnection(Connection conn) {
        try {
            conn.close();
        }catch (SQLException e){
            System.exit(1);
        }
    }

    /**
     * Gets Connection to the derbyDB database, catches a sql exception
     * @return returns the connection
     */
    private Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:derby://" + machineName + ":" + portDB + "/derbyDB");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Database not found at port: " + portDB);
            System.exit(1);
        }
        return conn;
    }

    /**
     * Reads the orders from the database with respect to the delivery date
     * @return returns a resultSet containing order information
     * @throws SQLException
     */
    private ResultSet readOrders() throws SQLException {
        final String ordersQuery = "select * from orders where deliveryDate = ?";

        PreparedStatement psOrdersQuery = conn.prepareStatement(ordersQuery);
        psOrdersQuery.setString(1, String.valueOf(date));
        ResultSet rsOrders = psOrdersQuery.executeQuery();
        return rsOrders;
    }

    /**
     * Reads the orderDetails from the database with respect to a given order number
     * @param orderNo order number
     * @return returns a result set containing order detail information
     * @throws SQLException
     */
    private ResultSet readDetails(String orderNo) throws SQLException {
        final String ordersQuery = "select * from orderDetails where orderNo = ?";

        PreparedStatement psOrdersQuery = conn.prepareStatement(ordersQuery);
        psOrdersQuery.setString(1, String.valueOf(orderNo));
        ResultSet rsDetails = psOrdersQuery.executeQuery();
        return rsDetails;
    }

    /**
     * Combines the results from Order and Order Details queries into an appropriate class structure
     * @param rsOrders Result set containing orders from the given date
     * @param itemMap HashMap mapping order number to item object
     * @return returns a list of OrderDetails each of which contain the data from Order and a list of Item objects
     * @throws SQLException
     */
    private ArrayList<OrderDetails> createOrdersList(ResultSet rsOrders, HashMap<String, Item> itemMap) throws SQLException {
        ArrayList<OrderDetails> ordersList = new ArrayList<OrderDetails>();

        while (rsOrders.next()) {
            String orderNo = rsOrders.getString("orderNo");
            String deliverTo = rsOrders.getString("deliverTo");
            String customer = rsOrders.getString("customer");
            String deliveryDate = rsOrders.getString("deliveryDate");

            LongLat deliverToCoords = new LongLat(deliverTo, this.machineName, this.portWeb); //instantiates what.three.words string to a LongLat object
            OrderDetails order = new OrderDetails(orderNo, deliveryDate, customer, deliverToCoords); //creates order object
            ResultSet rsDetails = readDetails(orderNo); //get the items in a given order from database

            /*
            iterates through each item string in the order and adds its corresponding
            Item object to the item field in the order object
             */
            while (rsDetails.next()){
                String itemName = rsDetails.getString("item");
                order.items.add(itemMap.get(itemName)); //add items into the order object
            }
            ordersList.add(order);
        }
        return ordersList;
    }
}
