package com.stock.jbehave.setup.resolve;

import com.stock.domain.Trade;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

/**
 * Created by khush on 12/09/2016.
 */
public class TradeResolver implements EntityResolver<Trade> {

    @Override
    public Trade resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext) {
        throw new NotImplementedException();
    }

    @Override
    public boolean validateFor(Class<?> type) {
        return type.equals(Trade.class);
    }
}
