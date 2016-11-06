package com.stock.service;

import com.stock.component.StockDividendYieldCalculator;
import com.stock.domain.Stock;
import com.stock.exception.NoDividendCalculatorPresentForStockTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Service
public class DividendYieldCalculationServiceImpl implements DividentYieldCalcutionService {

    private final List<StockDividendYieldCalculator> stockDividendYieldCalculators;

    @Autowired
    public DividendYieldCalculationServiceImpl(List<StockDividendYieldCalculator> stockDividendYieldCalculators) {
        this.stockDividendYieldCalculators = stockDividendYieldCalculators;
    }

    public BigDecimal calculateDividendYield(final Stock stock, final LocalDate calculationDate) {
        return stockDividendYieldCalculators.stream().filter(dividendYieldCalculator ->
                (dividendYieldCalculator.getStockType() == stock.getStockType()))
                .findFirst().orElseThrow(NoDividendCalculatorPresentForStockTypeException::new)
                .calculateDividendYield(stock, calculationDate);
    }


}
