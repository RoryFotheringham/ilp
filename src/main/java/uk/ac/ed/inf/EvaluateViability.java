package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsible for finding the sampled average percentage monetary value
 */
public class EvaluateViability {
    private static final int NUMBER_OF_SAMPLES = 7;
    private ArrayList<Date> sampledDates;
    private double sampledAvePercentage = 0;
    private String portDB;
    private String portWeb;
    private String machineName;

    /**
     * basic constructor, finds and prints the sampled average monetary value when called
     * @param machineName machine name
     * @param portDB port of the database
     * @param portWeb port of the web server
     */
    public EvaluateViability(String machineName, String portDB, String portWeb) {
        this.portDB = portDB;
        this.portWeb = portWeb;
        this.machineName = machineName;
        Connection conn = getConnection();
        this.sampledDates = sampleDates(conn);
        this.sampledAvePercentage = findSampledAvePercentage(this.sampledDates);
        printSampledAvePercentage();
        closeConnection(conn);
    }

    /**
     * prints the calculated sampled average percentage to the console and prints the dates it was sampled from
     */
    public void printSampledAvePercentage() {
        System.out.println("The average percentage monetary value of the service over " + NUMBER_OF_SAMPLES +
                " randomly sampled days is: " + sampledAvePercentage + "%");
        System.out.print("The sampled dates were: ");
        for(Date date : sampledDates){
            System.out.print(date.toString() + ", ");
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
     * reads every order from the database and finds selects 7 random dates
     * @param conn connection to the database
     * @return a list of 7 unique, random dates for which there are orders made
     */
    private ArrayList<Date> sampleDates(Connection conn) {
        ArrayList<Date> allDates = new ArrayList<Date>();
        try {
            final String datesQuery = "select * from orders"; // reads all of the orders from the database
            Statement statement = conn.createStatement();
            ResultSet rsDates = statement.executeQuery(datesQuery);
            while (rsDates.next()) {
                String deliveryDate = rsDates.getString("deliveryDate");
                allDates.add(java.sql.Date.valueOf(deliveryDate)); // stores the delivery date of each order in allDates
            }
        } catch (SQLException e){
            System.out.println("Fatal error while reading from orders database");
        }
        ArrayList<Date> sampledDates = new ArrayList<Date>();

        for(int i = 0; i < NUMBER_OF_SAMPLES; i++){
            int randomIndex = (int)(Math.random() * allDates.size() - 1); // finds a random number that would be a valid index for allDates
            int maxIter = 100;// if there are less than 7 dates in the whole database, then repeat dates will be selected
            while(sampledDates.contains(allDates.get(randomIndex)) && maxIter >= 0) {
                // checks that the randomly selected date hasn't been selected already
                maxIter --;
                randomIndex = (int) (Math.random() * allDates.size() - 1);
            }
            sampledDates.add(allDates.get(randomIndex));
        }
        return sampledDates;
    }

    /**
     * calculates a flightpath for the orders placed on each randomly sampled date
     * and finds the mean percentage monetary value over all of the flightpaths
     * @param dates the randomly sampled dates
     * @return the mean percentage monetary value over all of the flightpaths
     */
    private double findSampledAvePercentage(ArrayList<Date> dates){
        ArrayList<Double> percentages = new ArrayList<Double>();
        for (Date date : dates){
            //reads and stores the data from the database for a sampled date
            Read read = new Read(this.machineName, portWeb, portDB, date);
            //calculates the flightpath for the given date
            Process process = new Process(read.getOrders(), read.getDeliveries(), read.getArea());
            // deliveries, at this stage, will have been updated in Process and now contains information on every completed delivery
            double percentage = findPercentageMonetaryValue(read.getDeliveries());
            percentages.add(percentage);
        }
        return findMean(percentages);
    }

    /**
     * finds the monetary value of the orders made by the drone
     * divided by the monetary value of the orders placed for the given date
     * (multiplied by 100 to convert to percentage)
     * @param deliveries object that stores the deliveries made and the deliveries placed for a given day
     * @return the percentage monetary value
     */
    private double findPercentageMonetaryValue(Deliveries deliveries){
        double totalMonetaryValue = 0;
        for(DeliveryDetails delivery : deliveries.getAllDeliveries()){
            totalMonetaryValue += delivery.getCostInPence();
        }
        double actualMonetaryValue = 0;
        for(DeliveryDetails delivery : deliveries.getCompletedDeliveries()){
            actualMonetaryValue += delivery.getCostInPence();
        }
        return (actualMonetaryValue / totalMonetaryValue * 100);
    }

    private static double findMean(ArrayList<Double> percentages){
        double sumPercentages = 0;
        for(double percentage : percentages){
            sumPercentages += percentage;
        }
        return (sumPercentages / percentages.size());
    }

}
