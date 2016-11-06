package com.stock.service;

import com.stock.domain.SystemDate;
import com.stock.persistence.SystemDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by khush on 06/11/2016.
 */
@Repository
public class SystemDateServiceImpl implements SystemDateService {

    @Autowired
    private SystemDateDao systemDateDao;

    @Override
    public SystemDate getCurrentSystemDate() {
        return systemDateDao.findCurrent();
    }

    @Override
    public void setCurrentSystemDate(LocalDate systemDate) {
        SystemDate currentSystemDate = new SystemDate();
        currentSystemDate.setCalculationDate(systemDate);
        currentSystemDate.setCurrent(true);
        currentSystemDate.setLastUpdated(LocalDateTime.now());
        systemDateDao.persist(currentSystemDate);

    }
}
