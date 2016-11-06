package com.stock.jbehave.setup;

import com.stock.domain.Price;
import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.builder.PriceBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.jbehave.setup.resolve.SystemDateResolver;
import com.stock.persistence.Dao;
import com.stock.persistence.PriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class PricePersister extends AbstractEntityPersister<Price> {

    @Autowired
    private PriceDao priceDao;

    @Autowired
    private PriceBuilder priceBuilder;

    @Autowired
    private SystemDateResolver systemDateResolver;


    @Override
    protected Dao<Price> getDao() {
        return priceDao;
    }

    @Override
    protected EntityBuilder<Price> getEntityBuilder() {
        return priceBuilder;
    }

    @Override
    protected List<EntityResolver<?>> getEntityResolvers() {
        return Arrays.asList(systemDateResolver);
    }
}
