package uk.ac.ed.inf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Menus class, contains data on the menus' items as well as related functionality
 */
public class Menus {
    private static final String MENUS_JSON_DESTINATION = "/menus/menus.json";
    private static final int DELIVERY_CHARGE = 50; //50p delivery cost to be added to each delivery cost

    private String port;
    private String machineName;
    ArrayList<MenuDetails> menuDetailsList;//Contains menu data which is stored on instantiation of Menus class.
    private HashMap<String, Item> itemMap;

    /**
     * Constructor reads and stores menus information from the web server
     * @param machineName machine name
     * @param port port of the web server
     */
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
     * Makes a request to the web server for the JSON menus data and parses into an ArrayList(MenuDetails).
     * This method is called when an instance of Menus is created.
     * @return Returns an ArrayList(MenuDetails) with fields: name, location, menu filled with respect to the JSON file.
     */
    private ArrayList<MenuDetails> makeMenus(){ //todo can/should this be static? investigate
        Client client = new Client();
        String jsonMenuDetailsString = client.getResponse("http://" + machineName + ":" + port + MENUS_JSON_DESTINATION);

        Type listType = new TypeToken<ArrayList<MenuDetails>>(){}.getType();
        ArrayList<MenuDetails> menuDetailsList = new Gson().fromJson(jsonMenuDetailsString, listType);

        return menuDetailsList;
    }

    /**
     * Creates an map of type (String, Item) to enable a constant lookup of item cost and store location.
     * @return returns the map
     */
     private HashMap<String, Item> makeItemMap(){
        HashMap<String, Item> itemMap = new HashMap<String, Item>();
        for (MenuDetails menuDetails : menuDetailsList){ //Iterates through ArrayList of MenuDetails
            for(Item item : menuDetails.getMenu()){
                item.setStore(menuDetails.getName()); //Assign the name of the store to the store field in Item object

                item.setLongLat(new LongLat(menuDetails.getLocation(), machineName, port));
                itemMap.put(item.getItem(), item); //Add each item object to the map
            }
        }
        return itemMap;
    }
}


