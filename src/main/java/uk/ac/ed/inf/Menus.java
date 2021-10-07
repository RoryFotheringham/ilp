package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Menus class, contains data on the menus and items as well as related functionality
 */
public class Menus {
    private static final int DELIVERY_CHARGE = 50; //50p delivery cost to be added to each delivery cost
    String port;
    String machineName;
    ArrayList<MenuDetails> menuDetailsList; //Contains menu data which is stored on instantiation of Menus class.

    public Menus(String port, String machineName){
        this.port = port;
        this.machineName = machineName;
        this.menuDetailsList = makeMenus();
    }

    /**
     * Makes a request to the web server for the JSON menus data and parses into an ArrayList<MenuDetails>.
     * This method is called when an instance of Menus is created.
     * @return Returns an ArrayList<MenuDetails> with fields: name, location, menu filled with respect to the JSON file.
     */
    private ArrayList<MenuDetails> makeMenus(){
        Client client = new Client("http://localhost:9898/menus/menus.json", "9898"); //TODO The values in the constructor don't do anything at the moment
        String jsonMenuDetailsString = client.getResponse("http://localhost:9898/menus/menus.json");

        Type listType = new TypeToken<ArrayList<MenuDetails>>(){}.getType();
        ArrayList<MenuDetails> menuDetailsList = new Gson().fromJson(jsonMenuDetailsString, listType);

        return menuDetailsList;
    }

    /**
     * Searches for and calculates the total delivery cost of an arbitrary number of items.
     * @param requestedItems arbitrary number of strings which the method will calculate the cost of delivering.
     * @return returns the total cost of the items including the delivery charge.
     */
    public int getDeliveryCost(String ... requestedItems){
        int totalCost = DELIVERY_CHARGE; //Value initialises to the delivery cost
        for (String requestedItem : requestedItems){
            boolean found = false; //Boolean tracks whether the requested item has been found
            for (MenuDetails menuDetails : menuDetailsList){ //2D loop searches through each item contained within each menu
                for (Item menuItem : menuDetails.menu){
                    if (menuItem.item.equals(requestedItem)){
                        totalCost += menuItem.pence;
                        found = true;
                    }
                    if (found){ break; } //break out of both loops when item has been found to reduce unnecessary searching
                }
                if (found){ break; }
            }
        }
        return totalCost;
    }
}
