package com.stock.component;

import com.stock.domain.Stock;
import com.stock.domain.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface StockCalculator {
    StockType getStockType();
    BigDecimal getStockDividend(Stock stock);
    BigDecimal getTickerPrice(Stock stock, LocalDate calculationDate);

}
