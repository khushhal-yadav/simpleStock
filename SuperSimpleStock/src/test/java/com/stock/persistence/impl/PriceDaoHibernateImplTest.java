package com.stock.persistence.impl;

import com.stock.domain.Price;
import com.stock.domain.SystemDate;
import com.stock.exception.PriceNotPresentExcption;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertThat;

/**
 * Created by khush on 06/11/2016.
 */
public class PriceDaoHibernateImplTest {

    private EntityManager entityManager;

    private PriceDaoHibernateImpl dao;

    private String symbol = "TEA";
    private String exchange = "GBCE";
    private String currencyCode = "GBP";
    private LocalDate calculationDate = LocalDate.now();
    private SystemDate systemDate = new SystemDate(calculationDate, true, LocalDateTime.now());
    private Price price;

    @Before
    public void setUp() {
        entityManager = Mockito.mock(EntityManager.class);
        dao = new PriceDaoHibernateImpl();
        systemDate = new SystemDate(calculationDate, true, LocalDateTime.now());
        price = new Price(symbol, exchange, BigDecimal.ONE, currencyCode, systemDate);
        dao.setEntityManager(entityManager);
    }

    @Test
    public void testFindBySymbolExch() throws Exception {
        TypedQuery<Price> priceQuery = queryDao();

        Mockito.when(priceQuery.getSingleResult()).thenReturn(price);

        Price returnedPrice = dao.findBySymbolExch(symbol, exchange, systemDate);
        Mockito.verify(priceQuery).getSingleResult();
        assertThat(returnedPrice.getMarketPrice(), CoreMatchers.is(Matchers.equalTo(BigDecimal.ONE)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySymbolExchWithNullSymbol() throws Exception {
        dao.findBySymbolExch(null, exchange, systemDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySymbolExchWithNullExchange() throws Exception {
        dao.findBySymbolExch(symbol, null, systemDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySymbolExchWithNullSystemDate() throws Exception {
        dao.findBySymbolExch(symbol, exchange, null);
    }

    private TypedQuery<Price> queryDao() {
        TypedQuery<Price> priceTypedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(Price.FIND_BY_SYMBOL_EXCH, Price.class)).thenReturn(priceTypedQuery);
        Mockito.when(priceTypedQuery.setParameter("symbol", symbol)).thenReturn(priceTypedQuery);
        Mockito.when(priceTypedQuery.setParameter("exchange", exchange)).thenReturn(priceTypedQuery);
        Mockito.when(priceTypedQuery.setParameter("systemDate", systemDate)).thenReturn(priceTypedQuery);

        return priceTypedQuery;

    }

    @Test(expected = PriceNotPresentExcption.class)
    public void testFindBySymbolExchForException() throws Exception {
        TypedQuery<Price> priceQuery = queryDao();

        Mockito.when(priceQuery.getSingleResult()).thenThrow(new NoResultException());

        Price returnedPrice = dao.findBySymbolExch(symbol, exchange, systemDate);
        Mockito.verify(priceQuery).getSingleResult();
        assertThat(returnedPrice.getMarketPrice(), CoreMatchers.is(Matchers.equalTo(BigDecimal.ONE)));
    }
}