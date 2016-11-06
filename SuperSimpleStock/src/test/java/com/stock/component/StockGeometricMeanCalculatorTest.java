package com.stock.component;

import com.stock.service.TickerPriceService;
import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.persistence.StockDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
public class StockGeometricMeanCalculatorTest {

    @Mock
    private TickerPriceService tickerPriceService;

    @Mock
    private StockDao stockDao;
    private final LocalDate calculationDate = LocalDate.now();
    private final String exchange = "GBCE";
    private List<Stock> stocks;


    private StockGeometricMeanCalculator stockGeometricMeanCalculator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        stockGeometricMeanCalculator = new StockGeometricMeanCalculator(stockDao, tickerPriceService);
        Stock tea = new Stock("TEA", "GBCE", StockType.COMMON, new BigDecimal("0.00"), 0 , new BigDecimal("1.00"), "GBP");
        Stock ale = new Stock("ALE", "GBCE", StockType.COMMON, new BigDecimal("0.23"), 0 , new BigDecimal("0.60"), "GBP");
        Stock gin = new Stock("GIN", "GBCE", StockType.PREFERRED, new BigDecimal("0.08"), 2 , new BigDecimal("1.00"), "GBP");
        stocks = Arrays.asList(tea, ale, gin);
    }

    @Test
    public void testcalculateGeometricMeanForAnExch() throws Exception {
        Mockito.when(stockDao.findAllByExch(exchange)).thenReturn(stocks);
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(0), calculationDate)).thenReturn(new BigDecimal("1.05"));
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(1), calculationDate)).thenReturn(new BigDecimal("1.10"));
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(2), calculationDate)).thenReturn(new BigDecimal("1.30"));

        BigDecimal geometricMean = stockGeometricMeanCalculator.calculateGeometricMeanForAnExch(exchange, calculationDate);

        Assert.assertEquals(new BigDecimal("1.145096"), geometricMean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcalculateGeometricMeanWithNoStockPresent() throws Exception {
        Mockito.when(stockDao.findAllByExch(exchange)).thenReturn(null);
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(0), calculationDate)).thenReturn(new BigDecimal("1.05"));
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(1), calculationDate)).thenReturn(new BigDecimal("1.10"));
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(2), calculationDate)).thenReturn(new BigDecimal("1.30"));

        stockGeometricMeanCalculator.calculateGeometricMeanForAnExch(exchange, calculationDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testcalculateGeometricMeanWithMissingPrice() throws Exception {
        Mockito.when(stockDao.findAllByExch(exchange)).thenReturn(stocks);
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(0), calculationDate)).thenReturn(null);
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(1), calculationDate)).thenReturn(new BigDecimal("1.10"));
        Mockito.when(tickerPriceService.getTickerPrice(stocks.get(2), calculationDate)).thenReturn(new BigDecimal("1.30"));

        stockGeometricMeanCalculator.calculateGeometricMeanForAnExch(exchange, calculationDate);
    }

}