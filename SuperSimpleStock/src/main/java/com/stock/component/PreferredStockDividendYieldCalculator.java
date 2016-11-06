package com.stock.component;

import com.stock.service.TickerPriceService;
import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.util.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */

@Component
public class PreferredStockDividendYieldCalculator extends AbstractStockDividendYieldCalculator {

    @Autowired
    public PreferredStockDividendYieldCalculator(TickerPriceService tickerPriceService) {
        super(tickerPriceService);
    }

    @Override
    public BigDecimal getStockDividend(Stock stock) {
        return BigDecimalConverter.convert(stock.getParValue().multiply(new BigDecimal(stock.getFixedDividend())));
    }

    public StockType getStockType() {
        return StockType.PREFERRED;
    }
}
