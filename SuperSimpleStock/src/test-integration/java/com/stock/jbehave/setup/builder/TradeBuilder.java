package com.stock.jbehave.setup.builder;

import com.hazelcast.transaction.impl.TransactionDataSerializerHook;
import com.stock.domain.BuySell;
import com.stock.domain.Trade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class TradeBuilder implements EntityBuilder<Trade> {

    private int quantity;
    private BuySell buySell;
    private BigDecimal tradePrice;
    private LocalDate tradeDate;
    private LocalDate settlementDate;
    private LocalDateTime recordedTime;


    @Override
    public EntityBuilder<Trade> newInstance() {
        return new TradeBuilder();
    }

    @Override
    public Trade build() {
        return new Trade(quantity, buySell, tradePrice, tradeDate, settlementDate, LocalDateTime.now());
    }
}
