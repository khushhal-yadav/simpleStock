package com.stock.persistence;

import com.stock.domain.Stock;

import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
public interface StockDao extends Dao<Stock> {

    Stock findBySymbolExch(String symbol, String exchange);
    List<Stock> findAllByExch(String exchange);
}
