package uk.ac.ed.inf;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ){

        Menus menus = new Menus("port", "machineName" );
        System.out.println(menus.menuDetailsList.get(0).name);

        System.out.println(menus.getDeliveryCost("Ham and mozzarella Italian roll","Steak bake", "African wrap"));

        System.out.println( "Hello World! How are you?" );
    }
}

