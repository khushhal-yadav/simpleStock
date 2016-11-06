package com.stock.service;

import com.stock.domain.FxRate;
import com.stock.domain.Price;
import com.stock.domain.Stock;
import com.stock.domain.StockType;
import com.stock.domain.SystemDate;
import com.stock.exception.FxRateNotPresentException;
import com.stock.exception.PriceNotPresentExcption;
import com.stock.exception.SystemDateNotPresentException;
import com.stock.persistence.FxRateDao;
import com.stock.persistence.PriceDao;
import com.stock.persistence.SystemDateDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Created by khush on 06/11/2016.
 */
public class TickerPriceServiceImplTest {

    @Mock
    private SystemDateDao systemDateDao;

    @Mock
    private FxRateDao fxRateDao;

    @Mock
    private PriceDao priceDao;

    private TickerPriceService tickerPriceService;
    private LocalDate calculationDate = LocalDate.now();
    private SystemDate systemDate;
    private Stock stock;
    private Price price;
    private FxRate fxRate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        systemDate = new SystemDate(calculationDate, true, LocalDateTime.now());
        price = new Price("TEA", "GBCE", new BigDecimal("1.05"), "EUR", systemDate);
        fxRate = new FxRate("EUR", "GBP", new BigDecimal("0.85000"), systemDate);
        stock = new Stock("TEA", "GBCE", StockType.COMMON, new BigDecimal("0.00"), 0 , new BigDecimal("1.00"), "GBP");
        tickerPriceService = new TickerPriceServiceImpl(systemDateDao, fxRateDao, priceDao);
    }

    @Test
     public void testGetTickerPrice() throws Exception {
        Mockito.when(systemDateDao.findByCalculationDate(calculationDate)).thenReturn(systemDate);
        Mockito.when(priceDao.findBySymbolExch(stock.getSymbol(), stock.getExchange(), systemDate)).thenReturn(price);
        Mockito.when(fxRateDao.findByCurrencyFromTo(price.getCurrencyCode(), stock.getCurrencyCode(), systemDate)).thenReturn(fxRate);

        final BigDecimal tickerPrice = tickerPriceService.getTickerPrice(stock, calculationDate);

        assertEquals(new BigDecimal("0.892500"), tickerPrice);

    }

    @Test(expected = SystemDateNotPresentException.class)
    public void testGetTickerPriceWhenSystemDateNotPresent() throws Exception {
        Mockito.when(systemDateDao.findByCalculationDate(calculationDate)).thenThrow(new SystemDateNotPresentException("No SystemDate present", new NoResultException()));

        final BigDecimal tickerPrice = tickerPriceService.getTickerPrice(stock, calculationDate);

        assertEquals(new BigDecimal("0.892500"), tickerPrice);

    }

    @Test(expected = PriceNotPresentExcption.class)
    public void testGetTickerPriceWhenPriceNotPresent() throws Exception {
        Mockito.when(systemDateDao.findByCalculationDate(calculationDate)).thenReturn(systemDate);
        Mockito.when(priceDao.findBySymbolExch(stock.getSymbol(), stock.getExchange(), systemDate)).
                thenThrow(new PriceNotPresentExcption("No price present", new NoResultException()));

        final BigDecimal tickerPrice = tickerPriceService.getTickerPrice(stock, calculationDate);

        assertEquals(new BigDecimal("0.892500"), tickerPrice);

    }

    @Test(expected = FxRateNotPresentException.class)
    public void testGetTickerPriceWhenFxRateNotPresent() throws Exception {
        Mockito.when(systemDateDao.findByCalculationDate(calculationDate)).thenReturn(systemDate);
        Mockito.when(priceDao.findBySymbolExch(stock.getSymbol(), stock.getExchange(), systemDate)).thenReturn(price);
        Mockito.when(fxRateDao.findByCurrencyFromTo(price.getCurrencyCode(), stock.getCurrencyCode(), systemDate)).
                thenThrow(new FxRateNotPresentException("No FxRate present", new NoResultException()));

        final BigDecimal tickerPrice = tickerPriceService.getTickerPrice(stock, calculationDate);

        assertEquals(new BigDecimal("0.892500"), tickerPrice);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTickerPriceWithNullStock() throws Exception {
        tickerPriceService.getTickerPrice(null, calculationDate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTickerPriceWithNullCalcDate() throws Exception {
        tickerPriceService.getTickerPrice(stock, null);
    }




}