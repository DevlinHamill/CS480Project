package Utils;

import Resource.Global;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Logger;

public class AvApiHelper {
    private static final String apiKey = "H7WKQIGFRDPFG4A5";
    private static final String baseUrl = "https://www.alphavantage.co/query?";
    private static Logger logger = Global.logger;

    public static class HomeScreenData {
        private String stockSymbol, currentValue, changeSinceClose, changeSinceClosePercent;

        public HomeScreenData(String stockSymbol, String currentValue, String changeSinceClose, String changeSinceClosePercent) {
            this.stockSymbol = stockSymbol;
            this.currentValue = currentValue;
            this.changeSinceClose = changeSinceClose;
            this.changeSinceClosePercent = changeSinceClosePercent;
        }

        public String getStockSymbol() {
            return stockSymbol;
        }

        public String getCurrentValue() {
            return currentValue;
        }

        public String getChangeSinceClose() {
            return changeSinceClose;
        }

        public String getChangeSinceClosePercent() {
            return changeSinceClosePercent;
        }
    }

    public AvApiHelper() {

    }

    public HomeScreenData getHomeScreenData(String symbol) {
        logger.info(String.format("Running %s.getHomeScreenData()", this.getClass().getName()));
    }

    private JSONObject getJson(String query) {
        logger.info(String.format("Running %s.getHomeScreenData(%s)", this.getClass().getName(), query));
        JSONObject jsonResponse = null;

        try {
            // Create a URL object
            URL url = new URI(query).toURL();

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
                logger.warning("GET request failed. Response Code: " + responseCode);
            }

            connection.disconnect(); // Close the connection

        } catch (Exception e) {
            logger.warning("Error in getJSON: " + e.getMessage());
        }

        return jsonResponse;
    }
}