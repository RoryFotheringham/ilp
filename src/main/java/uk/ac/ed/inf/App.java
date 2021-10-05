package uk.ac.ed.inf;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        Client client = new Client();
        System.out.print(client.getResponse("http://localhost:9898/menus/menus.json"));
        System.out.println( "Hello World! How are you?" );
    }
}

