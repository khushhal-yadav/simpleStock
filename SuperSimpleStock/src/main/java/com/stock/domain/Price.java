package com.stock.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

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
        if (obj == null)
            return  false;
        if (getClass() != obj.getClass())
            return false;
        Price other = (Price) obj;
        return Objects.equals(this.symbol, other.symbol)
                && Objects.equals(this.exchange, other.exchange)
                && Objects.equals(this.systemDate, other.systemDate);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
}
