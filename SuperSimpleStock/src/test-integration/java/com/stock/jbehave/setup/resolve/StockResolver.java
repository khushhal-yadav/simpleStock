package com.stock.jbehave.setup.resolve;

import com.stock.domain.Stock;
import com.stock.persistence.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by khush on 10/09/2016.
 */
@Component
public class StockResolver implements EntityResolver<Stock> {

    @Autowired
    private StockDao stockDao;

    @Override
    public Stock resolveEntity(String params, Map<String, Map<String, Object>> persistenceContext) {
        String param[] = params.split(",", 0);
        return stockDao.findBySymbolExch(param[0], param[1]);
    }

    @Override
    public boolean validateFor(Class<?> type) {
        return type.equals(Stock.class);
    }
}
