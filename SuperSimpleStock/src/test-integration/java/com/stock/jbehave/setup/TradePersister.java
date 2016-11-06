package com.stock.jbehave.setup;

import com.stock.domain.Trade;
import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.builder.TradeBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.persistence.Dao;
import com.stock.persistence.TradeDao;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class TradePersister extends AbstractEntityPersister<Trade> {

    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private TradeBuilder tradeBuilder;


    @Override
    protected Dao<Trade> getDao() {
        return tradeDao;
    }

    @Override
    protected EntityBuilder<Trade> getEntityBuilder() {
        return tradeBuilder;
    }

    @Override
    protected List<EntityResolver<?>> getEntityResolvers() {
        return null;
    }

}
