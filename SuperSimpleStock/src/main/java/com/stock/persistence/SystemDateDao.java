package com.stock.persistence;

import com.stock.domain.SystemDate;

import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface SystemDateDao extends Dao<SystemDate> {

    SystemDate findCurrent();

    SystemDate findByCalculationDate(LocalDate calculationDate);
}
