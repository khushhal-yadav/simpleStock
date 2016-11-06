package com.stock.persistence.impl;

import com.stock.domain.Price;
import com.stock.domain.Stock;
import com.stock.domain.SystemDate;
import com.stock.exception.StockNotPresentException;
import com.stock.persistence.StockDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by khush on 10/09/2016.
 */
@Repository
public class StockDaoHibernateImpl extends AbstractHibernateDao<Stock> implements StockDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Cacheable(value = "priceCache", key = "new org.apache.commons.collections.keyvalue.Multikey(#symbol, #exchange, #systemDate)", unless = "#result == null")
    public Stock findBySymbolExch(String symbol, String exchange) {
        Optional.ofNullable(symbol).orElseThrow(() -> new IllegalArgumentException(String.format("Symbol can't be null %s", symbol)));
        Optional.ofNullable(exchange).orElseThrow(() -> new IllegalArgumentException(String.format("Exchange can't be null %s", exchange)));
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(Stock.FIND_BY_SYMBOL_EXCH,
                    Stock.class).setParameter("symbol", symbol).setParameter("exchange", exchange)
                    .getSingleResult());
        } catch (NoResultException nre) {
            throw new StockNotPresentException(String.format("No Stock found for symbol %s exchange %s", symbol, exchange), nre);
        }
    }

    @Override
    public List<Stock> findAllByExch(String exchange) {
        Optional.ofNullable(exchange).orElseThrow(() -> new IllegalArgumentException(String.format("Exchange can't be null %s", exchange)));
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(Stock.FIND_ALL_BY_EXCH,
                    Stock.class).setParameter("exchange", exchange).getResultList());
        } catch (NoResultException nre) {
            throw new StockNotPresentException(String.format("No Stock found for exchange %s", exchange), nre);
        }
    }
}
