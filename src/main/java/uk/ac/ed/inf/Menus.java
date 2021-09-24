package uk.ac.ed.inf;

public class Menus {

    String machineName;
    String Port;

    public Menus(String name, String port){
    this.machineName = name;
    this.Port = port;
    }

    public int getDeliveryCost(String ... items){
        return 3;
    }

}
