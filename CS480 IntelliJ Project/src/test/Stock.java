package test;

public class Stock {
	
	public String StockName;
	public String Stocksymbol;
	public String Currentstockprice;
	public String Changefrompreviousclose;
	public String ChangefrompreviousclosePrecentage;
	public String Previous_Close;
	public String Open;
	public String Bid;
	public String Ask;
	public String DaysRange;
	public String FiftyTwo_WeekRange;
	public String Volume;
	public String AvgVolume;
	public String MarketCap_intraday;
	public String Beta_5Y_Monthly;
	public String PERatio_TTM;
	public String EPS_TTM;
	public String Earnings_Date;
	public String Forward_Dividend_and_Yield;
	public String Ex_Dividend_Date;
	public String y_Target_Est;
	public String Stock_chart;
	public String[] intraday_dates = new String[5];
	public String[] intraday_values = new String[5];
	public String[] daily_dates = new String[5];
	public String[] daily_values = new String[5];
	public String[] monthly_dates = new String[5];
	public String[] monthly_values = new String[5];

	public Stock() {

	}

	public Stock(String stocksymbol) {
		Stocksymbol = stocksymbol;
		updateData();
	}

	public String getStocksymbol() {
		return Stocksymbol;
	}

	public String toString() {
		return Stocksymbol;
	}

	public void updateData() {

	}

}
