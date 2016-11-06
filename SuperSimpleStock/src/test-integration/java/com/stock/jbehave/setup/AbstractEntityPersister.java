package com.stock.jbehave.setup;

import com.stock.jbehave.setup.builder.EntityBuilder;
import com.stock.jbehave.setup.resolve.EntityResolver;
import com.stock.persistence.Dao;
import org.jbehave.core.model.ExamplesTable;
import org.mockito.internal.util.reflection.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by khush on 06/11/2016.
 */
public abstract class AbstractEntityPersister<T> implements EntityPersister<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final Object NULL_VALUE = "null";

    private Class<T> entityClass;

    public AbstractEntityPersister() {
        Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (type instanceof  Class)
            this.entityClass = (Class<T>) type;
    }

    @Override
    public Map<String, T> persist(ExamplesTable examplesTable, Map<String, Map<String, Object>> persistenceContext) throws Exception {
        Map<String, T> objectMap = buildObject(examplesTable, persistenceContext);
        getDao().persist(new ArrayList<T>(objectMap.values()));
        return objectMap;
    }

    protected abstract Dao<T> getDao();

    protected abstract EntityBuilder<T> getEntityBuilder();

    private Map<String, T> buildObject(ExamplesTable examplesTable, Map<String, Map<String, Object>> persistenceContext) {
        Map<String, T> objectMap = new HashMap<>();
        int rowNum = 0;

        for (Map<String, String> row : examplesTable.getRows()) {
            EntityBuilder<T> entityBuilder = getEntityBuilder().newInstance();

            for (String field : row.keySet()) {
                Field builderField = findField(entityBuilder, field);
                Class<?> fiedType = builderField.getType();

                if ((NULL_VALUE).equals(row.get(field).trim()))
                    Whitebox.setInternalState(entityBuilder, field, null);
                else if ((String.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, row.get(field).trim());
                else if ((Boolean.class).equals(fiedType) || (boolean.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, ("Y").equals(row.get(field).trim()) ? true : false);
                else if ((Integer.class).equals(fiedType) || (int.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, Integer.parseInt(row.get(field).trim()));
                else if ((Long.class).equals(fiedType) || (long.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, Long.parseLong(row.get(field).trim()));
                else if ((BigDecimal.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, new BigDecimal(row.get(field).trim()));
                else if ((LocalDate.class).equals(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, LocalDate.parse(row.get(field).trim()));
                else if (Enum.class.isAssignableFrom(fiedType))
                    Whitebox.setInternalState(entityBuilder, field, Enum.valueOf((Class<Enum>) fiedType, row.get(field).trim()));
                else {
                    EntityResolver<?> entityResolver = findEntityResolver(fiedType);

                    if (entityResolver != null) {
                        Object entity = entityResolver.resolveEntity(row.get(field), persistenceContext);
                        Whitebox.setInternalState(entityBuilder, field, entity);
                    } else
                        throw new IllegalArgumentException("Can't fins resolver fro type: " + fiedType);
                }
            }
            objectMap.put(String.valueOf(rowNum), entityBuilder.build());
            rowNum++;
        }


        return objectMap;
    }

    protected abstract List<EntityResolver<?>> getEntityResolvers();

    private EntityResolver<?> findEntityResolver(Class<?> fiedType) {
        for (EntityResolver<?> entityResolver : getEntityResolvers()) {
            if (entityResolver.validateFor(fiedType))
                return entityResolver;
        }
        return null;
    }

    private Field findField(EntityBuilder<T> entityBuilder, String field) {
        Class<?> builderClass = entityBuilder.getClass();
        Field builderField = null;

        while (builderField == null ) {
            try {
                builderField = builderClass.getDeclaredField(field);
            } catch (NoSuchFieldException e) {
                log.info("Couldn't find field " + field + " in class " + builderClass.getName() + " so checking superclass");
            }
            if (builderField == null) {
                builderClass = builderClass.getSuperclass();
                if (builderClass == null)
                    throw new IllegalArgumentException("Field " + field + " doesn't exist in class " + entityBuilder.getClass() +
                                " or super class");
            }
        }
        return builderField;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }
}
