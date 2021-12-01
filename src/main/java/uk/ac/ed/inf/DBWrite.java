package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

public class DBWrite {
    public DBWrite(FlightPath flightPath, Deliveries deliveries, String machineName, String portDB) {
        Connection conn = getConnection(machineName, portDB);
        writeFlightPath(conn, flightPath);
        writeDeliveries(conn, deliveries);
    }

    /**
     * Gets Connection to the derbyDB database, catches a sql exception
     * @return returns the connection
     */
    private Connection getConnection(String machineName, String portDB){
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
     * drops the table if it exists, otherwise does nothing
     * @param conn connection to the database
     * @param tableName the name of the table to drop if exists
     */
    private void dropIfExists(Connection conn, Statement statement, String tableName){
        try {
            tableName = tableName.toUpperCase();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rs =
                    dbMetaData.getTables(null, null, tableName, null);
            if (rs.next()) {
                statement.execute("drop table " + tableName);
            }
        }catch(SQLException e){
            System.out.println("Fatal error occurred while dropping table: " + tableName);
            System.exit(1);
        }
    }

    /**
     * drops if table already exists then creates the flightPath table
     * @param conn connection to the database
     */
    private void createFlightPathTable(Connection conn){
        try {
            Statement statement = conn.createStatement();
            dropIfExists(conn, statement, "FLIGHTPATH");
            statement.execute("create table flightpath(" +
                    "orderNo char(8)," +
                    "fromLongitude double," +
                    "fromLatitude double," +
                    "angle integer," +
                    "toLongitude double," +
                    "toLatitude double)"
            );
        }catch(SQLException e){
            System.out.println("Fatal error creating table: flightpath");
            System.exit(1);
        }
    }

    /**
     * drops if table already exists then creates the flightPath table
     * @param conn connection to the database
     */
    private void createDeliveriesTable(Connection conn){
        try {
            Statement statement = conn.createStatement();
            dropIfExists(conn, statement, "DELIVERIES");
            statement.execute("create table deliveries(" +
                    "orderNo char(8)," +
                    "deliveredTo varchar(19)," +
                    "costInPence int)"
            );
        }catch(SQLException e){
            System.out.println("Fatal error creating table: deliveries");
            System.exit(1);
        }
    }

    /**
     * inserts a list of moves into the flightpath database
     * catches a sql exeption if it fails
     * @param conn connection to the database
     * @param moves a list of Move objects
     */
    private void insertMoves(Connection conn, ArrayList<Move> moves){
        try {
            PreparedStatement psMove = conn.prepareStatement(
                    "insert into flightpath values (?, ?, ?, ?, ?, ?)"
            );
            for (Move move : moves) {
                psMove.setString(1, move.getOrderNo());
                psMove.setString(2, Double.toString(move.getFromLongitude()));
                psMove.setString(3, Double.toString(move.getFromLatitude()));
                psMove.setString(4, Integer.toString(move.getAngle()));
                psMove.setString(5, Double.toString(move.getToLongitude()));
                psMove.setString(6, Double.toString(move.getToLatitude()));
                psMove.execute();
            }
        } catch (SQLException e){
            System.out.println("Fatal error occurred writing to the flightpath database");
            System.exit(1);
        }
    }
    /**
     * inserts a list of delivery details into the deliveries database
     * catches a sql exception if it fails
     * @param conn connection to the database
     * @param completedDeliveries a list of DeliveryDetails objects representing each delivery made by the drone
     */
    private void insertDeliveryDetails(Connection conn, ArrayList<DeliveryDetails> completedDeliveries){
        try {
            PreparedStatement psDeliv = conn.prepareStatement(
                    "insert into deliveries values (?, ?, ?)"
            );
            for (DeliveryDetails deliv : completedDeliveries) {
                psDeliv.setString(1, deliv.getOrderNo());
                psDeliv.setString(2, deliv.getDeliveredTo());
                psDeliv.setString(3, Integer.toString(deliv.getCostInPence()));
                psDeliv.execute();
            }
        } catch (SQLException e){
            System.out.println("Fatal error occurred writing to the deliveries database");
            System.exit(1);
        }
    }

    /**
     * creates a flightpath table in the database and inserts each move made by the drone into it
     * @param conn connection to the database
     * @param flightPath FlightPath object containing the moves made by the drone
     */
    private void writeFlightPath(Connection conn, FlightPath flightPath){
        createFlightPathTable(conn);
        insertMoves(conn, flightPath.getFlightPath());
    }

    /**
     * creates a deliveries table in the database and inserts each delivery made by the drone into it
     * @param conn connection to the database
     * @param deliveries Delivery object containing every delivery made by the drone
     */
    private void writeDeliveries(Connection conn, Deliveries deliveries){
        createDeliveriesTable(conn);
        insertDeliveryDetails(conn, deliveries.getCompletedDeliveries());
    }
}
