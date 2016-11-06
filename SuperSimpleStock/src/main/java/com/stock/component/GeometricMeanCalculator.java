package com.stock.component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface GeometricMeanCalculator {
    BigDecimal calculateGeometricMeanForAnExch(final String exchage, final LocalDate calculationDate);
}
