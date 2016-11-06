package com.stock.component;

import com.stock.service.TickerPriceService;
import com.stock.domain.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by khush on 06/11/2016.
 */
public abstract class AbstractStockCalculator implements StockCalculator {

    protected final TickerPriceService tickerPriceService;

    protected AbstractStockCalculator(TickerPriceService tickerPriceService) {
        this.tickerPriceService = tickerPriceService;
    }

    @Override
    public BigDecimal getTickerPrice(Stock stock, LocalDate calculationDate) {
        return Optional.ofNullable(tickerPriceService.getTickerPrice(stock, calculationDate)).orElse(BigDecimal.ZERO);
    }
}
