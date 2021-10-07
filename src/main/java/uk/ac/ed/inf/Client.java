package uk.ac.ed.inf;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    //TODO check that I don't need to check the type of each exception for error handling and whether each exception results in a fatal error.
    String port;
    String server;

    public Client(String port, String server){
        this.port = port;
        this.server = server;
    }

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

        HttpRequest request = HttpRequest.newBuilder().uri(uriString).build(); //Builds an http request

        //Sends the https request to the server and catches exception
        //Returns a response
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch(Exception e){
            System.out.println("Fatal Error: Unable to connect to: " + server + " at port: " + port);
            System.exit(1);//Exit the application
        }

        //Checks the responses status code for a 404 error
        if (response.statusCode() == 404){
            System.out.println("Fatal Error: 404 - Requested resource could not be found");
            System.exit(1); //Exit the application
        }

        if (response.statusCode() != 200){
            System.out.println("Fatal Error: Unexpected response code: " + response.statusCode());
            System.exit(-1); //Exit the application (status: -1 indicates unexpected error)
        }
        return response.body();
    }
}
