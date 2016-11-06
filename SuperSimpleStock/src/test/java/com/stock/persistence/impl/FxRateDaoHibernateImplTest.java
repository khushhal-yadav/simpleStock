package com.stock.persistence.impl;

import com.stock.domain.FxRate;
import com.stock.domain.SystemDate;
import com.stock.exception.FxRateNotPresentException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.cglib.core.Local;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by khush on 06/11/2016.
 */
public class FxRateDaoHibernateImplTest {

    private EntityManager entityManager;

    private FxRateDaoHibernateImpl dao;

    private String currencyFrom = "GBP";
    private String currencyTo = "GBP";
    private LocalDate calculationDate = LocalDate.now();
    private SystemDate systemDate;
    private FxRate fxRate;

    @Before
    public void setUp() {
        entityManager = Mockito.mock(EntityManager.class);
        dao = new FxRateDaoHibernateImpl();
        systemDate = new SystemDate(calculationDate, true, LocalDateTime.now());
        fxRate = new FxRate(currencyFrom, currencyTo, BigDecimal.ONE, systemDate);
        dao.setEntityManager(entityManager);
    }

    @Test
    public void testfindByCurrencyFromTo() throws Exception {
        TypedQuery<FxRate> fxRateQuery = queryDao();
        Mockito.when(fxRateQuery.getSingleResult()).thenReturn(fxRate);

        FxRate returnedFxRate = dao.findByCurrencyFromTo(currencyFrom, currencyTo, systemDate);
        Mockito.verify(fxRateQuery).getSingleResult();
        assertThat(returnedFxRate.getRate(), CoreMatchers.is(Matchers.equalTo(BigDecimal.ONE)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testfindByCurrencyFromToWithNullCurrencyFrom() throws Exception {
        dao.findByCurrencyFromTo(null, currencyTo, systemDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testfindByCurrencyFromToWithNullCurrencyTo() throws Exception {
        dao.findByCurrencyFromTo(currencyFrom, null, systemDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testfindByCurrencyFromToWithNullSystemDate() throws Exception {
        dao.findByCurrencyFromTo(currencyFrom, currencyTo, null);
    }

    private TypedQuery<FxRate> queryDao() {
        TypedQuery<FxRate> fxRateTypedQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createNamedQuery(FxRate.FIND_BY_CURRENCY_FROM_TO, FxRate.class)).thenReturn(fxRateTypedQuery);
        Mockito.when(fxRateTypedQuery.setParameter("currencyFrom", currencyFrom)).thenReturn(fxRateTypedQuery);
        Mockito.when(fxRateTypedQuery.setParameter("currencyTo", currencyTo)).thenReturn(fxRateTypedQuery);
        Mockito.when(fxRateTypedQuery.setParameter("systemDate", systemDate)).thenReturn(fxRateTypedQuery);

        return fxRateTypedQuery;

    }

    @Test(expected = FxRateNotPresentException.class)
    public void testfindByCurrencyFromToForException() throws Exception {
        TypedQuery<FxRate> fxRateQuery = queryDao();
        Mockito.when(fxRateQuery.getSingleResult()).thenThrow(new NoResultException());

        dao.findByCurrencyFromTo(currencyFrom, currencyTo, systemDate);

    }



}