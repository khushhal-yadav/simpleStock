package com.stock.component;

import com.stock.domain.Stock;
import com.stock.domain.StockType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface StockPECalculator extends StockCalculator{
    BigDecimal calculatePE(Stock stock, LocalDate calculationDate);

}
