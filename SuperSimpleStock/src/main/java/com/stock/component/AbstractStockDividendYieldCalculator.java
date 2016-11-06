package com.stock.component;

import com.stock.domain.Stock;
import com.stock.service.TickerPriceService;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public abstract class AbstractStockDividendYieldCalculator extends AbstractStockCalculator implements StockDividendYieldCalculator {

    protected final TickerPriceService tickerPriceService;

    protected AbstractStockDividendYieldCalculator(TickerPriceService tickerPriceService) {
        super(tickerPriceService);
        this.tickerPriceService = tickerPriceService;
    }

    public BigDecimal calculateDividendYield(final Stock stock, final LocalDate calculationDate) {
        Assert.isTrue(stock != null, "Stock can't be null");
        Assert.isTrue(calculationDate != null, "Calculation Date can't be null");
        return calculateStockDividendYield(stock, calculationDate);
    }

    public BigDecimal calculateStockDividendYield(final Stock stock, final LocalDate calculationDate) {
        BigDecimal lastDividend = getStockDividend(stock);
        BigDecimal tickerPriceInStockCurrency = getTickerPrice(stock, calculationDate);
        Assert.isTrue(!((BigDecimal.ZERO).compareTo(tickerPriceInStockCurrency) == 0), "Ticker Price in Stock Currency not Present");

        BigDecimal dividendYield = lastDividend.divide(tickerPriceInStockCurrency, 6, BigDecimal.ROUND_HALF_UP);

        return dividendYield;
    }

}
