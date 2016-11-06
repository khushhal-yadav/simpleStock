package com.stock.service;

import com.stock.domain.BuySell;
import com.stock.domain.Trade;
import com.stock.exception.TickerPriceInStockCurrencyNotPresentException;
import com.stock.exception.TradeNotPresentException;
import com.stock.persistence.TradeDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by khush on 06/11/2016.
 */
public class TradeServiceImplTest {

    @Mock
    private TradeDao tradeDao;

    private TradeServiceImpl tradeService;
    private List<Trade> trades;
    private LocalDateTime recordedTime = LocalDateTime.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Trade trade1 = new Trade(100, BuySell.BUY, new BigDecimal(0.25), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
        Trade trade2 = new Trade(200, BuySell.BUY, new BigDecimal(0.15), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
        Trade trade3 = new Trade(400, BuySell.SELL, new BigDecimal(0.10), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
        trades = Arrays.asList(trade1, trade2, trade3);
        tradeService = new TradeServiceImpl(tradeDao);
    }

    @Test
    public void testGetStockPriceBasedOnTradeBookedAfterRecordedDateTime() throws Exception {
        Mockito.when(tradeDao.getTradesBookedAfterRecordedDateTime(recordedTime)).thenReturn(trades);
        BigDecimal stockPrice = tradeService.getStockPriceBasedOnTradeBookedAfterRecordedDateTime(recordedTime);
        assertEquals(new BigDecimal("0.137143"), stockPrice);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetStockPriceBasedOnTradeBookedAfterRecordedDateTimeWithNullRecordDateTime() throws Exception {
        tradeService.getStockPriceBasedOnTradeBookedAfterRecordedDateTime(null);

    }

    @Test(expected = TradeNotPresentException.class)
    public void testGetStockPriceBasedOnTradeBookedAfterRecordedDateTimeWithNullNoTradePresent() throws Exception {
        Mockito.when(tradeDao.getTradesBookedAfterRecordedDateTime(recordedTime)).
                thenThrow(new TradeNotPresentException("No trade Present", new NoResultException()));
        tradeService.getStockPriceBasedOnTradeBookedAfterRecordedDateTime(recordedTime);

    }
}