package com.stock.jbehave.setup.resolve;

import com.stock.common.AppStory;
import com.stock.domain.SystemDate;
import com.stock.persistence.SystemDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Map;

/**
 * Created by khush on 10/09/2016.
 */
@Controller
public class SystemDateResolver implements EntityResolver<SystemDate> {

    @Autowired
    private SystemDateDao systemDateDao;

    @Override
    public SystemDate resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext) {
        return "current".equals(params)
                ? systemDateDao.findCurrent()
                : systemDateDao.findByCalculationDate(LocalDate.parse(params, AppStory.DATE_TIME_FORMATTER));
    }

    @Override
    public boolean validateFor(Class<?> type) {
        return type.equals(SystemDate.class);
    }
}
