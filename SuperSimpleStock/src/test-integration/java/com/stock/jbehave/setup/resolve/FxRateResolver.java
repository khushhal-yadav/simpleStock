package com.stock.jbehave.setup.resolve;

import com.stock.common.AppStory;
import com.stock.domain.FxRate;
import com.stock.persistence.FxRateDao;
import com.stock.persistence.SystemDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by khush on 10/09/2016.
 */
@Component
public class FxRateResolver implements EntityResolver<FxRate> {

    @Autowired
    private FxRateDao fxRateDao;

    @Autowired
    private SystemDateDao systemDateDao;

    @Override
    public FxRate resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext) {
        String param[] = params.split(",", 0);
        return fxRateDao.findByCurrencyFromTo(param[0], param[1], systemDateDao.findByCalculationDate(LocalDate.parse(param[2], AppStory.DATE_TIME_FORMATTER)));
    }

    @Override
    public boolean validateFor(Class<?> type) {
        return type.equals(FxRate.class);
    }
}
