package com.stock.persistence.impl;

import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.exception.StockNotPresentException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by khush on 06/11/2016.
 */
public class StockDaoHibernateImplTest {

    private EntityManager entityManager;

    private StockDaoHibernateImpl dao;

    private String symbol = "TEA";
    private String exchange = "GBCE";
    private String currencyCode = "GBP";
    private Stock teaStock, ginStock;

    @Before
    public void setUp() {
        entityManager = Mockito.mock(EntityManager.class);
        dao = new StockDaoHibernateImpl();
        teaStock = new Stock(symbol, exchange, StockType.COMMON, BigDecimal.ZERO, 0, BigDecimal.ONE, currencyCode);
        ginStock = new Stock("GIN", exchange, StockType.PREFERRED, BigDecimal.ONE, 2, BigDecimal.ONE, currencyCode);
        dao.setEntityManager(entityManager);
    }

    @Test
    public void testFindBySymbolExch() throws Exception {
        TypedQuery<Stock> stockQuery = queryDao();
        Mockito.when(stockQuery.getSingleResult()).thenReturn(teaStock);

        Stock returnedStock = dao.findBySymbolExch(symbol, exchange);
        Mockito.verify(stockQuery).getSingleResult();
        assertThat(returnedStock.getStockType(), CoreMatchers.is(Matchers.sameInstance(StockType.COMMON)));
        assertThat(returnedStock.getLastDividend(), CoreMatchers.is(Matchers.equalTo(BigDecimal.ZERO)));
        assertThat(returnedStock.getParValue(), CoreMatchers.is(Matchers.equalTo(BigDecimal.ONE)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySymbolExchWithNullSymbol() throws Exception {
        dao.findBySymbolExch(null, exchange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySymbolExchWithNullExchange() throws Exception {
        dao.findBySymbolExch(symbol, null);
    }

    private TypedQuery<Stock> queryDao() {
        TypedQuery<Stock> stockTypedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(Stock.FIND_BY_SYMBOL_EXCH, Stock.class)).thenReturn(stockTypedQuery);
        Mockito.when(stockTypedQuery.setParameter("symbol", symbol)).thenReturn(stockTypedQuery);
        Mockito.when(stockTypedQuery.setParameter("exchange", exchange)).thenReturn(stockTypedQuery);
        return stockTypedQuery;

    }

    @Test(expected = StockNotPresentException.class)
    public void testFindBySymbolExchForException() throws Exception {
        TypedQuery<Stock> stockQuery = queryDao();
        Mockito.when(stockQuery.getSingleResult()).thenThrow(new NoResultException());

        dao.findBySymbolExch(symbol, exchange);
    }

    @Test
    public void testAllByExch() throws Exception {
        TypedQuery<Stock> stockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(Stock.FIND_ALL_BY_EXCH, Stock.class)).thenReturn(stockQuery);
        Mockito.when(stockQuery.setParameter("exchange", exchange)).thenReturn(stockQuery);
        Mockito.when(stockQuery.getResultList()).thenReturn(Arrays.asList(teaStock, ginStock));

        List<Stock> returnedStocks = dao.findAllByExch(exchange);
        Mockito.verify(stockQuery).getResultList();
        assertEquals(2, returnedStocks.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllByExchwithNullExch() throws Exception {
        dao.findAllByExch(null);
    }

    @Test(expected = StockNotPresentException.class)
    public void testAllByExchForException() throws Exception {
        TypedQuery<Stock> stockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(Stock.FIND_ALL_BY_EXCH, Stock.class)).thenReturn(stockQuery);
        Mockito.when(stockQuery.setParameter("exchange", exchange)).thenReturn(stockQuery);
        Mockito.when(stockQuery.getResultList()).thenThrow(new NoResultException());

        dao.findAllByExch(exchange);
    }

}