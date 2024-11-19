import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlphaVantageApiHelper {
    private static final String apiKey = "H7WKQIGFRDPFG4A5";
    private static final String baseUrl = "https://www.alphavantage.co/query?";

    //intraday example
    //https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=demo

    //daily example
    //https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&outputsize=full&apikey=demo

    //Ticker search
    //https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=tesco&apikey=demo

    public static void main(String[] args) {
        runApiTest();
    }

    public static JSONObject getMetadataJSON(JSONObject rootJSON) {
        return rootJSON.getJSONObject("Meta Data");
    }

    public static boolean stockExists(String keyword) {
        boolean found = false;
        String request = baseUrl + "function=SYMBOL_SEARCH" + "&keywords=" + keyword + "&apikey=" + apiKey;
        JSONObject jsonResponse = getJSON(request);

        System.out.println();

        JSONArray jsonArray = jsonResponse.getJSONArray(jsonResponse.keys().next());

        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);

            if(temp.get("1. symbol").toString().equals(keyword)) {
                found = true;
                break;
            }
        }

        return found;
    }

    public static JSONObject getIntradayJSON(String symbol, String interval) {
        String request = baseUrl + "function=TIME_SERIES_INTRADAY" + "&symbol=" + symbol + "&interval=" + interval + "&apikey=" + apiKey;
        return getJSON(request);
    }

    public static JSONObject getDailyJSON(String symbol, boolean compact) {
        String request = baseUrl + "function=TIME_SERIES_DAILY" + "&symbol=" + symbol + "&outputsize=" + (compact? "compact" : "full") + "&apikey=" + apiKey;
        return getJSON(request);
    }

    public static JSONObject getJSON(String urlString) {
        JSONObject jsonResponse = null;

        try {
            // Create a URL object
            URI uri = new URI(urlString);
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
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public static HashMap<String, String> getHomeScreenData (String symbol) {
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.STOCK_SYMBOL, symbol);

        //get most recent stock price using interday
        JSONObject intraday = getIntradayJSON(symbol, "1min");
        String mostRecentIntradayStockPrice = intraday
                .getJSONObject("Time Series (1min)")
                .getJSONObject(getMetadataJSON(intraday)
                        .getString("3. Last Refreshed"))
                .getString("4. close");

        //store most recent stock price in data
        data.put(Constants.CURRENT_VALUE, mostRecentIntradayStockPrice);

        //get previous close price using daily
        JSONObject daily = getDailyJSON(symbol, true);
        String prevCloseStockPrice = daily
                .getJSONObject("Time Series (Daily)")
                .getJSONObject(getMetadataJSON(daily)
                        .getString("3. Last Refreshed"))
                .getString("4. close");

        //store previous close price in data
        data.put(Constants.PREVIOUS_CLOSE, prevCloseStockPrice);

        double prevCloseValue = Double.parseDouble(prevCloseStockPrice);
        double change = prevCloseValue - Double.parseDouble(mostRecentIntradayStockPrice);

        data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE, (change >= 0.0 ? "+" : "") + String.format("%.2f", change));
        data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE, (change >= 0.0 ? "+" : "") + String.format("%.2f", 100 * (change / prevCloseValue)) + "%");

        return data;
    }

    public static void runApiTest() {
        System.out.println("test getIntradayJSON(\"IBM\", \"1min\"):");
        JSONObject jsonResponse = getIntradayJSON("IBM", "1min");

        //print the first layer JSONObject keys, if there is an error print the error message
        System.out.println("Level 1 Keys:");
        for (String key : jsonResponse.keySet()) {
            if(key.equals("Error Message")) {
                System.out.println("Error Message: " + jsonResponse.get("Error Message"));
            }
            else {
                System.out.println(key);
            }
        }

        //print the second layer JSONObject keys and their values
        System.out.println("\nLevel 2 Keys:");
        for(String key : jsonResponse.keySet()) {
            System.out.println("\n" + key + " Keys and values:");
            for (String key2 : jsonResponse.getJSONObject(key).keySet()) {
                System.out.println(key2 + ": " + jsonResponse.getJSONObject(key).get(key2));
            }
        }

        System.out.println("test getDailyJSON(\"IBM\", true):");
        jsonResponse = getDailyJSON("IBM", true);

        //print the first layer JSONObject keys, if there is an error print the error message
        System.out.println("Level 1 Keys:");
        for (String key : jsonResponse.keySet()) {
            if(key.equals("Error Message")) {
                System.out.println("Error Message: " + jsonResponse.get("Error Message"));
            }
            else {
                System.out.println(key);
            }
        }

        //print the second layer JSONObject keys and their values
        System.out.println("\nLevel 2 Keys:");
        for(String key : jsonResponse.keySet()) {
            System.out.println("\n" + key + " Keys and values:");
            for (String key2 : jsonResponse.getJSONObject(key).keySet()) {
                System.out.println(key2 + ": " + jsonResponse.getJSONObject(key).get(key2));
            }
        }

        System.out.println("test stockExists(\"IBM\"):");
        Scanner keyboard = new Scanner(System.in);
        System.out.print("\nEnter a stock symbol to check: ");
        String symbol = keyboard.next();
        System.out.println(symbol + " found: " + stockExists(symbol));

        if(stockExists(symbol)) {
            System.out.println("test getHomeScreenData(\"" + symbol + "\"):");
            HashMap<String, String> homeScreenData = getHomeScreenData(symbol);

            for(String key : homeScreenData.keySet()) {
                System.out.println("Key: " + key + ", Data: " + homeScreenData.get(key));
            }
        }
    }
}