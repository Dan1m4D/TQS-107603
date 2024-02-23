package stock;
import java.util.List;
import java.util.ArrayList;

/**
 * StocksPortfolio
 */
public class StocksPortfolio{
    IStockmarketService stockMarket;
    List<Stock> stocks = new ArrayList<>();
    
    public StocksPortfolio(IStockmarketService stockMarket) {
        this.stockMarket = stockMarket;
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double value = 0.0;
        for (Stock stock : stocks) {
            value += stockMarket.lookUpPrice(stock.getLabel()) * stock.getQuantity();
        }
        return value;
    }
}