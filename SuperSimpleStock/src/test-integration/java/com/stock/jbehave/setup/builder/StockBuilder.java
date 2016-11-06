package com.stock.jbehave.setup.builder;

import com.stock.domain.Stock;
import com.stock.domain.StockType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class StockBuilder implements EntityBuilder<Stock> {

    private String symbol;
    private String exchange;
    private StockType stockType;
    private BigDecimal lastDividend;
    private int fixedDividend;
    private BigDecimal parValue;
    private String currency;


    @Override
    public EntityBuilder<Stock> newInstance() {
        return new StockBuilder();
    }

    @Override
    public Stock build() {
        Stock stock = new Stock(symbol, exchange, stockType, lastDividend, fixedDividend, parValue, currency);
        return stock;
    }
}
