package com.stock.persistence.impl;

import com.stock.domain.Price;
import com.stock.domain.Trade;
import com.stock.exception.TradeNotPresentException;
import com.stock.persistence.TradeDao;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by khush on 12/09/2016.
 */
@Repository
public class TradeDaoHibernateImpl extends AbstractHibernateDao<Trade> implements TradeDao {
    @Override
    public List<Trade> getTradesBookedAfterRecordedDateTime(LocalDateTime recordedDateTime) {
        Assert.isTrue(recordedDateTime != null, "RecordedDateTime date can't be null");
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(Trade.FIND_TRADES_BOOKED_AFTER_RECORD_DATE_TIME,
                    Trade.class).setParameter("recordedTime", recordedDateTime).getResultList());
        } catch (NoResultException nre) {
            throw new TradeNotPresentException(String.format("No Trades found with recorded trade greater tahtn %s", recordedDateTime), nre);
        }

    }
}
