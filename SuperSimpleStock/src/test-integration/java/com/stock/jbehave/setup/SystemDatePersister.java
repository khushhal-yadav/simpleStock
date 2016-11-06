package com.stock.jbehave.setup;

import com.stock.domain.SystemDate;
import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.builder.SystemDateBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.persistence.Dao;
import com.stock.persistence.SystemDateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class SystemDatePersister<T> extends AbstractEntityPersister<SystemDate> {

    @Autowired
    private SystemDateBuilder systemDateBuilder;

    @Autowired
    private SystemDateDao systemDateDao;

    @Override
    protected Dao<SystemDate> getDao() {
        return systemDateDao;
    }

    @Override
    protected EntityBuilder<SystemDate> getEntityBuilder() {
        return this.systemDateBuilder;
    }

    @Override
    protected List<EntityResolver<?>> getEntityResolvers() {
        return null;
    }
}
