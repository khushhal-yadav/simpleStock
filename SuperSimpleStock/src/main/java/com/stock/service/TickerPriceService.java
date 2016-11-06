package com.stock.service;

import com.stock.domain.Stock;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface TickerPriceService {

    BigDecimal getTickerPrice(Stock stock, LocalDate calculationDate);

}
