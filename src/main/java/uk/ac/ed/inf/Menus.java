package uk.ac.ed.inf;


import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

public class Menus {

    Collection<Shop> shops;
    String machineName;
    String Port;

    //TODO unmarshall data from the website to the menus class then complete getDeliveryCost()
    // perhaps by searching through each menu item and storing each one that matches the string.


    private static final HttpClient client = HttpClient.newHttpClient();

    public Menus(String name, String port){
        this.machineName = name;
        this.Port = port;
    }

    public int getDeliveryCost(String ... items){
        for (String item : items){

        }

        return 3;
    }

}
