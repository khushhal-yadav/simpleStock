package com.stock.jbehave.setup.resolve;

import org.junit.Test;

import java.util.Map;

/**
 * Created by khush on 10/09/2016.
 */
public interface EntityResolver<T> {
    T resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext);
    boolean validateFor(Class<?> type);
}
