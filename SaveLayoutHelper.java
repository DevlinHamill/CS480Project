package test;

import java.io.*;

/**
 * @author StockFullyOptomistic
 * CS 480
 */

public class SaveLayoutHelper {
	/**
	 * the current file
	 */
    private File file;
    /**
     * stores each button
     */
    private String[] buttonSymbols = new String[10];
    
    /**
     * runs the test
     * @param args main arguments
     */
    public static void main(String[] args) {
        new SaveLayoutHelper().runTest();
    }
    
    /**
     * helps with testing
     */
    private void runTest() {
        SaveLayoutHelper saveLayoutHelper = new SaveLayoutHelper();
        saveLayoutHelper.addStockAtPosition(new Stock("IBM"), 0);
        saveLayoutHelper.addStockAtPosition(new Stock("THX"), 1);
        saveLayoutHelper.addStockAtPosition(new Stock("ABC"), 2);
        saveLayoutHelper.addStockAtPosition(new Stock("XYZ"), 5);
        saveLayoutHelper.addStockAtPosition(new Stock("IBC"), 7);
        saveLayoutHelper.addStockAtPosition(new Stock("TXT"), 9);

        for(int i = 0; i < 10; i++) {
            System.out.println("Position " + i + ": " + saveLayoutHelper.getStockAtPosition(i).getStocksymbol());
        }
    }
    /**
     * creates a new textfile file path
     */
    public SaveLayoutHelper() {
        this(Constants.DEFAULT_FILE_PATH);
    }

    /**
     * constructor that takes in a new file path
     * @param filePath text file being saved
     */
    public SaveLayoutHelper(String filePath) {
        file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error creating a new layout file to store positions: " + e.getMessage());
            }
        }

        readStockPositions();
    }

    /**
     * adds a stock symbol on a specific position on the text file
     * @param stock stock symbol being added to txt file
     * @param position position the stock symbols add
     */
    public void addStockAtPosition(Stock stock, int position) {
        if(HomeScreen.debug) System.out.println("Adding " + stock + " to layout file");
        buttonSymbols[position] = stock.getStocksymbol();
        readStockPositions();
        writeStockPositions();
    }
    
    /**
     * returns the current stock from an input position
     * @param position array location being checked
     * @return the stock at a position
     */
    public Stock getStockAtPosition(int position) {
        Stock returnStock = null;
        if(buttonSymbols[position] != null) {
            returnStock = new Stock(buttonSymbols[position]);
        }
        else {
            returnStock = new Stock();
        }

        return returnStock;
    }
    
    /**
     * removes the stock from an array location
     * @param position array location that is being saved with the textfile
     */
    public void removeStockAtPosition(int position) {
        if(HomeScreen.debug) System.out.println("Removing " + buttonSymbols[position] + " from layout file");
        buttonSymbols[position] = null;
        writeStockPositions();
    }
    /**
     * reads the stock positions on the file
     */
    private void readStockPositions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length == 2) { // Validate data format
                    int position = Integer.parseInt(tokens[0]);
                    buttonSymbols[position] = tokens[1];
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error finding layout file: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("Error reading stock positions from layout file: " + e.getMessage());
        }
        catch (NumberFormatException e) {
            System.out.println("Error parsing position number in layout file: " + e.getMessage());
        }
    }
    /**
     * writes new stock positions onto the file
     */
    private void writeStockPositions() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            for (int i = 0; i < buttonSymbols.length; i++) {
                if (buttonSymbols[i] != null && !buttonSymbols[i].isEmpty()) {
                    fileWriter.write(i + " " + buttonSymbols[i] + "\n");
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error writing stock positions to layout file: " + e.getMessage());
        }
    }
}
