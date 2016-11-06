package com.stock.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by khush on 06/11/2016.
 */
@Entity
@Table(name = "trade", schema = "stockapp")
@NamedQueries({
        @NamedQuery(name = Trade.FIND_TRADES_BOOKED_AFTER_RECORD_DATE_TIME, query = "FROM Trade where recordedTime >= :recordedTime")
})
public class Trade {

    public static final String FIND_TRADES_BOOKED_AFTER_RECORD_DATE_TIME = "Trade.findTradesBookedAfterRecordDate";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @NotNull
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "buy_sell")
    @NotNull
    private BuySell buySell;

    @NotNull
    @Column(name = "trade_price")
    private BigDecimal tradePrice;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
    @Column(name = "trade_date")
    private LocalDate tradeDate;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @Column(name = "recorded_timestamp")
    private LocalDateTime recordedTime;

    public Trade() {
    }

    public Trade(int quantity, BuySell buySell, BigDecimal tradePrice, LocalDate tradeDate, LocalDate settlementDate, LocalDateTime recordedTime) {
        this.quantity = quantity;
        this.buySell = buySell;
        this.tradePrice = tradePrice;
        this.tradeDate = tradeDate;
        this.settlementDate = settlementDate;
        this.recordedTime = recordedTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTradePrice() {
        return tradePrice;
    }
}
