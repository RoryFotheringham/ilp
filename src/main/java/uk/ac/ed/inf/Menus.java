package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.what3words.javawrapper.What3WordsV3;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Menus class, contains data on the menus' items as well as related functionality
 */
public class Menus {
    private static final String MENUS_JSON_DESTINATION = "/menus/menus.json";
    private static final int DELIVERY_CHARGE = 50; //50p delivery cost to be added to each delivery cost

    String port;
    String machineName;
    ArrayList<MenuDetails> menuDetailsList;//Contains menu data which is stored on instantiation of Menus class.
    HashMap<String, Item> itemMap;


    public Menus(String machineName, String port){
        this.port = port;
        this.machineName = machineName;
        this.menuDetailsList = makeMenus();
        this.itemMap = makeItemMap();
    }

    public HashMap<String, Item> getItemMap() {
        return itemMap;
    }

    /**
     * Makes a request to the web server for the JSON menus data and parses into an ArrayList<MenuDetails>.
     * This method is called when an instance of Menus is created.
     * @return Returns an ArrayList<MenuDetails> with fields: name, location, menu filled with respect to the JSON file.
     */
    private ArrayList<MenuDetails> makeMenus(){ //todo can/should this be static? investigate
        Client client = new Client();
        String jsonMenuDetailsString = client.getResponse("http://" + machineName + ":" + port + MENUS_JSON_DESTINATION);

        Type listType = new TypeToken<ArrayList<MenuDetails>>(){}.getType();
        ArrayList<MenuDetails> menuDetailsList = new Gson().fromJson(jsonMenuDetailsString, listType);

        return menuDetailsList;
    }

    /**
     * Creates an map of type <String, Item> to enable a constant lookup of item cost and store location.
     * @return returns the map
     */
     private HashMap<String, Item> makeItemMap(){
        HashMap<String, Item> itemMap = new HashMap<String, Item>();
        for (MenuDetails menuDetails : menuDetailsList){ //Iterates through ArrayList of MenuDetails
            for(Item item : menuDetails.menu){
                item.store = menuDetails.name; //Assign the name of the store to the store field in Item object

                item.longLat = new LongLat(menuDetails.location, machineName, port);
                itemMap.put(item.item, item); //Add each item object to the map
            }
        }
        return itemMap;
    }

    /**
     * Calculates the total delivery cost of an arbitrary number of items.
     * @param requestedItems arbitrary number of strings which the method will calculate the cost of delivering.
     * @return returns the total cost of the items including the delivery charge.
     */
    public int getDeliveryCost(String ... requestedItems){
        int totalCost = DELIVERY_CHARGE;
        for (String itemName : requestedItems){
            Item item = itemMap.get(itemName);
            totalCost += item.pence;
        }
        return totalCost;
    }
}


