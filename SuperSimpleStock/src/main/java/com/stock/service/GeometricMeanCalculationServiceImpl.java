package com.stock.service;

import com.stock.component.GeometricMeanCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
@Service
public class GeometricMeanCalculationServiceImpl implements GeometricMeanCalculationService {

    private final GeometricMeanCalculator geometricMeanCalculator;

    @Autowired
    public GeometricMeanCalculationServiceImpl(GeometricMeanCalculator geometricMeanCalculator) {
        this.geometricMeanCalculator = geometricMeanCalculator;
    }

    @Override
    public BigDecimal calculateGeometricMeanForAnExch(final String exchange, final LocalDate calculationDate) {
        return geometricMeanCalculator.calculateGeometricMeanForAnExch(exchange, calculationDate);
    }
}
