package com.stock.service;

import com.stock.domain.SystemDate;

import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface SystemDateService {
    SystemDate getCurrentSystemDate();
    void setCurrentSystemDate(LocalDate systemDate);
}
