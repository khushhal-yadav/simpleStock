package com.stock.jbehave.setup;

import org.jbehave.core.model.ExamplesTable;

import java.util.Map;

/**
 * Created by khush on 06/11/2016.
 */
public interface EntityPersister<T> {

    Map<String, T> persist(ExamplesTable examplesTable, Map<String, Map<String, Object>> persistenceContext) throws Exception;
    Class<T> getEntityClass();
}
