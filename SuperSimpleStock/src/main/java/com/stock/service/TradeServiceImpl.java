package com.stock.service;

import com.stock.domain.Trade;
import com.stock.persistence.TradeDao;
import com.stock.util.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;

    @Autowired
    public TradeServiceImpl(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    @Override
    public BigDecimal getStockPriceBasedOnTradeBookedAfterRecordedDateTime(LocalDateTime recordedDateTime) {
        Assert.isTrue(recordedDateTime != null, "Record date time can't be null");
        List<Trade> tradesBookedAfterRecordedDateTime = tradeDao.getTradesBookedAfterRecordedDateTime(recordedDateTime);

        BigDecimal totalPrice = tradesBookedAfterRecordedDateTime.stream().map(trade -> new BigDecimal(trade.getQuantity()).multiply(trade.getTradePrice())).reduce(BigDecimal.ONE, BigDecimal::add);
        int totalQuantity = tradesBookedAfterRecordedDateTime.stream().mapToInt(Trade::getQuantity).sum();

        BigDecimal stockPrice = totalPrice.divide(new BigDecimal(totalQuantity), 6, BigDecimal.ROUND_HALF_UP);

        return stockPrice;
    }
}
