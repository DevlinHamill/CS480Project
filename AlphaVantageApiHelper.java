package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author StockFullyOptomistic
 * CS 480
 */

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

    /**
     * retrieves a json object
     * @param urlString inputs a url string
     * @return a JSONObject
     */
    public static JSONObject getJSON(String urlString) {
        JSONObject jsonResponse = null;

        try {
            
        	/**
        	 * creates a URI using the url
        	 */
            URI uri = new URI(urlString);
            /**
             * transforms it to a url object
             */
            URL url = uri.toURL();

            
            /**
             * opens a connection
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set HTTP method
            connection.setRequestProperty("Accept", "application/json"); // Specify JSON response

            
            /**
             * checks response code
             */
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
               
            	/**
            	 * reads response
            	 */
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                /**
                 * Creates a string based on the response
                 */
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                /**
                 * intializes a JSON object from said response
                 */
                jsonResponse = new JSONObject(response.toString());
            }
            else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }

            connection.disconnect(); // Close the connection

        } catch (Exception e) {
            System.out.println("Error in getJSON: " + e.getMessage());
            jsonResponse = getJSON(urlString);
        }

        return jsonResponse;
    }

    /**
     * retrieves the metadata from the current input json
     * @param rootJSON input json being used to evaluate the data
     * @return returns metadata as a jsonobject
     */
    public static JSONObject getMetadataJSON(JSONObject rootJSON) {
    	/**
    	 * the json object being returned
    	 */
        JSONObject returnJson;

        try {
            returnJson = rootJSON.getJSONObject("Meta Data");
        } catch (JSONException e) {
            System.out.println("Error in getMetadataJSON: " + e.getMessage());
            returnJson = getMetadataJSON(rootJSON);
        }

        return returnJson;
    }

    /**
     * gets intraday json
     * @param symbol stock symbol being evaluated
     * @param interval the amount of time the data is being pulled
     * @return the jsonobject containing the info
     */
    public static JSONObject getIntradayJSON(String symbol, String interval) {
    	/**
    	 * used to return a jsonobject that contains intraday data
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=TIME_SERIES_INTRADAY" + "&symbol=" + sanitizeStockSymbol(symbol) + "&interval=" + interval + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getIntradayJSON: " + e.getMessage());
            returnJson = getIntradayJSON(symbol, interval);
        }

        return returnJson;
    }

    /**
     * Used to get daily info as a JSONObject
     * @param symbol stock symbol being evaluated
     * @param compact boolean that tells if the data is compact or not
     * @return the JSONObject that contains all daily data
     */
    public static JSONObject getDailyJSON(String symbol, boolean compact) {
    	/**
    	 * the data being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=TIME_SERIES_DAILY_ADJUSTED" + "&symbol=" + sanitizeStockSymbol(symbol) + "&outputsize=" + (compact ? "compact" : "full") + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getDailyJSON: " + e.getMessage());
            returnJson = getDailyJSON(symbol, compact);
        }

        return returnJson;
    }
    
    /**
     * gets overview json info
     * @param symbol stock symbol being evaluated
     * @return the JSONObject
     */
    public static JSONObject getOverviewJSON(String symbol) {
    	/**
    	 * JSONObject being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=OVERVIEW" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
            return getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getOverviewJSON: " + e.getMessage());
            returnJson = getOverviewJSON(symbol);
        }

        return returnJson;
    }
    /**
     * gets the JSONObject for a global quote
     * @param symbol the stock symbol being evaluated
     * @return the JSONObject
     */
    public static JSONObject getGlobalQuoteJSON(String symbol) {
    	/**
    	 * JSONObject being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=GLOBAL_QUOTE" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getGlobalQuoteJSON: " + e.getMessage());
            returnJson = getGlobalQuoteJSON(symbol);
        }

        return returnJson;
    }
    /**
     * gets the JSONObject for earnings
     * @param symbol the stock symbol being evaluated
     * @return the JSONObject
     */
    public static JSONObject getEarningsJSON(String symbol) {
    	/**
    	 * JSONObject being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=EARNINGS" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getEarningsJSON: " + e.getMessage());
            returnJson = getEarningsJSON(symbol);
        }

        return returnJson;
    }
    /**
     * gets the historical data as an JSONobject
     * @param symbol symbol being evaluated
     * @return the JSONObject
     */
    public static JSONObject getHistoricalJSON(String symbol) {
    	/**
    	 * JSONObject being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=HISTORICAL_OPTIONS" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getHistoricalJSON: " + e.getMessage());
            returnJson = getHistoricalJSON(symbol);
        }

        return returnJson;
    }
    
    /**
     * Retrieves monthly data as a JSONObject
     * @param symbol stock symbol being used refrenced
     * @return the JSONObject being evaluated
     */
    public static JSONObject getMonthlyJSON(String symbol) {
    	/**
    	 * JSONObject being returned
    	 */
        JSONObject returnJson;

        try {
            String request = baseUrl + "function=TIME_SERIES_MONTHLY" + "&symbol=" + sanitizeStockSymbol(symbol) + "&apikey=" + apiKey;
            returnJson = getJSON(request);
        } catch (Exception e) {
            System.out.println("Error in getMonthlyJSON: " + e.getMessage());
            returnJson = getMonthlyJSON(symbol);
        }

        return returnJson;
    }
    /**
     * sanitizes the stock symbol if any special characters exists
     * @param input the stock symbol being sanitized
     * @return the clean stock symbol
     */
    public static String sanitizeStockSymbol(String input) {
    	/**
    	 * the string thats being returned
    	 */
        String returnString = "";

        if (input != null) {
            returnString = input.replaceAll("[^A-Z0-9.-]", "");
        }
        return returnString;
    }

    /**
     * checks if the stock symbol exists
     * @param keyword the stock being checked
     * @return a boolean check if the stock symbol is found
     */
    public static boolean stockExists(String keyword) {
        boolean found = false;
        try {
        	/**
        	 * creates the request
        	 */
            String request = baseUrl + "function=SYMBOL_SEARCH" + "&keywords=" + keyword + "&apikey=" + apiKey;

            //make sure the stock symbol is in the valid format before seeing if it's in the database
            if (keyword.equals(sanitizeStockSymbol(keyword))) {
            	/**
            	 * creates a JSONObject from the request
            	 */
                JSONObject jsonResponse = getJSON(request);
                /**
                 * Creates the jsonArray from the JSONObject
                 */
                JSONArray jsonArray = jsonResponse.getJSONArray(jsonResponse.keys().next());

                for (int i = 0; i < jsonArray.length(); i++) {
                	/**
                	 * creates a temp JSONObject based on the current JSONObject value within the array
                	 */
                    JSONObject temp = jsonArray.getJSONObject(i);

                    if (temp.get("1. symbol").toString().equals(keyword)) {
                        found = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error in stockExists: " + e.getMessage());
            found = stockExists(keyword);
        }

        return found;
    }

    /**
     * retrieves homescreendata
     * @param symbol the current stock symbol being evaluated
     * @return the hashmap of the homescreen data
     */
    public static HashMap<String, String> getHomeScreenData (String symbol) {
    	/**
    	 * the hashmap being returned
    	 */
        HashMap<String, String> data = new HashMap<>();

        try {
            //stock symbol
            data.put(Constants.STOCK_SYMBOL, symbol);
            /**
             * global JSON request for the stock symbol
             */
            JSONObject Global = getGlobalQuoteJSON(symbol);


            
            /**
             * Saves the price from the global quote JSONObject
             */
            String price = Global
                    .getJSONObject("Global Quote")
                    .getString("05. price");

            price = String.format("%.2f", Double.parseDouble(price));

            data.put(Constants.CURRENT_VALUE, price);

            
            /**
             * Saves the change from last closed 
             */
            String Change = Global
                    .getJSONObject("Global Quote")
                    .getString("09. change");

            Change = String.format("%.2f", Double.parseDouble(Change));

            data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE, Change);

            //change percentage
            String changePercentage = Global
                    .getJSONObject("Global Quote")
                    .getString("10. change percent");

            data.put(Constants.CHANGE_SINCE_PREVIOUS_CLOSE_PERCENTAGE, changePercentage);
        } catch (Exception e) {
            System.out.println("Error in getHomeScreenData: " + e.getMessage());
            data = getHomeScreenData(symbol);
        }

        return data;
    }
    /**
     * saves the stock view data as a hashmap
     * @param symbol stock symbol being checked
     * @return the hashmap
     */
    public static HashMap<String, String> getStockViewData(String symbol){
    	HashMap<String, String> ViewPageData = new HashMap<String, String>();

        try {
            //Overview connection
        	/**
        	 * overview JSONObject
        	 */
            JSONObject Overview = getOverviewJSON(symbol);
            /**
             * Global JSONObject
             */
            JSONObject Global = getGlobalQuoteJSON(symbol);
            /**
             * Daily JSONObject
             */
            JSONObject Daily = getDailyJSON(symbol, true);
            /**
             * Earnings JSONObject
             */
            JSONObject Earning = getEarningsJSON(symbol);
            /**
             * Historical JSONObject
             */
            JSONObject Historical = getHistoricalJSON(symbol);
            /**
             * Monthly JSONObject
             */
            JSONObject Monthly = getMonthlyJSON(symbol);

            //name string
            /**
             * the name of the stock from overview
             */
            String Name = Overview
                    .getString("Name");
            ViewPageData.put(Constants.NAME, Name);

            
            /**
             * The previous closed from global
             */
            String PreviousClose = Global
                    .getJSONObject("Global Quote")
                    .getString("08. previous close");

            PreviousClose = String.format("%.2f", Double.parseDouble(PreviousClose));

            ViewPageData.put(Constants.PREVIOUS_CLOSE, PreviousClose);

            /**
             * the open value from daily
             */
            String Open = Daily
                    .getJSONObject("Time Series (Daily)")
                    .getJSONObject(getMetadataJSON(Daily)
                            .getString("3. Last Refreshed"))
                    .getString("1. open");

            Open = String.format("%.2f", Double.parseDouble(Open));
            ViewPageData.put(Constants.OPEN, Open);

            /**
             * the bid amount
             */
            String Bid = Historical
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getString("bid");
            /**
             * the bid size
             */
            String Bidsize = Historical
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getString("bid_size");
            ViewPageData.put(Constants.BID, Bid + " x " + Bidsize);

            
            /**
             * the Ask amount
             */
            String Ask = Historical
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getString("ask");
            /**
             * the ask size
             */
            String Asksize = Historical
                    .getJSONArray("data")
                    .getJSONObject(0)
                    .getString("ask_size");

            ViewPageData.put(Constants.ASK, Ask + " x " + Asksize);

            //Days Range
            /**
             * the low value from global
             */
            String low = Global
                    .getJSONObject("Global Quote")
                    .getString("04. low");
            /**
             * the high value from global
             */
            String high = Global
                    .getJSONObject("Global Quote")
                    .getString("03. high");
            
            /**
             * gets the proper high decimal format
             */
            String highstr = String.format("%.2f", Double.parseDouble(high));
            /**
             * gets the proper low decimal format
             */
            String lowstr = String.format("%.2f", Double.parseDouble(low));
            /**
             * creates the days range as a string
             */
            String Days_Range = lowstr + " - " + highstr;
            ViewPageData.put(Constants.DAYSRANGE, Days_Range);

            //52 week range
            /**
             * the Fifty two week low value from overview
             */
            String FiftyTwo_week_low = Overview
                    .getString("52WeekLow");
            /**
             * the fifty two week high value from overview
             */
            String FiftyTwo_week_high = Overview
                    .getString("52WeekHigh");
            
            /**
             * formats low properly
             */
            String FiftyTwo_week_low_str = String.format("%.2f", Double.parseDouble(FiftyTwo_week_low));
            /**
             * formats high properly 
             */
            String FiftyTwo_week_high_str = String.format("%.2f", Double.parseDouble(FiftyTwo_week_high));
            /**
             * creates the fifty two week range to be saved
             */
            String week_range = FiftyTwo_week_low_str + " - " + FiftyTwo_week_high_str;

            ViewPageData.put(Constants.FIFTYTWO_WEEKRANGE, week_range);

            //Volume
            /**
             * the volume from global
             */
            String volume = Global
                    .getJSONObject("Global Quote")
                    .getString("06. volume");
            ViewPageData.put(Constants.VOLUME, volume);

            //Avg volume
            /**
             * the average volume from daily
             */
            JSONObject dailyData = getDailyJSON(symbol, true);
            /**
             * creates an object array to store each volume for testing
             */
            Object[] obs = dailyData.keySet().toArray();

            if (HomeScreen.debug && false) {
                System.out.println("AlphaVantageApiHelper.getStockViewData daily JSONObj keyset");
                for (int i = 0; i < obs.length; i++) {
                    System.out.println((String) obs[i]);
                }
            }
            /**
             * creates a new time series connection
             */
            JSONObject timeSeries = dailyData.getJSONObject("Time Series (Daily)");
            
            /**
             * keeps track of the current count
             */
            int count = 0;
            /**
             * keeps track of the total volume
             */
            double totalVolume = 0;

            for (String date : timeSeries.keySet()) {
                if (count > 7) {
                    break;
                }
                /**
                 * retrieves the current volume
                 */
                Double currentVolume = timeSeries
                        .getJSONObject(date)
                        .getDouble("6. volume");

                totalVolume += (currentVolume);
                count++;
            }
            /**
             * calculates the average volume
             */
            Double avgvolume = totalVolume / (double) count;

            ViewPageData.put(Constants.AVERAGE_VOLUME, "" + avgvolume);

          
            /**
             * creates a new intraday JSON
             */
            JSONObject intraday = getIntradayJSON(symbol, "5min");
            /**
             * gets the the most recent intraday stock values
             */
            String mostRecentIntradayStockPrice = intraday
                    .getJSONObject("Time Series (5min)")
                    .getJSONObject(getMetadataJSON(intraday)
                            .getString("3. Last Refreshed"))
                    .getString("4. close");
            String outstandingshares = Overview.getString("SharesOutstanding");
            /**
             * uses the most recent intraday stock price and the outstanding shares to calculate the market cap
             */
            double total = Double.parseDouble(mostRecentIntradayStockPrice) * Double.parseDouble(outstandingshares);
            /**
             * turns it to a billions format
             */
            double billions = total / (1000000000);
            /**
             * formats the decimals
             */
            String formatBillions = String.format("%.3f", billions);

            ViewPageData.put(Constants.MARKETCAP_INTRADAY, formatBillions + "B");

            /**
             * retreives the beta from overview
             */
            String beta = Overview.getString("Beta");

            ViewPageData.put(Constants.BETA_5Y_MONTHLY, beta);

            /**
             * retrieves the PE ratio from overview
             */
            String PE_Ratio = Overview.getString("PERatio");

            if (PE_Ratio.equals("None")) {
                PE_Ratio = "0.0";
                System.out.println(Overview.getString("Name") + " PE Ratio set from \"None\" to \"0.0\"");
            }

            ViewPageData.put(Constants.PE_RATION_TTM, PE_Ratio);

            /**
             * retrieves EPS from overview
             */
            String EPS = Overview.getString("EPS");

            ViewPageData.put(Constants.EPS_TTM, EPS);

            /**
             * gets the earnings as an array
             */
            JSONArray earningsArray = Earning.getJSONArray("quarterlyEarnings");
            /**
             * retreives the earning date end from said array
             */
            String EarningsDateEnd = earningsArray
                    .getJSONObject(0)
                    .getString("reportedDate");
            /**
             * retrieves the earnings date start from said array
             */
            String EarningsDateStart = earningsArray
                    .getJSONObject(0)
                    .getString("fiscalDateEnding");
            ViewPageData.put(Constants.EARNINGS_DATE, EarningsDateStart + " - " + EarningsDateEnd);

            /**
             * gets the dividends per share
             */
            String dividendpershare = Overview.getString("DividendPerShare");
            /**
             * gets the dividend yield from overview
             */
            String dividendyield = Overview.getString("DividendYield");

            if(dividendyield.equals("None")) {
                dividendyield = "0.0";
                System.out.println(Overview.getString("Name") + " Dividend Yield set from \"None\" to \"0.0\"");
            }
            /**
             * formats the precentage from the dividend yield
             */
            double precentage = Double.parseDouble(dividendyield) * 100;
            /**
             * formate both the dividend per share and the percentage
             */
            String DivAndYield = dividendpershare + "(" + precentage + "%)";
            ViewPageData.put(Constants.FORWARD_DIVIDEND_AND_YIELD, DivAndYield);

            /**
             * retrieves the ex div date from overview
             */
            String exDividendDate = Overview.getString("ExDividendDate");
            
            ViewPageData.put(Constants.EX_DIVIDEND_DATE, exDividendDate);

            /**
             * 1 year target estimate
             */
            Double Estimate = Double.parseDouble(EPS) * Double.parseDouble(PE_Ratio);
            /**
             * formates the estimate string
             */
            String EstimateStr = String.format("%.2f", Estimate);
            ViewPageData.put(Constants.Y_TARGET_EST, "" + EstimateStr);

            /**
             * creates a new intraday JSON for the char data
             */
            JSONObject seriesintra = intraday.getJSONObject("Time Series (5min)");
            count = 0;
            /**
             * creates an array list that holds each entry and reverses the order
             */
            ArrayList<String> sortedDates = new ArrayList<>(seriesintra.keySet());
            Collections.sort(sortedDates, Collections.reverseOrder());

            for (String date : sortedDates) {
                /**
                 * gets the current close
                 */
                String currentclose = seriesintra
                        .getJSONObject(date)
                        .getString("4. close");
                /**
                 * formats the string
                 */
                String.format("%.2f", Double.parseDouble(currentclose));
                if (count >= 5) {
                    break;
                }
                if (count == 0) {
                    ViewPageData.put(Constants.INTRADAY_DATE_ONE, date);
                    ViewPageData.put(Constants.INTRADAY_VALUE_ONE, currentclose);
                } else if (count == 1) {
                    ViewPageData.put(Constants.INTRADAY_DATE_TWO, date);
                    ViewPageData.put(Constants.INTRADAY_VALUE_TWO, currentclose);
                } else if (count == 2) {
                    ViewPageData.put(Constants.INTRADAY_DATE_THREE, date);
                    ViewPageData.put(Constants.INTRADAY_VALUE_THREE, currentclose);
                } else if (count == 3) {
                    ViewPageData.put(Constants.INTRADAY_DATE_FOUR, date);
                    ViewPageData.put(Constants.INTRADAY_VALUE_FOUR, currentclose);
                } else if (count == 4) {
                    ViewPageData.put(Constants.INTRADAY_DATE_FIVE, date);
                    ViewPageData.put(Constants.INTRADAY_VALUE_FIVE, currentclose);
                }
                count++;
            }
            /**
             * creates a daily connection for the chart
             */
            JSONObject seriesdaily = Daily.getJSONObject("Time Series (Daily)");
            count = 0;
            /**
             * creates an array list to sort the data
             */
            ArrayList<String> sortedDatesDaily = new ArrayList<>(seriesdaily.keySet());
            Collections.sort(sortedDatesDaily, Collections.reverseOrder());
            for (String date : sortedDatesDaily) {
                /**
                 * retrieves the current close
                 */
                String currentclose = seriesdaily
                        .getJSONObject(date)
                        .getString("4. close");
                /**
                 * formats current close
                 */
                String.format("%.2f", Double.parseDouble(currentclose));
                if (count >= 5) {
                    break;
                }
                if (count == 0) {
                    ViewPageData.put(Constants.DAILY_DATE_ONE, date);
                    ViewPageData.put(Constants.DAILY_VALUE_ONE, currentclose);
                } else if (count == 1) {
                    ViewPageData.put(Constants.DAILY_DATE_TWO, date);
                    ViewPageData.put(Constants.DAILY_VALUE_TWO, currentclose);
                } else if (count == 2) {
                    ViewPageData.put(Constants.DAILY_DATE_THREE, date);
                    ViewPageData.put(Constants.DAILY_VALUE_THREE, currentclose);
                } else if (count == 3) {
                    ViewPageData.put(Constants.DAILY_DATE_FOUR, date);
                    ViewPageData.put(Constants.DAILY_VALUE_FOUR, currentclose);
                } else if (count == 4) {
                    ViewPageData.put(Constants.DAILY_DATE_FIVE, date);
                    ViewPageData.put(Constants.DAILY_VALUE_FIVE, currentclose);
                }
                count++;
            }
            /**
             * retrievs monthly json data
             */
            JSONObject seriesMonthly = Monthly.getJSONObject("Monthly Time Series");
            count = 0;
            /**
             * stores each entry into an array list 
             */
            ArrayList<String> sortedDatesMonthly = new ArrayList<>(seriesMonthly.keySet());
            Collections.sort(sortedDatesMonthly, Collections.reverseOrder());
            for (String date : sortedDatesMonthly) {
                /**
                 * stores the current close
                 */
                String currentclose = seriesMonthly
                        .getJSONObject(date)
                        .getString("4. close");
                /**
                 * formates the current close
                 */
                String.format("%.2f", Double.parseDouble(currentclose));
                if (count >= 5) {
                    break;
                }
                if (count == 0) {
                    ViewPageData.put(Constants.MONTHLY_DATE_ONE, date);
                    ViewPageData.put(Constants.MONTHLY_VALUE_ONE, currentclose);
                } else if (count == 1) {
                    ViewPageData.put(Constants.MONTHLY_DATE_TWO, date);
                    ViewPageData.put(Constants.MONTHLY_VALUE_TWO, currentclose);
                } else if (count == 2) {
                    ViewPageData.put(Constants.MONTHLY_DATE_THREE, date);
                    ViewPageData.put(Constants.MONTHLY_VALUE_THREE, currentclose);
                } else if (count == 3) {
                    ViewPageData.put(Constants.MONTHLY_DATE_FOUR, date);
                    ViewPageData.put(Constants.MONTHLY_VALUE_FOUR, currentclose);
                } else if (count == 4) {
                    ViewPageData.put(Constants.MONTHLY_DATE_FIVE, date);
                    ViewPageData.put(Constants.MONTHLY_VALUE_FIVE, currentclose);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ViewPageData = getStockViewData(symbol);
        }

         return ViewPageData;
    }
    
    /**
     * for testing
     */
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