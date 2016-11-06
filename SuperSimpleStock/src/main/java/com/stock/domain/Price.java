package com.stock.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by khush on 06/11/2016.
 */
@Entity
@Table(name = "price", schema = "stockapp")
@NamedQueries({
        @NamedQuery(name = Price.FIND_BY_SYMBOL_EXCH, query = "FROM Price WHERE symbol = :symbol AND exchange = :exchange AND systemDate = :systemDate")
})
public class Price {

    public static final String FIND_BY_SYMBOL_EXCH = "Price.findBySymbolExch";
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    @Length(min = 3, max = 3)
    private String symbol;

    @NotNull
    @Length(min = 4, max = 4)
    private String exchange;

    @NotNull
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @NotNull
    @Length(min = 3, max = 3)
    @Column(name = "currency_code")
    private String currencyCode;

    @ManyToOne
    @JoinColumn(name = "system_date", nullable = false)
    private SystemDate systemDate;

    public Price() {
    }

    public Price(String symbol, String exchange, BigDecimal marketPrice, String currencyCode, SystemDate systemDate) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.marketPrice = marketPrice;
        this.currencyCode = currencyCode;
        this.systemDate = systemDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, exchange, systemDate);
    }

    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
                .filter(o -> o.getClass() == getClass())
                .map(Price.class::cast)
                .filter(o -> Objects.equals(this.symbol, o.symbol))
                .filter(o -> Objects.equals(this.exchange, o.exchange))
                .filter(o -> Objects.equals(this.systemDate, o.systemDate))
                .isPresent();
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
}
