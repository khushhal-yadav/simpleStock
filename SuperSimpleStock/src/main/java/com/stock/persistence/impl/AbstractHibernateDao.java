package com.stock.persistence.impl;

import com.stock.persistence.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;

/**
 * Created by khush on 10/09/2016.
 */
public class AbstractHibernateDao<T> implements Dao<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    @PersistenceContext

    protected EntityManager entityManager;

    private Class<T> entityClass;

    public AbstractHibernateDao() {
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type instanceof Class)
            this.entityClass = (Class<T>)type;
    }

    protected <RT> RT withEntityManager(Function<EntityManager, RT> function) {
        EntityManager manager = getEntityManager();
        try {
            return function.apply(manager);
        } finally {
            manager.close();
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<T> getEntityClass() {
        return this.entityClass;
    }

    @Override
    @Transactional
    public T persist(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.persist(entity);
        entityManager.flush();
        return entity;
    }

    @Override
    @Transactional
    public void persist(List<? extends T> entities) {
        EntityManager entityManager = getEntityManager();
        entities.forEach(entity -> entityManager.persist(entity));
        entityManager.flush();
    }

    @Transactional
    private <S extends T> List<S> allOfType(Class<S> entityClass) {
        TypedQuery<S> query = getEntityManager().createQuery(String.format("FROM %s entity", entityClass.getSimpleName()),
                entityClass);
        return query.getResultList();
    }

    @Override
    public T find(Object id) {
        return findByType(getEntityClass(), id);
    }

    @Transactional
    private <S extends T> S findByType(Class<S> entityClass, Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}
