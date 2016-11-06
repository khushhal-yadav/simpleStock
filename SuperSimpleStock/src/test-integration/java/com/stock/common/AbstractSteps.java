package com.stock.common;

import com.stock.service.SystemDateService;
import com.stock.jbehave.setup.EntityPersister;
import liquibase.exception.LiquibaseException;
import org.apache.commons.lang.StringUtils;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by khush on 06/11/2016.
 */
public class AbstractSteps implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment environment;

    @Autowired
    private SchemaBuider schemaBuider;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private SystemDateService systemDateService;

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    @Autowired
    private List<EntityPersister<?>> entityPersisters;


    protected Map<String, Map<String, Object>> persistenceContext = new HashMap<>();

    public final Map<Class<?>, EntityPersister<?>> entityPersisterMap = new HashMap<>();

    @BeforeScenario
    public void prepareDatabase() throws LiquibaseException {
        String contexts = StringUtils.join(environment.getActiveProfiles(), ",");
        schemaBuider.update(contexts);
    }

    @Given("system date is set to $dateString")
    public void setSystemDate(String dateString) {
        log.debug("Given 1 a system date");
        LocalDate systemDate = LocalDate.parse(dateString, AppStory.DATE_TIME_FORMATTER);
        systemDateService.setCurrentSystemDate(systemDate);
    }

    @When("the following $entity items exist in the system: $exampleTable")
    public void setupEntityInSystem(String entityName, ExamplesTable examplesTable) {
        Class<?> entityClass = getEntityClassBySimpleName(entityName);
        EntityPersister<?> entityPersister = entityPersisterMap.get(entityClass);
        Map<String, Object> entities = (Map<String, Object>)getEntitiesMap(() -> entityPersister.persist(examplesTable, persistenceContext));
        persistenceContext.put(entityClass.getSimpleName(), entities);

        log.debug("When 1 I do something");
    }

    private static <T> T getEntitiesMap(Callable<T> action) {
        try {
            return action.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> getEntityClassBySimpleName(String entityName) {
        return entityManagerFactory.getMetamodel().getEntities().stream()
                .map(EntityType::getBindableJavaType)
                .filter(Objects::nonNull)
                .filter(type -> Objects.equals(type.getSimpleName(), entityName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No entity type with a simple name of " + entityName + " found"));
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        entityPersisterMap.putAll(entityPersisters.stream().collect(Collectors.toMap(EntityPersister::getEntityClass, Function.identity())));

    }
}
