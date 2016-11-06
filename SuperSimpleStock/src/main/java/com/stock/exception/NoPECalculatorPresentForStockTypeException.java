package com.stock.exception;

import java.util.function.Supplier;

/**
 * Created by khush on 06/11/2016.
 */
public class NoPECalculatorPresentForStockTypeException extends RuntimeException implements Supplier<RuntimeException> {

    public NoPECalculatorPresentForStockTypeException() {
    }

    public NoPECalculatorPresentForStockTypeException(String message) {
        super(message);
    }

    @Override
    public NoPECalculatorPresentForStockTypeException get() {
        return new NoPECalculatorPresentForStockTypeException("No PE ratio Calculator Present for the provided Stock Type");
    }
}
