package com.stock.component;

import com.stock.domain.StockType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
public class PreferredStockDividendYieldCalculatorTest extends AbstractDividendYieldCalculatorTestSupport {

    private PreferredStockDividendYieldCalculator preferredStockDividendYieldCalculator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        preferredStockDividendYieldCalculator = new PreferredStockDividendYieldCalculator(tickerPriceService);
    }

    @Test
    public void testCalculateDividendYield() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        BigDecimal dividendYield = preferredStockDividendYieldCalculator.calculateDividendYield(stock, localDate);
        Assert.assertEquals(new BigDecimal("4.000000"), dividendYield);

    }

    @Test
     public void testCalculateDividendYieldWithZeroDividend() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(0);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        BigDecimal dividendYield = preferredStockDividendYieldCalculator.calculateDividendYield(stock, localDate);
        Assert.assertEquals(new BigDecimal("0.000000"), dividendYield);
    }

    @Test
    public void testCalculateDividendYieldWithZeroParValue() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.00"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        BigDecimal dividendYield = preferredStockDividendYieldCalculator.calculateDividendYield(stock, localDate);
        Assert.assertEquals(new BigDecimal("0.000000"), dividendYield);
    }

    @Test(expected = RuntimeException.class)
    public void testCalculateDividendYieldWithPriceAbsent() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(null);
        preferredStockDividendYieldCalculator.calculateDividendYield(stock, localDate);
    }

    @Test
    public void testGetStockType() throws Exception {
        Assert.assertEquals(StockType.PREFERRED, preferredStockDividendYieldCalculator.getStockType());
    }

}