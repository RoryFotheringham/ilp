package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

//This is going to be where we read from and store all of the orders from the database

public class Orders {
    ArrayList<OrderDetails> ordersList;
    String port;
    String machineName;
    java.sql.Date date;

    //HashMap<Date, OrderOfDay> ordersODHashMap;

    public Orders(String machineName, String port, java.sql.Date date) throws SQLException {
      // this.ordersList = readOrders();
       this.machineName = machineName;
       this.port = port;
       this.date = date;
       this.ordersList = readOrders();
    }

    public ArrayList<OrderDetails> readOrders() throws SQLException { //todo maybe i could make this method throw a sql exception
                                                // maybe then i could just try catch the whole method in one block
        ArrayList<OrderDetails> ordersList = new ArrayList<OrderDetails>();

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:derby://" + machineName + ":" + port + "/derbyDB");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Database not found at port: " + port);
            System.out.println("jdbc:derby://" + machineName + ":" + port + "/derbyDB");
            System.exit(1);
        }

        final String ordersQuery = "select * from orders where deliveryDate = ?";
       // PreparedStatement ps = conn.prepareStatement(ordersQuery);


        PreparedStatement psOrdersQuery = null;
        try {
            psOrdersQuery = conn.prepareStatement(ordersQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected database error");
            System.exit(-1);
        }

        try {
            psOrdersQuery.setString(1, String.valueOf(date));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected error with query string");
            System.exit(-1);
        }

        ResultSet rs = null;
        try {
            rs = psOrdersQuery.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected error with query");
            System.exit(-1);
        }

        try {
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected database error");

        }

        while (rs.next()) {
            String orderNo = rs.getString("orderNo");
            String deliverTo = rs.getString("deliverTo");
            String customer = rs.getString("customer");
            String deliveryDate = rs.getString("deliveryDate");
            //todo use this data to instantiate an orders class which is then added to a list ordersList<Order>
            //todo then iterate through each item in that list and map the orderNo to item object in a HashMap.
            OrderDetails order = new OrderDetails(orderNo, deliveryDate, customer, deliverTo);
            ordersList.add(order);
        }

        return ordersList;
    }


}
