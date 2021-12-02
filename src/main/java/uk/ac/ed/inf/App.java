package uk.ac.ed.inf;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class App
{
    public static void main( String[] args ){

        String day = args[0]; //verify that the first argument is a valid day
        if(Integer.parseInt(day) > 31 || Integer.parseInt(day) <= 0 || day.length() != 2){
            System.out.println("day = " + day);
            throw new IllegalArgumentException("invalid date argument: date must be in form DD MM YYYY");
        }
        String month = args[1];//verify that the second argument is a valid month
        if(Integer.parseInt(month) > 12 || Integer.parseInt(month) <= 0 || month.length() != 2){
            System.out.println("month = " + month);

            throw new IllegalArgumentException("invalid date argument: date must be in form DD MM YYYY");
        }
        String year = args[2];//verify that the third argument is a valid year
        if(year.length() != 4){
            System.out.println("year = " + year);
            throw new IllegalArgumentException("invalid date argument: date must be in form DD MM YYYY");
        }
        String portWeb = args[3];
        String portDB = args[4];
        String machineName = "localhost"; //there is no 'machineName' argument so assume that machine name is always "localhost"

        Date date_sql = Date.valueOf(year + "-" + month + "-" + day); //parse the date args into SQL date format for querying the database
        String date_std = day + "-" + month + "-" + year; //parse the date args into standard british format for creating filenames

        //read information from the web server and database
        Read read = new Read(machineName, portWeb, portDB, date_sql);
        Orders orders = read.getOrders();
        Area area = read.getArea();
        Deliveries deliveries = read.getDeliveries();
        //process the information into a flightPath and absolutePath
        Process process = new Process(orders, deliveries, area);
        ArrayList<Node> absolutePathList = process.getAbsPathList();
        FlightPath flightPath = process.getFlightPath();
        //create geoJson file from flightPath and absolutePath
        Write write = new Write(flightPath, absolutePathList, deliveries, date_std, machineName, portDB);
        //finds the flightpath of 7 randomly sampled days and calculates and prints the average percentage monetary value
        EvaluateViability eval = new EvaluateViability(machineName, portDB, portWeb);
    }
}
