package com.stock.app;

import com.stock.service.DividentYieldCalcutionService;
import com.stock.service.GeometricMeanCalculationService;
import com.stock.service.PECalculationService;
import com.stock.service.TradeService;
import com.stock.common.AbstractSteps;
import com.stock.common.AppStory;
import com.stock.domain.Stock;
import com.stock.persistence.StockDao;
import junit.framework.Assert;
import org.jbehave.core.annotations.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by khush on 06/11/2016.
 */
@Component
@Profile("test")
public class StockSteps extends AbstractSteps {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private StockDao stockDao;

    @Autowired
    private DividentYieldCalcutionService dividentYieldCalcutionService;

    @Autowired
    private PECalculationService peCalculationService;

    @Autowired
    private TradeService tradeService;

    @Autowired
    private GeometricMeanCalculationService geometricMeanCalculationService;

    @Then("Dividend Yield for Stock with Symbol $symbol and ExchangeCode $exhcange for calculation date $dateString is $expectedDividendYield")
    public void calculateDividendYield(String symbol, String exchange, String dateString, String expectedDividendYield) {
        LocalDate calculationDate = LocalDate.parse(dateString, AppStory.DATE_TIME_FORMATTER);
        Stock stock = stockDao.findBySymbolExch(symbol, exchange);

        BigDecimal dividendYield = dividentYieldCalcutionService.calculateDividendYield(stock, calculationDate);

        log.debug("dividendYield : " + dividendYield);
        Assert.assertEquals(new BigDecimal(expectedDividendYield), dividendYield);
    }

    @Then("PE Ratio for Stock with Symbol $symbol and ExchangeCode $exhcange for calculation date $dateString is $expectedPeRatio")
    public void calculatePERatio(String symbol, String exchange, String dateString, String expectedPeRatio) {
        LocalDate calculationDate = LocalDate.parse(dateString, AppStory.DATE_TIME_FORMATTER);
        Stock stock = stockDao.findBySymbolExch(symbol, exchange);

        BigDecimal peRatio = peCalculationService.calculatePERatio(stock, calculationDate);

        log.debug("peRatio : " + peRatio);
        Assert.assertEquals(new BigDecimal(expectedPeRatio), peRatio);
    }

    @Then("Stock price based on traddes booked in last $minutes mins is $expectedStockPrice")
    public void calculatePERatio(String minutes, String expectedStockPrice) {
        int mins = Integer.parseInt(minutes);
        LocalDateTime recordedDateTime = LocalDateTime.now().minus(mins, ChronoUnit.MINUTES);

        BigDecimal stockPrice = tradeService.getStockPriceBasedOnTradeBookedAfterRecordedDateTime(recordedDateTime);

        log.debug("stockPrice : " + stockPrice);
        Assert.assertEquals(new BigDecimal(expectedStockPrice), stockPrice);
    }

    @Then("Geometric Mean of all stokcs for $exchange exchange on $dateString is $expectedGeometricMean")
    public void calculateGeometricMean(String exchange, String dateString, String expectedGeometricMean) {

        LocalDate calculationDate = LocalDate.parse(dateString, AppStory.DATE_TIME_FORMATTER);

        BigDecimal geometricMeanForAnExch = geometricMeanCalculationService.calculateGeometricMeanForAnExch(exchange, calculationDate);

        log.debug("geometricMeanForAnExch : " + geometricMeanForAnExch);
        Assert.assertEquals(new BigDecimal(expectedGeometricMean), geometricMeanForAnExch);
    }
}
