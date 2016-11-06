package com.stock.persistence;

import com.stock.domain.Trade;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
public interface TradeDao extends Dao<Trade> {
    List<Trade> getTradesBookedAfterRecordedDateTime(LocalDateTime recordedDateTime);
}
