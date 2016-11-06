package com.stock.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by khush on 06/11/2016.
 */
public interface TradeService {

    BigDecimal getStockPriceBasedOnTradeBookedAfterRecordedDateTime(LocalDateTime dateTime);
}
