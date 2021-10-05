package uk.ac.ed.inf;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gson.Gson;

public class Menus {

    Collection<Shop> shops;
    String machineName;//TODO perhaps process these instance vars into the urlString that is called in Client
    String Port;

    public Menus(String name, String port){
        this.machineName = name;
        this.Port = port;
    }

    public void makeMenus() throws IOException, InterruptedException { //TODO figure out what's going on with these exceptions
        Client client = new Client();
        String menus = client.getResponse("http://localhost:9898/menus/menus.json");

        //TODO Unmarshal with GSON and work with Collection<Shop> shops

    }



    public int getDeliveryCost(String ... items){
        for (String item : items){

        }

        return 3;
    }

}
