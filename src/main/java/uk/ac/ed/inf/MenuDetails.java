package uk.ac.ed.inf;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MenuDetails class contains name, location and item information of a menu.
 * An ArrayList of MenuDetails is contained within the Menus class.
 */
public class MenuDetails {
    private String name;
    private String location;
    private ArrayList<Item> menu;

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<Item> getMenu() {
        return menu;
    }
}
