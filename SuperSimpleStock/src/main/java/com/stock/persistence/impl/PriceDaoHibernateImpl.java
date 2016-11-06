package com.stock.persistence.impl;

import com.stock.domain.FxRate;
import com.stock.domain.Price;
import com.stock.domain.SystemDate;
import com.stock.exception.PriceNotPresentExcption;
import com.stock.persistence.PriceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by khush on 10/09/2016.
 */
@Component
public class PriceDaoHibernateImpl extends AbstractHibernateDao<Price> implements PriceDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Cacheable(value = "priceCache", key = "new org.apache.commons.collections.keyvalue.Multikey(#symbol, #exchange, #systemDate)", unless = "#result == null")
    public Price findBySymbolExch(String symbol, String exchange, SystemDate systemDate) {
        Optional.ofNullable(symbol).orElseThrow(() -> new IllegalArgumentException(String.format("Symbol can't be null %s", symbol)));
        Optional.ofNullable(exchange).orElseThrow(() ->new IllegalArgumentException(String.format("Exchange can't be null %s", exchange)));
        Optional.ofNullable(systemDate).orElseThrow(() -> new IllegalArgumentException(String.format("SystemDate can't be null %s", systemDate)));
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(Price.FIND_BY_SYMBOL_EXCH,
                    Price.class).setParameter("symbol", symbol).setParameter("exchange", exchange)
                    .setParameter("systemDate", systemDate).getSingleResult());
        } catch (NoResultException nre) {
            throw new PriceNotPresentExcption(String.format("No Price found for symbol %s exchange %s and for system date %s", symbol, exchange, systemDate), nre);
        }
    }
}
