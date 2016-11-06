package com.stock.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by khush on 06/11/2016.
 */
@Entity
@Table(name = "system_date", schema = "stockapp")
@NamedQueries({
        @NamedQuery(name = SystemDate.FIND_CURRENT_RECORD, query = "FROM SystemDate WHERE curent = true"),
        @NamedQuery(name = SystemDate.FIND_BY_CALCUATION_DATE, query = "FROM SystemDate WHERE calculationDate = :calculationDate")
})
public class SystemDate {

    public static final String FIND_CURRENT_RECORD = "SystemDate.findCurrentRecord";
    public static final String FIND_BY_CALCUATION_DATE = "SystemDate.findByCalculationDate";

    @Id
    @GeneratedValue
    @Column
    private int id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDate")
    @Column(name = "calculation_date")
    private LocalDate calculationDate;

    @Type(type = "yes_no")
    @NotNull
    @Column(name = "is_current")
    private boolean current = false;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public SystemDate() {
    }

    public SystemDate(LocalDate calculationDate, boolean current, LocalDateTime lastUpdated) {
        this.calculationDate = calculationDate;
        this.current = current;
        this.lastUpdated = lastUpdated;
    }

    public void setCalculationDate(LocalDate calcDate) {
        this.calculationDate = calcDate;
    }

    public void setCurrent(boolean isCurrent) {
        this.current = isCurrent;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calculationDate, current, lastUpdated);
    }

    @Override
    public boolean equals(Object obj) {
        return Optional.ofNullable(obj)
                .filter(o -> o.getClass() == getClass())
                .map(SystemDate.class::cast)
                .filter(o -> Objects.equals(this.calculationDate, o.calculationDate))
                .filter(o -> Objects.equals(this.current, o.current))
                .filter(o -> Objects.equals(this.lastUpdated, o.lastUpdated))
                .isPresent();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
