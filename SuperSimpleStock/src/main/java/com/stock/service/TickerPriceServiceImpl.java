package com.stock.service;

import com.stock.domain.FxRate;
import com.stock.domain.Price;
import com.stock.domain.Stock;
import com.stock.domain.SystemDate;
import com.stock.persistence.FxRateDao;
import com.stock.persistence.PriceDao;
import com.stock.persistence.SystemDateDao;
import com.stock.util.BigDecimalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by khush on 06/11/2016.
 */
@Service
public class TickerPriceServiceImpl implements TickerPriceService {


    private final SystemDateDao systemDateDao;
    private final FxRateDao fxRateDao;
    private final PriceDao priceDao;

    @Autowired
    public TickerPriceServiceImpl(SystemDateDao systemDateDao, FxRateDao fxRateDao, PriceDao priceDao) {
        this.systemDateDao = systemDateDao;
        this.fxRateDao = fxRateDao;
        this.priceDao = priceDao;
    }

    public BigDecimal getTickerPrice(Stock stock, LocalDate calculationDate) {
        Assert.isTrue(stock != null, "Stock can't be null");
        Assert.isTrue(calculationDate != null, "Calculation date can't be null");

        SystemDate calcDate = systemDateDao.findByCalculationDate(calculationDate);
        Price price = priceDao.findBySymbolExch(stock.getSymbol(), stock.getExchange(), calcDate);
        FxRate fxRate = fxRateDao.findByCurrencyFromTo(price.getCurrencyCode(),stock.getCurrencyCode(), calcDate);

        //below are not null fields in db, so should always be there.
        return BigDecimalConverter.convert(price.getMarketPrice().multiply(fxRate.getRate()));
    }
}
