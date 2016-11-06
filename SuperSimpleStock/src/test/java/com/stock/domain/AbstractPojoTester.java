package com.stock.domain;

import com.stock.domain.FxRate;
import com.stock.domain.Price;
import com.stock.domain.Stock;
import org.junit.Assert;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by khush on 06/11/2016.
 */
public abstract class AbstractPojoTester<T> {

    private Class<T> pojoClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private static final Map<Class<?>, Object> TEST_VALUES;
    private final Map<Class<?>, Object> customMap = new HashMap<Class<?>, Object>();

    static {
        Map<Class<?>, Object> parameterMap = new HashMap<Class<?>, Object>(){
            {
                put(String.class, "foo");
                put(int.class, 123);
                put(Integer.class, 123);
                put(long.class, 123L);
                put(Long.class, 123L);
                put(double.class, 123.0);
                put(Double.class, 123.0);
                put(boolean.class, true);
                put(Boolean.class, true);
                put(Date.class, new Date(123));
                put(Stock.class, new Stock());
                put(Price.class, new Price());
                put(FxRate.class, new FxRate());
            }
        };
        TEST_VALUES = Collections.unmodifiableMap(parameterMap);
    }

    @Test
    public void testPojo() throws Exception {
        T pojo = getInstance();
        BeanInfo pojoInfo = Introspector.getBeanInfo(pojoClass);
        for (PropertyDescriptor propertyDescriptor :pojoInfo.getPropertyDescriptors()) {
            testProperty(pojo, propertyDescriptor);
        }

    }

    private void testProperty(Object pojo, PropertyDescriptor propertyDescriptor) throws InvocationTargetException, IllegalAccessException {
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        Object testValue = TEST_VALUES.get(propertyType);

       if (testValue == null)
           return;

        Method writeMethod = propertyDescriptor.getWriteMethod();
        Method readeMethod = propertyDescriptor.getReadMethod();
        if (readeMethod != null && writeMethod != null) {
            writeMethod.invoke(pojo, testValue);
            Assert.assertEquals(String.format("property mismatch for %s", propertyDescriptor.getName()),
                    readeMethod.invoke(pojo),
                    testValue);
        }


    }


    protected T getInstance() throws Exception {
        Constructor<T> ctor = pojoClass.getDeclaredConstructor();
        ctor.setAccessible(true);

        return ctor.newInstance();
    }


}
