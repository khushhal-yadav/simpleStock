package com.stock.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by khush on 06/11/2016.
 */
@Entity
@Table(name = "fx_rate", schema = "stockapp")
@NamedQueries({
        @NamedQuery(name = FxRate.FIND_BY_CURRENCY_FROM_TO, query = "FROM FxRate WHERE currencyFrom = :currencyFrom AND currencyTo = :currencyTo AND systemDate =:systemDate")
})
public class FxRate {

    public static final String FIND_BY_CURRENCY_FROM_TO = "FxRate.findByCurrencyFromTo";

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(name = "currency_from")
    @Length(min = 3, max = 3)
    private String currencyFrom;

    @NotNull
    @Column(name = "currency_to")
    @Length(min = 3, max = 3)
    private String currencyTo;

    @NotNull
    @Column(name = "fx_rate")
    private BigDecimal rate;

    @ManyToOne
    @JoinColumn(name = "system_date")
    private SystemDate systemDate;

    public FxRate() {
    }

    public FxRate(String currencyFrom, String currencyTo, BigDecimal rate, SystemDate systemDate) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.rate = rate;
        this.systemDate = systemDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyFrom, currencyTo, systemDate);
    }

    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
                .filter(o -> o.getClass() == getClass())
                .map(FxRate.class::cast)
                .filter(o -> Objects.equals(this.currencyFrom, o.currencyFrom))
                .filter(o -> Objects.equals(this.currencyTo, o.currencyTo))
                .filter(o -> Objects.equals(this.systemDate, o.systemDate))
                .isPresent();
    }

    public BigDecimal getRate() {
        return rate;
    }
}
