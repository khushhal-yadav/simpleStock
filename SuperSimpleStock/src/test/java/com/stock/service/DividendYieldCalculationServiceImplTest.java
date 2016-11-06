package com.stock.service;

import com.stock.component.CommonStockDividendYieldCalculator;
import com.stock.component.PreferredStockDividendYieldCalculator;
import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.exception.NoDividendCalculatorPresentForStockTypeException;
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
public class DividendYieldCalculationServiceImplTest {

    private DividentYieldCalcutionService dividentYieldCalcutionService;

    @Mock
    private CommonStockDividendYieldCalculator commonStockDividendYieldCalculator;

    @Mock
    private PreferredStockDividendYieldCalculator preferredStockDividendYieldCalculator;

    @Mock
    private Stock stock;

    private final LocalDate calculationDate = LocalDate.now();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(commonStockDividendYieldCalculator.getStockType()).thenReturn(StockType.COMMON);
        Mockito.when(preferredStockDividendYieldCalculator.getStockType()).thenReturn(StockType.PREFERRED);
        Mockito.when(commonStockDividendYieldCalculator.calculateDividendYield(stock, calculationDate)).thenReturn(new BigDecimal(BigInteger.TEN));
        Mockito.when(preferredStockDividendYieldCalculator.calculateDividendYield(stock, calculationDate)).thenReturn(new BigDecimal(BigInteger.TEN));
    }

    @Test
    public void testCalculateDividendYieldForCommonStock() throws Exception {
        dividentYieldCalcutionService = new DividendYieldCalculationServiceImpl(Arrays.asList(commonStockDividendYieldCalculator, preferredStockDividendYieldCalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.COMMON);
        Assert.assertEquals(BigDecimal.TEN, dividentYieldCalcutionService.calculateDividendYield(stock, calculationDate));

    }

    @Test
    public void testCalculateDividendYieldForPreferredStock() throws Exception {
        dividentYieldCalcutionService = new DividendYieldCalculationServiceImpl(Arrays.asList(commonStockDividendYieldCalculator, preferredStockDividendYieldCalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.PREFERRED);
        Assert.assertEquals(BigDecimal.TEN, dividentYieldCalcutionService.calculateDividendYield(stock, calculationDate));

    }

    @Test(expected = NoDividendCalculatorPresentForStockTypeException.class)
    public void testCalculateDividendYieldWhenNoDividendCalculatorPresent() throws Exception {
        dividentYieldCalcutionService = new DividendYieldCalculationServiceImpl(Arrays.asList(preferredStockDividendYieldCalculator));
        Mockito.when(stock.getStockType()).thenReturn(StockType.COMMON);
        dividentYieldCalcutionService.calculateDividendYield(stock, calculationDate);

    }


}