package com.stock.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by khush on 06/11/2016.
 */
@Entity
@Table(name = "stock", schema = "stockapp")
@NamedQueries({
        @NamedQuery(name = Stock.FIND_BY_SYMBOL_EXCH, query = "FROM Stock WHERE symbol = :symbol AND exchange = :exchange"),
        @NamedQuery(name = Stock.FIND_ALL_BY_EXCH, query = "FROM Stock WHERE exchange = :exchange")
})
public class Stock {

    public static final String FIND_BY_SYMBOL_EXCH = "Stock.findBySymbolExch";
    public static final String FIND_ALL_BY_EXCH = "Stock.findAllByExch";

    @Id
    @GeneratedValue
    private String id;

    @NotNull
    private String symbol;

    @NotNull
    private String exchange;

    @Enumerated(EnumType.STRING)
    @Column(name = "stock_type")
    @NotNull
    private StockType stockType;

    @Column(name = "last_dividend")
    private BigDecimal lastDividend = BigDecimal.ZERO;

    @Column(name = "fixed_dividend")
    private int fixedDividend = 0;

    @Column(name = "par_value")
    private BigDecimal parValue = BigDecimal.ZERO;

    @NotNull
    @Length(min = 3, max = 3)
    @Column(name = "currency_code")
    private String currencyCode;

    public Stock() {
    }

    public Stock(String symbol, String exchange, StockType stockType, BigDecimal lastDividend, int fixedDividend,
                 BigDecimal parValue, final String currencyCode) {
        this.symbol = symbol;
        this.exchange = exchange;
        this.stockType = stockType;
        this.lastDividend = lastDividend;
        this.fixedDividend = fixedDividend;
        this.parValue = parValue;
        this.currencyCode = currencyCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, exchange, stockType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return  false;
        if (getClass() != obj.getClass())
            return false;
        Stock other = (Stock) obj;
        return Objects.equals(this.symbol, other.symbol)
                && Objects.equals(this.exchange, other.exchange)
                && Objects.equals(this.stockType, other.stockType);
    }

    public int getFixedDividend() {
        return fixedDividend;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public StockType getStockType() {
        return stockType;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}
