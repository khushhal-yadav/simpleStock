package com.stock.service;

import com.stock.component.CommonStockPECalculator;
import com.stock.component.PreferredStockPECalculator;
import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.exception.NoPECalculatorPresentForStockTypeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Created by khush on 06/11/2016.
 */
public class PECalculationServiceImplTest {

    private PECalculationService peCalculationService;

    @Mock
    private CommonStockPECalculator commonStockPECalculator;

    @Mock
    private PreferredStockPECalculator preferredStockPECalculator;

    @Mock
    private Stock stock;

    private final LocalDate calculationDate = LocalDate.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(commonStockPECalculator.getStockType()).thenReturn(StockType.COMMON);
        Mockito.when(preferredStockPECalculator.getStockType()).thenReturn(StockType.PREFERRED);
        Mockito.when(commonStockPECalculator.calculatePE(stock, calculationDate)).thenReturn(new BigDecimal(BigInteger.TEN));
        Mockito.when(preferredStockPECalculator.calculatePE(stock, calculationDate)).thenReturn(new BigDecimal(BigInteger.TEN));
    }

    @Test
    public void testCalculatePEForCommonStock() throws Exception {
        peCalculationService = new PECalculationServiceImpl(Arrays.asList(commonStockPECalculator, preferredStockPECalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.COMMON);
        Assert.assertEquals(BigDecimal.TEN, peCalculationService.calculatePERatio(stock, calculationDate));
    }

    @Test
     public void testCalculatePEForPreferredStock() throws Exception {
        peCalculationService = new PECalculationServiceImpl(Arrays.asList(commonStockPECalculator, preferredStockPECalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.PREFERRED);
        Assert.assertEquals(BigDecimal.TEN, peCalculationService.calculatePERatio(stock, calculationDate));
    }

    @Test(expected = NoPECalculatorPresentForStockTypeException.class)
    public void testCalculatePEWhenNoPECalculatorPresent() throws Exception {
        peCalculationService = new PECalculationServiceImpl(Arrays.asList(commonStockPECalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.PREFERRED);
        Assert.assertEquals(BigDecimal.TEN, peCalculationService.calculatePERatio(stock, calculationDate));
    }

}