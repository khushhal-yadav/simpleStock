package com.stock.jbehave.setup.builder;

import com.stock.domain.Price;
import com.stock.domain.SystemDate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class PriceBuilder implements EntityBuilder<Price> {

    private String symbol;
    private String exchange;
    private BigDecimal marketPrice;
    private String currencyCode;
    private SystemDate systemDate;

    @Override
    public EntityBuilder<Price> newInstance() {
        return new PriceBuilder();
    }

    @Override
    public Price build() {
        Price price = new Price(symbol, exchange, marketPrice, currencyCode, systemDate);
        return price;
    }
}
