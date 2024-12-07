package Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AvApiHelper {
    private static final String apiKey = "H7WKQIGFRDPFG4A5";
    private static final String baseUrl = "https://www.alphavantage.co/query?";

    public String[] getHomeScreenData() {

    }

    private static JSONObject getJson(String query) {
        JSONObject jsonResponse = null;

        try {
            // Create a URL object
            URI uri = new URI(query);
            URL url = uri.toURL();

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set HTTP method
            connection.setRequestProperty("Accept", "application/json"); // Specify JSON response

            // Check response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // init JSON
                jsonResponse = new JSONObject(response.toString());
            }
            else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            connection.disconnect(); // Close the connection

        } catch (Exception e) {
            System.out.println("Error in getJSON: " + e.getMessage());
        }

        return jsonResponse;
    }

    private static JSONObject getRealTimeBulkJson() {

    }
}