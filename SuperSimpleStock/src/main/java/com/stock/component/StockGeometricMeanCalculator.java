package com.stock.component;

import com.stock.domain.Stock;
import com.stock.persistence.StockDao;
import com.stock.service.TickerPriceService;
import com.stock.util.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class StockGeometricMeanCalculator implements GeometricMeanCalculator {

    private final StockDao stockDao;

    private final TickerPriceService tickerPriceService;

    @Autowired
    public StockGeometricMeanCalculator(StockDao stockDao, TickerPriceService tickerPriceService) {
        this.stockDao = stockDao;
        this.tickerPriceService = tickerPriceService;
    }

    @Override
    public BigDecimal calculateGeometricMeanForAnExch(final String exchange, final LocalDate calculationDate) {
        Assert.isTrue(exchange != null, "Exchange can't be null");
        Assert.isTrue(calculationDate != null, "Calculation Date can't be null");

        final List<Stock> allStockByExch = Optional.ofNullable(stockDao.findAllByExch(exchange)).orElse(new ArrayList<>());
        Assert.isTrue(allStockByExch.size() != 0, "No stocks present for the Exchange for the given Calculation date");

        List<BigDecimal> stockPrices = allStockByExch.stream().map(stock ->
                Optional.ofNullable(tickerPriceService.getTickerPrice(stock, calculationDate)).orElse(BigDecimal.ZERO)).collect(Collectors.toList());
        final int root = stockPrices.size();
        BigDecimal multipliedPrice = stockPrices.stream().reduce(BigDecimal.ONE, BigDecimal::multiply);
        Assert.isTrue(!(BigDecimal.ZERO.compareTo(multipliedPrice) == 0), "Price missing for the stock for the Exchange and Calculation date provided");

        final BigDecimal geometricMean = BigDecimalConverter.convert(new BigDecimal(Math.pow(multipliedPrice.doubleValue(), (1.0 / root))));

        return  geometricMean;
    }
}
