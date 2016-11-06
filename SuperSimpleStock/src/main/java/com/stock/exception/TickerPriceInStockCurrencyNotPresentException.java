package com.stock.exception;

import java.util.function.Supplier;

/**
 * Created by khush on 06/11/2016.
 */
public class TickerPriceInStockCurrencyNotPresentException extends RuntimeException implements Supplier<RuntimeException> {

    public TickerPriceInStockCurrencyNotPresentException() {
    }

    public TickerPriceInStockCurrencyNotPresentException(String message) {
        super(message);
    }

    @Override
    public TickerPriceInStockCurrencyNotPresentException get() {
        return new TickerPriceInStockCurrencyNotPresentException("Ticker Price in Stock Currency not Present");
    }
}
