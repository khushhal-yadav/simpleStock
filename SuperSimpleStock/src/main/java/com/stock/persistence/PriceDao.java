package com.stock.persistence;

import com.stock.domain.Price;
import com.stock.domain.SystemDate;
import com.stock.persistence.Dao;

/**
 * Created by khush on 06/11/2016.
 */
public interface PriceDao extends Dao<Price> {

    Price findBySymbolExch(String symbol, String exchange, SystemDate systemDate);

}
