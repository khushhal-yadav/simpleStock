package com.stock.persistence.impl;

import com.stock.domain.Stock;
import com.stock.domain.StockType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.jpa.HibernateEntityManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

/**
 * Created by khush on 06/11/2016.
 */
public class AbstractHibernateDaoTest {

    private EntityManager entityManager;

    private final AbstractHibernateDao<Stock> dao = new AbstractHibernateDao<Stock>(){};

    @Before
    public void setMockEntityManager() {
        entityManager = Mockito.mock(HibernateEntityManager.class, Mockito.RETURNS_DEEP_STUBS);
        dao.setEntityManager(entityManager);
        assertThat(dao.getEntityManager(), CoreMatchers.is(Matchers.sameInstance(entityManager)));
    }

    @Test
    public void testGetEntityClass() {
        Class<Stock> entityClass = dao.getEntityClass();
        assertThat(entityClass, CoreMatchers.is(Matchers.sameInstance(Stock.class)));
    }

    @Test
    public void testFind() {
        final int id = 1;
        dao.find(id);
        Mockito.verify(entityManager).find(Stock.class, id);
    }

    @Test
    public void testSingleEntityPersistence() {
        Stock stock = new Stock("TEA", "GBCE", StockType.COMMON, new BigDecimal("0.00"), 0 , new BigDecimal("1.00"), "GBP");
        dao.persist(stock);
        Mockito.verify(entityManager).persist(stock);
        Mockito.verify(entityManager).flush();
    }

    @Test
    public void testBatchEntityPersistence() {
        Stock tea = new Stock("TEA", "GBCE", StockType.COMMON, new BigDecimal("0.00"), 0 , new BigDecimal("1.00"), "GBP");
        Stock ale = new Stock("ALE", "GBCE", StockType.COMMON, new BigDecimal("0.23"), 0 , new BigDecimal("0.60"), "GBP");
        List<Stock> stocks = Arrays.asList(tea, ale);
        dao.persist(stocks);
        stocks.forEach(stock -> Mockito.verify(entityManager).persist(stock));        ;
        Mockito.verify(entityManager).flush();
    }






}