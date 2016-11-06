package com.stock.util;

import java.math.BigDecimal;

/**
 * Created by khush on 06/11/2016.
 */
public class BigDecimalConverter {

    public static final BigDecimal convert(BigDecimal bigDecimal) {
        return bigDecimal.setScale(6, BigDecimal.ROUND_HALF_UP);
    }

}
