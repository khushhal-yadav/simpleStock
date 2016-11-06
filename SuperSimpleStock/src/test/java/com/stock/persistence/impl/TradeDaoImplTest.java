package com.stock.persistence.impl;

import com.stock.domain.BuySell;
import com.stock.domain.Trade;
import com.stock.exception.TradeNotPresentException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
public class TradeDaoImplTest {

    private EntityManager entityManager;

    private TradeDaoHibernateImpl dao;

    private Trade trade1, trade2, trade3;
    private LocalDateTime recordDateTime = LocalDateTime.now();

    @Before
    public void setUp() {
        entityManager = Mockito.mock(EntityManager.class);
        dao = new TradeDaoHibernateImpl();
        dao.setEntityManager(entityManager);
        trade1 = new Trade(100, BuySell.BUY, new BigDecimal(0.25), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
        trade2 = new Trade(200, BuySell.BUY, new BigDecimal(0.15), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
        trade3 = new Trade(400, BuySell.SELL, new BigDecimal(0.10), LocalDate.of(2016, 9, 11), LocalDate.of(2016, 9, 11), LocalDateTime.now());
    }

    @Test
    public void testGetTradesBookedAfterRecordedDateTime() throws Exception {
        TypedQuery<Trade> stockQuery = queryDao();
        Mockito.when(stockQuery.getResultList()).thenReturn(Arrays.asList(trade1, trade2, trade3));

        final List<Trade> tradesBookedAfterRecordedDateTime = dao.getTradesBookedAfterRecordedDateTime(recordDateTime);
        Assert.assertEquals(3, tradesBookedAfterRecordedDateTime.size());

    }

    private TypedQuery<Trade> queryDao() {
        TypedQuery<Trade> tradeTypedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(Trade.FIND_TRADES_BOOKED_AFTER_RECORD_DATE_TIME, Trade.class)).thenReturn(tradeTypedQuery);
        Mockito.when(tradeTypedQuery.setParameter("recordedTime", recordDateTime)).thenReturn(tradeTypedQuery);
        return tradeTypedQuery;

    }

    @Test(expected = TradeNotPresentException.class)
    public void testGetTradesBookedAfterRecordedDateTimeForException() throws Exception {
        TypedQuery<Trade> stockQuery = queryDao();
        Mockito.when(stockQuery.getResultList()).thenThrow(new NoResultException());

        dao.getTradesBookedAfterRecordedDateTime(recordDateTime);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTradesBookedAfterRecordedDateTimeWithNullRecordDate() throws Exception {
        dao.getTradesBookedAfterRecordedDateTime(null);
    }

}