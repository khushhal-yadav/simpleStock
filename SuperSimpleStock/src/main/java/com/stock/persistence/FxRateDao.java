package com.stock.persistence;

import com.stock.domain.FxRate;
import com.stock.domain.SystemDate;

/**
 * Created by khush on 06/11/2016.
 */
public interface FxRateDao extends Dao<FxRate> {

    FxRate findByCurrencyFromTo(String currencyFrom, String currencyTo, SystemDate systemDate);
}
