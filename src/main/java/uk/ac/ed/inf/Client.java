package uk.ac.ed.inf;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client class manages responses and requests from the server and handles errors appropriately.
 */
public class Client {

    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     *Method that creates and sends an HttpRequest to a given server.
     *A fatal error occurs resulting in application exit if:
     * - The URL string is syntactically or semantically incorrect,
     * - The server is not running,
     * - The server gives a response code other than 200.
     * @param urlString Address of the resource
     * @return returns a String of the resource body
     */
    public String getResponse(String urlString){
        //Forms a URI string for use in request
        //Catches syntactic error in URL string
        URI uriString = null;
        try{
            uriString = URI.create(urlString);
        }
        catch (Exception e) {
            System.out.println("Fatal Error: URL string: " + urlString + " is syntactically incorrect");
            System.exit(1); //Exit the application
        }

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder().uri(uriString).build(); //Builds an http request
        }catch (Exception e){
            System.out.println("Fatal Error: Error connecting to server");
            System.exit(1);

        }
        //Sends the https request to the server and catches exception
        //Returns a response
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            System.out.println("Fatal Error: Unable to connect to server");
            System.exit(1);//Exit the application
        }

        if (response.statusCode() != 200){
            System.out.println("Fatal Error: Unexpected response code: " + response.statusCode());
            System.exit(1); //Exit the application
        }
        return response.body();
    }
}
