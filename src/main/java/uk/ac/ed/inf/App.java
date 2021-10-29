package uk.ac.ed.inf;

import java.sql.Date;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args ) throws SQLException {
//todo handle the args to construct Menus and Orders Classes
        Date date = new Date(2020,12, 23);
        Orders orders = new Orders("localhost", "1527", date);

        for (String orderNo : orders.ordersListString){
            System.out.println(orderNo);
        }
    }
}
