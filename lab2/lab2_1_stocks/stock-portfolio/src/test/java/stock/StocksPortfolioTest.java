package stock;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
/**
 * StocksPortfolioTest
 */
@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    //1. Prepare a mock to substitute the remote service (@Mock annotation)
    @Mock
    private IStockmarketService stockMarket;
    
    //2. Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
    @InjectMocks
    private StocksPortfolio portfolio;

    @DisplayName("Test total value -> With JUnit 5 and Mockito")
    @Test
    void getTotalValue() {
        Stock stock1 = new Stock("AAPL", 2);
        Stock stock2 = new Stock("GOOGL", 3);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(160.0);
        when(stockMarket.lookUpPrice("GOOGL")).thenReturn(1200.0);
        // 5. Verify the result (assert) and the use of the mock (verify)
        assertEquals(2*160.0 + 3*1200.0, portfolio.totalValue());
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
        verify(stockMarket, times(1)).lookUpPrice("GOOGL");
    }

    @DisplayName("Test total value -> With HamCrest and Mockito")
    @Test
    void getTotalValueWithHamCrest() {
        Stock stock1 = new Stock("AAPL", 2);
        Stock stock2 = new Stock("GOOGL", 3);
        portfolio.addStock(stock1);
        portfolio.addStock(stock2);
        // 3. Load the mock with the proper expectations (when...thenReturn)
        when(stockMarket.lookUpPrice("AAPL")).thenReturn(160.0);
        when(stockMarket.lookUpPrice("GOOGL")).thenReturn(1200.0);
        // 5. Verify the result (assert) and the use of the mock (verify)
        assertThat(portfolio.totalValue(), is(2*160.0 + 3*1200.0));
        verify(stockMarket, times(1)).lookUpPrice("AAPL");
        verify(stockMarket, times(1)).lookUpPrice("GOOGL");
    }

}