package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

//This is going to be where we read from and store all of the orders from the database

public class Orders {
    ArrayList<OrderDetails> ordersList;
    String port;
    String machineName;
    java.sql.Date date;
    Connection conn;

    //HashMap<Date, OrderOfDay> ordersODHashMap;

    public Orders(String machineName, String port, java.sql.Date date, HashMap<String, Item> itemMap) throws SQLException {
      // this.ordersList = readOrders();
       this.machineName = machineName;
       this.port = port;
       this.date = date;
       this.conn = getConnection();
       ResultSet rsOrders = readOrders();
       this.ordersList = createOrdersList(rsOrders, itemMap);
    }

    private Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:derby://" + machineName + ":" + port + "/derbyDB");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Database not found at port: " + port);
            System.exit(1);
        }
        return conn;
    }

    private ResultSet readOrders() throws SQLException {
        final String ordersQuery = "select * from orders where deliveryDate = ?";

        PreparedStatement psOrdersQuery = conn.prepareStatement(ordersQuery);
        psOrdersQuery.setString(1, String.valueOf(date));
        ResultSet rsOrders = psOrdersQuery.executeQuery();
        return rsOrders;
    }

    private ResultSet readDetails(String orderNo) throws SQLException {
        final String ordersQuery = "select * from orderDetails where orderNo = ?";

        PreparedStatement psOrdersQuery = conn.prepareStatement(ordersQuery);
        psOrdersQuery.setString(1, String.valueOf(orderNo));
        ResultSet rsDetails = psOrdersQuery.executeQuery();
        return rsDetails;
    }

    private ArrayList<OrderDetails> createOrdersList(ResultSet rsOrders, HashMap<String, Item> itemMap) throws SQLException {
        ArrayList<OrderDetails> ordersList = new ArrayList<OrderDetails>();
        while (rsOrders.next()) {
            String orderNo = rsOrders.getString("orderNo");
            String deliverTo = rsOrders.getString("deliverTo");
            String customer = rsOrders.getString("customer");
            String deliveryDate = rsOrders.getString("deliveryDate");
            OrderDetails order = new OrderDetails(orderNo, deliveryDate, customer, deliverTo);

            ResultSet rsDetails = readDetails(orderNo); //get the items in a given order from database

            while (rsDetails.next()){
                String itemName = rsDetails.getString("item");
                order.items.add(itemMap.get(itemName)); //add items into the order object
            }
            ordersList.add(order);
        }
        return ordersList;
    }
}
