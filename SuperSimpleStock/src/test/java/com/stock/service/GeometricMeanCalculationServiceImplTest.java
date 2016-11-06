package com.stock.service;

import com.stock.component.GeometricMeanCalculator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by khush on 06/11/2016.
 */
public class GeometricMeanCalculationServiceImplTest {

    private GeometricMeanCalculationService geometricMeanCalculationService;

    @Mock
    private GeometricMeanCalculator geometricMeanCalculator;

    private final LocalDate calculationDate = LocalDate.now();
    private final String exchange = "GBCE";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        geometricMeanCalculationService = new GeometricMeanCalculationServiceImpl(geometricMeanCalculator);
    }

    @Test
    public void testcalculateGeometricMeanForAnExch() throws Exception {
        Mockito.when(geometricMeanCalculator.calculateGeometricMeanForAnExch(exchange, calculationDate)).thenReturn(BigDecimal.TEN);
        Assert.assertEquals(BigDecimal.TEN, geometricMeanCalculationService.calculateGeometricMeanForAnExch(exchange, calculationDate));

    }

}