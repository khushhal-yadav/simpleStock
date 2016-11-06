package com.stock.jbehave.setup.builder;

import com.stock.domain.FxRate;
import com.stock.domain.SystemDate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class FxRateBuilder implements EntityBuilder<FxRate> {

    private String currencyFrom;
    private String currencyTo;
    private BigDecimal rate;
    private SystemDate systemDate;

    @Override
    public EntityBuilder<FxRate> newInstance() {
        return new FxRateBuilder();
    }

    @Override
    public FxRate build() {
        FxRate fxRate = new FxRate(currencyFrom, currencyTo, rate, systemDate);
        return fxRate;
    }
}
