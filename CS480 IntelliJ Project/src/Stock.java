public class Stock {

	public Stock(String stocksymbol) {
		Stocksymbol = stocksymbol;
		updateData();
	}

	public String Stocksymbol;
	public String Initialstockprice;
	public String Deltafrompreviousclose;
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

	public String getStocksymbol() {
		return Stocksymbol;
	}

	public String toString() {
		return Stocksymbol;
	}

	public void updateData() {

	}
}
