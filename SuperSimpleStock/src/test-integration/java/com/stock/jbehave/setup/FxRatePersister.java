package com.stock.jbehave.setup;

import com.stock.domain.FxRate;
import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.builder.FxRateBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.jbehave.setup.resolve.SystemDateResolver;
import com.stock.persistence.Dao;
import com.stock.persistence.FxRateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class FxRatePersister extends AbstractEntityPersister<FxRate> {

    @Autowired
    private FxRateDao fxRateDao;

    @Autowired
    private FxRateBuilder fxRateBuilder;

    @Autowired
    private SystemDateResolver systemDateResolver;

    @Override
    protected Dao<FxRate> getDao() {
        return fxRateDao;
    }

    @Override
    protected EntityBuilder<FxRate> getEntityBuilder() {
        return fxRateBuilder;
    }

    @Override
    protected List<EntityResolver<?>> getEntityResolvers() {
        return Arrays.asList(systemDateResolver);
    }
}
