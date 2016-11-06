package com.stock.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by khush on 06/11/2016.
 */
@MappedSuperclass
@Embeddable
public class AbstractStock {

    @NotNull
    protected String symbol;

    @NotNull
    protected String exchange;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_type")
    @NotNull
    protected StockType stockType;

    @Column(name = "last_dividend")
    protected BigDecimal lastDividend = BigDecimal.ZERO;

    @Column(name = "fixed_dividend")
    protected BigDecimal fixedDividend = BigDecimal.ZERO;

    @Column(name = "par_value")
    protected BigDecimal parValue = BigDecimal.ZERO;
}
