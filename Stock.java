package test;

/**
 * @author StockFullyOptomistic
 * CS 480
 */

public class Stock {
	/**
	 * stock name being stored
	 */
	public String StockName;
	/**
	 * stock symbol being stored
	 */
	public String Stocksymbol;
	/**
	 * stores the current stock price
	 */
	public String Currentstockprice;
	/**
	 * stores change from previous close
	 */
	public String Changefrompreviousclose;
	/**
	 * stors the close percentage
	 */
	public String ChangefrompreviousclosePrecentage;
	/**
	 * stores the previous
	 */
	public String Previous_Close;
	/**
	 * stores the open
	 */
	public String Open;
	/**
	 * stores the bid
	 */
	public String Bid;
	/**
	 * stores the ask
	 */
	public String Ask;
	/**
	 * stores the days range
	 */
	public String DaysRange;
	/**
	 * stores the fifty two week range
	 */
	public String FiftyTwo_WeekRange;
	/**
	 * stores the volume
	 */
	public String Volume;
	/**
	 * stores the average volume
	 */
	public String AvgVolume;
	/**
	 * stores the marketcap intraday data
	 */
	public String MarketCap_intraday;
	/**
	 * stores the beta data
	 */
	public String Beta_5Y_Monthly;
	/**
	 * stores the PERatio
	 */
	public String PERatio_TTM;
	/**
	 * stores the EPS_TTM
	 */
	public String EPS_TTM;
	/**
	 * stores the Earning Date
	 */
	public String Earnings_Date;
	/**
	 * stores the Forward div and yield
	 */
	public String Forward_Dividend_and_Yield;
	/**
	 * stores the ex dividend date
	 */
	public String Ex_Dividend_Date;
	/**
	 * stores the 1y target est
	 */
	public String y_Target_Est;
	/**
	 * stores the previous
	 */
	public String Stock_chart;
	/**
	 * stores intraday dates for the chart
	 */
	public String[] intraday_dates = new String[5];
	/**
	 * stores intraday values for the chart
	 */
	public String[] intraday_values = new String[5];
	/**
	 * stores the daily dates 
	 */
	public String[] daily_dates = new String[5];
	/**
	 * stores the daily values
	 */
	public String[] daily_values = new String[5];
	/**
	 * stores the monthly dates 
	 */
	public String[] monthly_dates = new String[5];
	/**
	 * stores the monthly values
	 */
	public String[] monthly_values = new String[5];

	
	public Stock() {

	}

	/**
	 * Updates the current stock symbol
	 * @param stocksymbol the existing stock symbol
	 */
	public Stock(String stocksymbol) {
		Stocksymbol = stocksymbol;
		updateData();
	}

	/**
	 * Retrieves the stock symbol for this current object
	 * @return returns the stock symbol
	 */
	public String getStocksymbol() {
		return Stocksymbol;
	}

	/**
	 * sets the stock symbol to the new stock symbol
	 * @param stocksymbol takes in a current stock 
	 */
	public void setStocksymbol(String stocksymbol) {
		Stocksymbol = stocksymbol;
	}

	/**
	 * returns the stock symbol when toString is called
	 */
	public String toString() {
		return Stocksymbol;
	}

	public void updateData() {

	}

}
