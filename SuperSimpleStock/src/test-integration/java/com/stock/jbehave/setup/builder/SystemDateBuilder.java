package com.stock.jbehave.setup.builder;

import com.stock.domain.SystemDate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by khush on 06/11/2016.
 */
@Component
public class SystemDateBuilder implements EntityBuilder<SystemDate> {

    private LocalDate calculationDate;
    private boolean current;
    private LocalDateTime lastUpdated;

    @Override
    public EntityBuilder<SystemDate> newInstance() {
        return new SystemDateBuilder();
    }

    @Override
    public SystemDate build() {
        SystemDate systemDate = new SystemDate(calculationDate, current, lastUpdated);
        return systemDate;
    }

    public SystemDateBuilder() {
    }

    public SystemDateBuilder(LocalDate calculationDate, boolean current, LocalDateTime lastUpdated) {
        this.calculationDate = calculationDate;
        this.current = current;
        this.lastUpdated = lastUpdated;
    }
}
