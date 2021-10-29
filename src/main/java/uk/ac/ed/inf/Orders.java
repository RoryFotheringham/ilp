package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

//This is going to be where we read from and store all of the orders from the database

public class Orders {
    ArrayList<OrderOfDay> ordersList;
    ArrayList<String> ordersListString;
    String port;
    String machineName;
    java.util.Date utilDate;


    //HashMap<Date, OrderOfDay> ordersODHashMap;

    public Orders(String machineName, String port, java.util.Date utilDate) throws SQLException {
      // this.ordersList = readOrders();
       this.machineName = machineName;
       this.port = port;
       this.utilDate = utilDate;

        this.ordersListString = readOrders();

    }

    public ArrayList<String> readOrders() throws SQLException { //todo maybe i could make this method throw a sql exception
                                                // maybe then i could just try catch the whole method in one block
        ArrayList<String> ordersListString = new ArrayList<String>();

        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:derby://" + machineName + ":" + port + "/derbyDB");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Database not connected");
            System.exit(1);
        }

        final String ordersQuery = "select * from orders";
        PreparedStatement ps = conn.prepareStatement(ordersQuery);

/*
        PreparedStatement psOrdersQuery = null;
        try {
            psOrdersQuery = conn.prepareStatement(ordersQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected database error");
            System.exit(-1);
        }

        //java.sql.Date sqlDate = new java.sql.Date(this.utilDate.getTime());

        try {
            psOrdersQuery.setString(1, String.valueOf(utilDate));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Fatal Error: Unexpected error with query string");
            System.exit(-1);
        }

 */

        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
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
            String orderNo = rs.getString("deliveryDate");
            ordersListString.add(orderNo);
        }

        return ordersListString;
    }
}
