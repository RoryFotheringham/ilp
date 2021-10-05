package uk.ac.ed.inf;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client { //TODO deal with the response codes outlined in Brief Article.

    private static final HttpClient client = HttpClient.newHttpClient();

    public String getResponse(String urlString) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlString)).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();

    }
}
