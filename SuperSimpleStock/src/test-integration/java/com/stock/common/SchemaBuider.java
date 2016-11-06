package com.stock.common;

import liquibase.Liquibase;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.exception.LiquibaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by khush on 06/11/2016.
 */
@Component
@Profile("test")
public class SchemaBuider {

    private String contexts;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final ArrayList<String> refreshQueries = new ArrayList<>();

    private final AtomicBoolean initialised = new AtomicBoolean();
    private final Liquibase liquibase;    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SchemaBuider(Liquibase liquibase, JdbcTemplate jdbcTemplate) {
        this.liquibase = liquibase;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        try {
            softUpdate(null);
        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isInitialised() {return initialised.get();}

    private void softUpdate(String contexts) throws LiquibaseException {
        if (initialised.get()) return;
        update(contexts);
        
    }

    public void update(String contexts) throws LiquibaseException {
        if (initialised.getAndSet(true) == false) {
            this.contexts = contexts;
            forceUpdate(contexts);
        } else {
            reset();
        }
    }

    private void forceUpdate(String contexts) throws LiquibaseException {
        DatabaseChangeLog changeLog = liquibase.getDatabaseChangeLog();
        List<ChangeSet> changeSets = changeLog.getChangeSets();
        Set<String> tableNames = new HashSet<>();
        changeSets.forEach(changeSet -> changeSet.getChanges().stream().forEach(change -> {
            Set<DatabaseObject> affectedDatabaseObjects = change.getAffectedDatabaseObjects(liquibase.getDatabase());
            affectedDatabaseObjects.forEach(databaseObject -> {if (databaseObject instanceof Table) tableNames.add(databaseObject.getName());});
        }));

        jdbcTemplate.execute("DROP TABLE IF EXISTS STOCKAPP.DATABASECHANGELOG");
        tableNames.forEach(tableName -> jdbcTemplate.execute("DROP TABLE IF EXISTS STOCKAPP." + tableName));
        liquibase.update(contexts);

        refreshQueries.clear();

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        Set<BeanDefinition> candidates = scanner.findCandidateComponents("com.stock.domain");
        candidates.forEach(candidate -> {
            Class<?> entityType = null;
            String tableName = null;

            try {
                entityType = Class.forName(candidate.getBeanClassName());
                javax.persistence.Table table;
                if ((table = entityType.getAnnotation(javax.persistence.Table.class)) != null && table.name() != null) {
                    tableName = table.name();
                    refreshQueries.add("DELETE FROM STOCKAPP." + tableName);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tableNames.clear();
        initialised.set(true);
    }

    private void reset() {
        if (isInitialised()) {
            log.debug("Dropping domain tables");
            refreshQueries.forEach(refreshQuery -> log.debug("\t{}", refreshQuery));
            //jdbcTemplate.batchUpdate(refreshQueries.toArray(new String[] {}));
        }
    }
    
    
}
