package com.stock.component;

import com.stock.service.TickerPriceService;
import com.stock.domain.Stock;
import com.stock.util.BigDecimalConverter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public abstract class AbstractStockPECalculator extends AbstractStockCalculator implements StockPECalculator {

    protected final TickerPriceService tickerPriceService;

    protected AbstractStockPECalculator(TickerPriceService tickerPriceService) {
        super(tickerPriceService);
        this.tickerPriceService = tickerPriceService;
    }

    @Override
    public BigDecimal calculatePE(Stock stock, LocalDate calculationDate) {
        Assert.isTrue(stock != null, "Stock can't be null");
        Assert.isTrue(calculationDate != null, "Calculation Date can't be null");
        return calculateStockPERatio(stock, calculationDate);
    }

    public BigDecimal calculateStockPERatio(Stock stock, LocalDate calculationDate) {
        BigDecimal tickerPrice = getTickerPrice(stock, calculationDate);
        BigDecimal dividend = getStockDividend(stock);
        Assert.isTrue(!(BigDecimal.ZERO.compareTo(dividend) == 0), "Dividend not present for the calculation date and stock");

        return BigDecimalConverter.convert(tickerPrice.divide(dividend));
    }

}
