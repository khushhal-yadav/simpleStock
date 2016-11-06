package com.stock.persistence;

import java.util.List;

/**
 * Created by khush on 06/11/2016.
 */
public interface Dao<T> {

    T persist(T entity);
    void persist(List<? extends T> entities);
    T find(Object id);

}
