package Models;

public class Stock {
    private String stockSymbol;

    public Stock(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }
}
