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
    //https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=IBM&interval=5min&apikey=H7WKQIGFRDPFG4A5

    //daily example
    //https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=IBM&outputsize=full&apikey=H7WKQIGFRDPFG4A5

    //Ticker search
    //https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=tesco&apikey=demo

    public static void main(String[] args) {
        runApiTest();
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
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        return jsonResponse;
    }

    public static JSONObject getMetadataJSON(JSONObject rootJSON) {
        return rootJSON.getJSONObject("Meta Data");
    }

    public static JSONObject getIntradayJSON(String symbol, String interval) {
        String request = baseUrl + "function=TIME_SERIES_INTRADAY" + "&symbol=" + sanitizeStockSymbol(symbol) + "&interval=" + interval + "&apikey=" + apiKey;
        return getJSON(request);
    }

    public static JSONObject getDailyJSON(String symbol, boolean compact) {
        String request = baseUrl + "function=TIME_SERIES_DAILY_ADJUSTED" + "&symbol=" + sanitizeStockSymbol(symbol) + "&outputsize=" + (compact? "compact" : "full") + "&apikey=" + apiKey;
        return getJSON(request);
    }
    
    public static JSONObject getOverviewJSON(String symbol) {
        String request = baseUrl + "function=OVERVIEW" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
        return getJSON(request);
    }
    
    public static JSONObject getGlobalQuoteJSON(String symbol) {
        String request = baseUrl + "function=GLOBAL_QUOTE" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
        return getJSON(request);
    }
    
    public static JSONObject getEarningsJSON(String symbol) {
        String request = baseUrl + "function=EARNINGS" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
        return getJSON(request);
    }
    public static JSONObject getHistoricalJSON(String symbol) {
        String request = baseUrl + "function=HISTORICAL_OPTIONS" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
        return getJSON(request);
    }
    
    public static String sanitizeStockSymbol(String input) {
        String returnString = "";

        if (input != null) {
            returnString = input.replaceAll("[^A-Z\\.]", "");
        }
        return returnString;
    }

    public static boolean stockExists(String keyword) {
        boolean found = false;
        String request = baseUrl + "function=SYMBOL_SEARCH" + "&keywords=" + keyword + "&apikey=" + apiKey;

        //make sure the stock symbol is in the valid format before seeing if it's in the database
        if(keyword.equals(sanitizeStockSymbol(keyword))) {
            JSONObject jsonResponse = getJSON(request);
            JSONArray jsonArray = jsonResponse.getJSONArray(jsonResponse.keys().next());

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject temp = jsonArray.getJSONObject(i);

                if(temp.get("1. symbol").toString().equals(keyword)) {
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    public static HashMap<String, String> getHomeScreenData (String symbol) {
        HashMap<String, String> data = new HashMap<>();
        
        /*
         * stock symbol
         */
        data.put(Constants.STOCK_SYMBOL, symbol);
        JSONObject Global = getGlobalQuoteJSON(symbol);
        /**
         * price
         */
        String price = Global
       		 .getJSONObject("Global Quote")
              .getString("05. price");
        
        price = String.format("%.2f", Double.parseDouble(price));
        
        data.put(Constants.CURRENT_VALUE, price);
        /**
         * change
         */
        String Change = Global
       		 .getJSONObject("Global Quote")
              .getString("09. change");
        
        Change = String.format("%.2f", Double.parseDouble(Change));
        
        data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE, Change);
        /**
         * change percentage
         */
        
        String changePercentage = Global
       		 .getJSONObject("Global Quote")
                .getString("10. change percent");
        
        data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE, changePercentage);
        
       
        
        return data;
    }
    
    public static HashMap<String, String> getStockViewData(String symbol){
    	
    	HashMap<String, String> ViewPageData = new HashMap<String, String>();
    	
    	/**
    	 * Overview connection
    	 */
    	 JSONObject Overview = getOverviewJSON(symbol);
    	 JSONObject Global = getGlobalQuoteJSON(symbol);
    	 JSONObject Daily = getDailyJSON(symbol, true);
    	 JSONObject Earning = getEarningsJSON(symbol);
    	 JSONObject Historical = getHistoricalJSON(symbol);
    	 
    	 /**
    	  * name string
    	  */
         String Name = Overview
                 .getString("Name");
         ViewPageData.put(Constants.NAME, Name);
         
         /**
          * Previous close
          */
         
         String PreviousClose = Global
        		 .getJSONObject("Global Quote")
                 .getString("08. previous close");
         
         PreviousClose = String.format("%.2f", Double.parseDouble(PreviousClose));
         
         ViewPageData.put(Constants.PREVIOUS_CLOSE, PreviousClose);
         
         /**
          * Open
          */
         
         String Open = Daily
                 .getJSONObject("Time Series (Daily)")
                 .getJSONObject(getMetadataJSON(Daily)
                         .getString("3. Last Refreshed"))
                 .getString("1. open");
         
         Open = String.format("%.2f", Double.parseDouble(Open));
         ViewPageData.put(Constants.OPEN, Open);
         
         /**
          * Bid
          * 
          */
         
         String Bid = Historical
        		 .getJSONArray("data")
                 .getJSONObject(0)
         		.getString("bid");
         String Bidsize = Historical
        		 .getJSONArray("data")
                 .getJSONObject(0)
         		.getString("bid_size");
         ViewPageData.put(Constants.BID, Bid+ " x "+Bidsize);
         
         /**
          * Ask
          * 
          */
         
         String Ask = Historical
        		 .getJSONArray("data")
                 .getJSONObject(0)
         		.getString("ask");
         
         String Asksize = Historical
        		 .getJSONArray("data")
                 .getJSONObject(0)
         		.getString("ask_size");
         
         ViewPageData.put(Constants.ASK, Ask+ " x "+ Asksize);
         
         
         /**
          * Days Range
          */
         
         String low = Global
        		 .getJSONObject("Global Quote")
                 .getString("04. low");
         
         String high = Global
        		 .getJSONObject("Global Quote")
                 .getString("03. high");
         
         String highstr = String.format("%.2f", Double.parseDouble(high));
         String lowstr = String.format("%.2f", Double.parseDouble(low));
         String Days_Range = lowstr + " - " +highstr;
         ViewPageData.put(Constants.DAYSRANGE, Days_Range);
         
         /**
          * 52 week range
          */
         
         String FiftyTwo_week_low = Overview
                 .getString("52WeekLow");
         String FiftyTwo_week_high = Overview
                 .getString("52WeekHigh");
         
         String FiftyTwo_week_low_str = String.format("%.2f", Double.parseDouble(FiftyTwo_week_low));
         String FiftyTwo_week_high_str = String.format("%.2f", Double.parseDouble(FiftyTwo_week_high));
         
         String week_range = FiftyTwo_week_low_str+ " - " + FiftyTwo_week_high_str;
         
         ViewPageData.put(Constants.FIFTYTWO_WEEKRANGE, week_range);
         
         /**
          * Volume
          */
         String volume = Global
        		 .getJSONObject("Global Quote")
                 .getString("06. volume");
         ViewPageData.put(Constants.VOLUME, volume);
         
         
         /**
          * Avg volume
          */
         
         
         JSONObject dailyData = getDailyJSON(symbol, true);
         JSONObject timeSeries = dailyData.getJSONObject("Time Series (Daily)");
         
         int count = 0;
         double totalVolume = 0;

         for (String date : timeSeries.keySet()) {
             if (count > 7) {break;}
             Double currentVolume = timeSeries
            		 .getJSONObject(date)
            		 .getDouble("6. volume");
             		
             totalVolume += (currentVolume);
             count++;
         }
         
         Double avgvolume = totalVolume / (double)count;
         
         
         ViewPageData.put(Constants.AVERAGE_VOLUME, ""+avgvolume);
         
         /**
          * Market cap intraday
          */
         JSONObject intraday = getIntradayJSON(symbol, "5min");
         String currentDay = getMetadataJSON(intraday).getString("3. Last Refreshed");
         String mostRecentIntradayStockPrice = intraday
                 .getJSONObject("Time Series (5min)")
                 .getJSONObject(getMetadataJSON(intraday)
                         .getString("3. Last Refreshed"))
                 .getString("4. close");
         String outstandingshares = Overview.getString("SharesOutstanding");
         
         double total = Double.parseDouble(mostRecentIntradayStockPrice) * Double.parseDouble(outstandingshares);
         double billions = total/(1000000000);
         
         String formatBillions = String.format("%.3f", billions);
         
         
         ViewPageData.put(Constants.MARKETCAP_INTRADAY, formatBillions+"B");
         
         
                 
         /**
          * Beta (5y monthly)
          */
         
         String beta = Overview.getString("Beta");
         
         ViewPageData.put(Constants.BETA_5Y_MONTHLY, beta);
         
         /**
          * PE Ratio (TTM)
          */
         
         String PE_Ratio = Overview.getString("PERatio");
         
         ViewPageData.put(Constants.PE_RATION_TTM, PE_Ratio);
         
         
         /**
          * EPS (TTM)
          */
         
         String EPS = Overview.getString("EPS");
         
         ViewPageData.put(Constants.EPS_TTM, EPS);
         
         /**
          * Earning Date
          */
         JSONArray earningsArray = Earning.getJSONArray("quarterlyEarnings");
         String EarningsDateEnd = earningsArray
        		 .getJSONObject(0)
        		 .getString("reportedDate");
         String EarningsDateStart = earningsArray
        		 .getJSONObject(0)
        		 .getString("fiscalDateEnding");
         ViewPageData.put(Constants.EARNINGS_DATE, EarningsDateStart+" - "+EarningsDateEnd);
         
         
         /**
          * Forward dividend & yield
          */
         String dividendpershare = Overview.getString("DividendPerShare");
         String dividendyield = Overview.getString("DividendYield");
         double precentage = Double.parseDouble(dividendyield) * 100;
         
         String DivAndYield = dividendpershare+"("+precentage+"%)";
         ViewPageData.put(Constants.FORWARD_DIVIDEND_AND_YIELD, DivAndYield);
         
         /**
          * Ex-Dividend Date
          */
         
         String exDividendDate = Overview.getString("ExDividendDate");
         
         ViewPageData.put(Constants.EX_DIVIDEND_DATE, exDividendDate);
         
         /**
          * 1 year target estimate
          */
         
         Double Estimate = Double.parseDouble(EPS) * Double.parseDouble(PE_Ratio);
         String EstimateStr = String.format("%.2f", Estimate);
         ViewPageData.put(Constants.Y_TARGET_EST, ""+EstimateStr);
         
         System.out.println("Name: "+ViewPageData.get(Constants.NAME) + "\n"+
        		 "Previous close: "+ViewPageData.get(Constants.PREVIOUS_CLOSE) + "\n"+
        		 "Open: "+ViewPageData.get(Constants.OPEN) + "\n"+
        		 "Bid: "+ViewPageData.get(Constants.BID) + "\n"+
        		 "Ask: "+ViewPageData.get(Constants.ASK) + "\n"+
        		 "Days range: "+ViewPageData.get(Constants.DAYSRANGE) + "\n"+
        		 "52 week range: "+ViewPageData.get(Constants.FIFTYTWO_WEEKRANGE) + "\n"+
        		 "Volume: "+ViewPageData.get(Constants.VOLUME) + "\n"+
        		 "Average volume: "+ViewPageData.get(Constants.AVERAGE_VOLUME) + "\n"+
        		 "Market cap: "+ViewPageData.get(Constants.MARKETCAP_INTRADAY) + "\n"+
        		 "Beta: "+ViewPageData.get(Constants.BETA_5Y_MONTHLY) + "\n"+
        		 "PE ratio: "+ViewPageData.get(Constants.PE_RATION_TTM) + "\n"+
        		 "Eps: "+ViewPageData.get(Constants.EPS_TTM) + "\n"+
        		 "Earning date: "+ViewPageData.get(Constants.EARNINGS_DATE) + "\n"+
        		 "Forward div and yield: "+ViewPageData.get(Constants.FORWARD_DIVIDEND_AND_YIELD) + "\n"+
        		 "ex dividend date: "+ViewPageData.get(Constants.EX_DIVIDEND_DATE) + "\n"+
        		 "1y target est: "+ViewPageData.get(Constants.Y_TARGET_EST) + "\n");
         
         return ViewPageData;
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
        
        getStockViewData("IBM");
        
        
    }
}