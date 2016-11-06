package com.stock.service;

import com.stock.component.StockPECalculator;
import com.stock.domain.Stock;
import com.stock.exception.NoPECalculatorPresentForStockTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
@Service
public class PECalculationServiceImpl implements PECalculationService {

    private final List<StockPECalculator> stockPeCalculators;

    @Autowired
    public PECalculationServiceImpl(List<StockPECalculator> stockPeCalculators) {
        this.stockPeCalculators = stockPeCalculators;
    }

    @Override
    public BigDecimal calculatePERatio(Stock stock, LocalDate calculationDate) {
        return stockPeCalculators.stream().filter(peCalculator ->
                (peCalculator.getStockType() == stock.getStockType()))
                .findFirst().orElseThrow(NoPECalculatorPresentForStockTypeException::new)
                .calculatePE(stock, calculationDate);
    }
}
