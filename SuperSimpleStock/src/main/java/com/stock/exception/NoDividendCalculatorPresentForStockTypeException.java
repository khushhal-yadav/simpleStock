package com.stock.exception;

import java.util.function.Supplier;

/**
 * Created by khush on 06/11/2016.
 */
public class NoDividendCalculatorPresentForStockTypeException extends RuntimeException implements Supplier<RuntimeException> {

    public NoDividendCalculatorPresentForStockTypeException() {
    }

    public NoDividendCalculatorPresentForStockTypeException(String message) {
        super(message);
    }

    @Override
    public NoDividendCalculatorPresentForStockTypeException get() {
        return new NoDividendCalculatorPresentForStockTypeException("No Dividend Calculator Present for the provided Stock Type");
    }
}
