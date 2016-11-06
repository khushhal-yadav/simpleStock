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
public class PreferredStockPECalculatorTest extends AbstractPECalculatorTestSupport{

    private PreferredStockPECalculator preferredStockPECalculator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        preferredStockPECalculator = new PreferredStockPECalculator(tickerPriceService);
    }

    @Test
    public void testcalculatePE() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        BigDecimal dividendYield = preferredStockPECalculator.calculatePE(stock, localDate);
        Assert.assertEquals(new BigDecimal("0.250000"), dividendYield);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDividendYieldWithZeroDividend() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(0);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        preferredStockPECalculator.calculatePE(stock, localDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateDividendYieldWithZeroParValue() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.00"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(new BigDecimal("0.02"));
        preferredStockPECalculator.calculatePE(stock, localDate);
    }

    @Test
    public void testCalculateDividendYieldWithPriceAbsent() throws Exception {
        Mockito.when(stock.getFixedDividend()).thenReturn(8);
        Mockito.when(stock.getParValue()).thenReturn(new BigDecimal("0.01"));
        Mockito.when(tickerPriceService.getTickerPrice(stock, localDate)).thenReturn(null);
        BigDecimal dividendYield = preferredStockPECalculator.calculatePE(stock, localDate);
        Assert.assertEquals(new BigDecimal("0.000000"), dividendYield);
    }

    @Test
    public void testGetStockType() throws Exception {
        Assert.assertEquals(StockType.PREFERRED, preferredStockPECalculator.getStockType());
    }


}