package com.stock.jbehave.setup.builder;

/**
 * Created by khush on 06/11/2016.
 */
public interface EntityBuilder<T> {

    EntityBuilder<T> newInstance();
    T build();
}
