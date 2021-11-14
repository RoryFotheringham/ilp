package uk.ac.ed.inf;

import java.sql.Date;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException {
//todo handle the args to construct Menus and Orders Classes
        Date date = Date.valueOf("2023-03-31");
        Orders orders = new Orders("localhost", "1527", date);

        System.out.println(orders.ordersList.get(0).customer);
    }
}
