package com.stock.jbehave.setup;

import com.stock.domain.Stock;
import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.builder.StockBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.persistence.Dao;
import com.stock.persistence.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class StockPersister extends AbstractEntityPersister<Stock> {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockBuilder stockBuilder;


    @Override
    protected Dao<Stock> getDao() {
        return stockDao;
    }

    @Override
    protected EntityBuilder<Stock> getEntityBuilder() {
        return stockBuilder;
    }

    @Override
    protected List<EntityResolver<?>> getEntityResolvers() {
        return null;
    }
}
