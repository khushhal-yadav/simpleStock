package com.stock.config;

import liquibase.Liquibase;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.H2Database;
import liquibase.database.jvm.HsqlConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by khush on 06/11/2016.
 */
@Configuration
@Profile("test")
public class SpringLiquibaseConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DatabaseConnection databaseConnection() throws SQLException {
        return new HsqlConnection(dataSource.getConnection());
    }

    @Bean
    public H2Database database() throws SQLException {
        final H2Database database = new H2Database();
        database.setDefaultSchemaName(environment.getProperty("liquibase.server"));
        database.setDatabaseChangeLogLockTableName("DATABASECHANGELOGLOCK");
        database.setDatabaseChangeLogTableName("DATABASECHANGELOG");
        database.setConnection(databaseConnection());
        return database;
    }

    @Bean
    public ResourceAccessor resourceAccessor() {
        return new CompositeResourceAccessor(new ClassLoaderResourceAccessor(), new FileSystemResourceAccessor());
    }

    @Bean
    public Liquibase liquibase() throws Exception {
        return new Liquibase(environment.getProperty("liquibase.changeLogFile"), resourceAccessor(), database());
    }

}
