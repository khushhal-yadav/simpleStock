package com.stock.persistence.impl;

import com.stock.domain.SystemDate;
import com.stock.exception.SystemDateNotPresentException;
import com.stock.persistence.SystemDateDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by khush on 10/09/2016.
 */
@Repository
public class SystemDateDaoHibernateImpl extends AbstractHibernateDao<SystemDate> implements SystemDateDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public SystemDate findCurrent() {
        try {
            SystemDate currentSystemDate;
            currentSystemDate = withEntityManager(entityManager -> entityManager.createNamedQuery(SystemDate
                    .FIND_CURRENT_RECORD, SystemDate.class)).getSingleResult();
            return currentSystemDate;
        } catch (NoResultException nre) {
            throw new SystemDateNotPresentException("No current Systemdate found", nre);
        }

    }

    @Override
    @Cacheable(value = "systemDateByCalculationDateCache", unless = "#resut == null")
    public SystemDate findByCalculationDate(LocalDate calculationDate) {
        Assert.isTrue(calculationDate != null, "Calculation date can't be null");
        try {
            return withEntityManager(entityManager -> entityManager.createNamedQuery(SystemDate.FIND_BY_CALCUATION_DATE,
                    SystemDate.class).setParameter("calculationDate", calculationDate).getSingleResult());
        } catch (NoResultException nre) {
            throw new SystemDateNotPresentException(String.format("No System date found for calculation date %tD", calculationDate), nre);
        }
    }
}
