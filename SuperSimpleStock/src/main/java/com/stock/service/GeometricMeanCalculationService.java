package com.stock.service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface GeometricMeanCalculationService {

    BigDecimal calculateGeometricMeanForAnExch(final String exchange, final LocalDate calculationDate);
}
