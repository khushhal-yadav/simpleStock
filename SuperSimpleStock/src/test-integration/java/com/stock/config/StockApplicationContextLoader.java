package com.stock.config;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;

/**
 * Created by khush on 06/11/2016.
 */
public class StockApplicationContextLoader implements ContextLoader {
    @Override
    public String[] processLocations(Class<?> aClass, String... locations) {
        return locations;
    }

    @Override
    public ApplicationContext loadContext(String... locations) throws Exception {
        return StockApplicationContext.getInstance();
    }
}
