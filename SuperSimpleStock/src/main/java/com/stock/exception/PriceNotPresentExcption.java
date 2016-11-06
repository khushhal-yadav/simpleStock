package com.stock.exception;

import javax.persistence.NoResultException;

/**
 * Created by khush on 06/11/2016.
 */
public class PriceNotPresentExcption extends RuntimeException {

    public PriceNotPresentExcption(String message, NoResultException nre) {
        super(message, nre);
    }
}
