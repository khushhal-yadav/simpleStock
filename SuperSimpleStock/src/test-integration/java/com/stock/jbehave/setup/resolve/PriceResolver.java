package com.stock.jbehave.setup.resolve;

import com.stock.common.AppStory;
import com.stock.domain.Price;
import com.stock.persistence.PriceDao;
import com.stock.persistence.SystemDateDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by khush on 10/09/2016.
 */
public class PriceResolver<T> implements EntityResolver<Price> {

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private SystemDateDao systemDateDao;

    @Override
    public Price resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext) {
        String param[] = params.split(",", 0);
        return priceDao.findBySymbolExch(param[0], param[1], systemDateDao.findByCalculationDate(LocalDate.parse(param[2], AppStory.DATE_TIME_FORMATTER)));
    }

    @Override
    public boolean validateFor(Class<?> type) {
        return type.equals(Price.class);
    }
}
