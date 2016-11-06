package com.stock.persistence.impl;

import com.stock.domain.FxRate;
import com.stock.domain.SystemDate;
import com.stock.exception.FxRateNotPresentException;
import com.stock.persistence.FxRateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by khush on 10/09/2016.
 */
@Repository
public class FxRateDaoHibernateImpl extends AbstractHibernateDao<FxRate> implements FxRateDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    @Cacheable(value = "fxRateCache", key = "new org.apache.commons.collections.keyvalue.Multikey(#currencyFrom, #currencyTo, #systemDate)", unless = "#result == null")
    public FxRate findByCurrencyFromTo(String currencyFrom, String currencyTo, SystemDate systemDate) {
        Optional.ofNullable(currencyFrom).orElseThrow(() ->new IllegalArgumentException(String.format("CurrencyFrom can't be null %s", currencyFrom)));
        Optional.ofNullable(currencyTo).orElseThrow(() -> new IllegalArgumentException(String.format("CurrencyTo can't be null %s", currencyTo)));
        Optional.ofNullable(systemDate).orElseThrow(() -> new IllegalArgumentException(String.format("SystemDate can't be null %s", systemDate)));
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(FxRate.FIND_BY_CURRENCY_FROM_TO,
                    FxRate.class).setParameter("currencyFrom", currencyFrom).setParameter("currencyTo", currencyTo)
                    .setParameter("systemDate", systemDate).getSingleResult());
        } catch (NoResultException nre) {
            throw new FxRateNotPresentException(String.format("No FxRate found for currency from %s currencyTo %s and for system date %s", currencyFrom, currencyTo, systemDate), nre);
        }
    }
}
