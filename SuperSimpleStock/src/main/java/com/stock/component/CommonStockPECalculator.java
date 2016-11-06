
package com.stock.component;

        import com.stock.service.TickerPriceService;
        import com.stock.domain.Stock;
        import com.stock.domain.StockType;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Component;

        import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class CommonStockPECalculator extends AbstractStockPECalculator {

    @Autowired
    public CommonStockPECalculator(TickerPriceService tickerPriceService) {
        super(tickerPriceService);
    }


    @Override
    public BigDecimal getStockDividend(Stock stock) {
        return stock.getLastDividend();
    }

    @Override
    public StockType getStockType() {
        return StockType.COMMON;
    }
}