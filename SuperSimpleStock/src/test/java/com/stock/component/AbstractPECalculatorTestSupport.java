package com.stock.component;

import com.stock.service.TickerPriceService;
import com.stock.domain.Stock;
import org.mockito.Mock;

import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public class AbstractPECalculatorTestSupport {

    @Mock
    protected TickerPriceService tickerPriceService;

    @Mock
    protected Stock stock;
    protected LocalDate localDate = LocalDate.now();

}