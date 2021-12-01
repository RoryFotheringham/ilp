package uk.ac.ed.inf;

import java.sql.*;
import java.util.ArrayList;

public class EvaluateViability {
    private static final int NUMBER_OF_SAMPLES = 7;
    private ArrayList<Date> sampledDates;
    private double sampledAvePercentage = 0;
    private String portDB;
    private String portWeb;
    private String machineName;

    public EvaluateViability(String machineName, String portDB, String portWeb) {
        this.portDB = portDB;
        this.portWeb = portWeb;
        this.machineName = machineName;
        Connection conn = getConnection();
        this.sampledDates = sampleDates(conn);
        this.sampledAvePercentage = findSampledAvePercentage(this.sampledDates);
        printSampledAvePercentage();
    }

    public void printSampledAvePercentage() {
        System.out.println("The average percentage monetary value of the service over " + NUMBER_OF_SAMPLES +
                " randomly sampled days is: " + sampledAvePercentage + "%");
        System.out.print("The sampled dates where: ");
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
     * reads every order from the database and finds selects 7 random dates
     * @param conn connection to the database
     * @return a list of 7 unique, random dates for which there are orders made
     */
    private ArrayList<Date> sampleDates(Connection conn) {
        ArrayList<Date> allDates = new ArrayList<Date>();
        try {
            final String datesQuery = "select * from orders";
            Statement statement = conn.createStatement();
            ResultSet rsDates = statement.executeQuery(datesQuery);
            while (rsDates.next()) {
                String deliveryDate = rsDates.getString("deliveryDate");
                allDates.add(java.sql.Date.valueOf(deliveryDate));
            }
        } catch (SQLException e){
            System.out.println("Fatal error while reading from orders database");
        }
        ArrayList<Date> sampledDates = new ArrayList<Date>();

        for(int i = 0; i < NUMBER_OF_SAMPLES; i++){
            int randomIndex = (int)(Math.random() * allDates.size() - 1);
            int maxIter = 100;
            while(sampledDates.contains(allDates.get(randomIndex)) && maxIter >= 0) {
                maxIter --;
                randomIndex = (int) (Math.random() * allDates.size() - 1);
            }
            sampledDates.add(allDates.get(randomIndex));
        }
        return sampledDates;
    }

    private double findSampledAvePercentage(ArrayList<Date> dates){
        ArrayList<Double> percentages = new ArrayList<Double>();
        for (Date date : dates){
            Read read = new Read(this.machineName, portWeb, portDB, date);
            Process process = new Process(read.getOrders(), read.getDeliveries(), read.getArea());
            // deliveries, at this stage, will have been updated in Process and now contains information on every completed delivery
            double percentage = findPercentageMonetaryValue(read.getDeliveries());
            percentages.add(percentage);
        }
        return findMean(percentages);
    }

    public double findPercentageMonetaryValue(Deliveries deliveries){
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
