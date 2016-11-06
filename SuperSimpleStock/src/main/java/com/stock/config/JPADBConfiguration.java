package com.stock.config;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by khush on 06/11/2016.
 */
@Configuration
@PropertySource("classpath:confg/db.properties")
@EnableTransactionManagement
public class JPADBConfiguration {

    private static final String PACKAGE_ROOT = "com.stock";
    private static final String PERSIST_APP_DB = "persist-AppDB";

    @Autowired
    private Environment environment;

    @Value("${db.connection.pool.enable:true}")
    private boolean connectionPoolingEnable;

    @Value("${db.connection.pool.max:10}")
    private int maxPoolConnections;

    @Value("${db.connection.pool.min:2}")
    private int minPoolConnections;

    @Value("${db.connection.pool.expirationMinutes:10}")
    private int poolExpirationMinutes;

    @Value("${db.connection.pool.validationMinutes:5}")
    private int poolValidationMinutes;

    @Value("${db.show-sql:false}")
    private boolean showsql;

    private Properties generateDefaultProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", getDatabaseDialect().getName());
        properties.setProperty("hibernate.show_sql", Boolean.toString(showsql));
        properties.setProperty("hibernate.cache.use_second_level_cache", "true");
        properties.setProperty("hibernate.cache.region.factory_class", "com.hazelcast.hibernate.HazelcastCacheRegionFactory");
        properties.setProperty("hibernate.cache.provider_configuration_file_resource_path", "hazelcast-hibernate.xml");
        return properties;
    }

    private Class<? extends Dialect> getDatabaseDialect() {return H2Dialect.class;}

    private JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public DataSource dataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:" + environment.getProperty("liquibase.database") + ":DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;INIT=create schema if not exists " + environment.getProperty("liquibase.database"));
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName(PERSIST_APP_DB);
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setDataSource(dataSource());
        em.setPackagesToScan(PACKAGE_ROOT);
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaProperties(generateDefaultProperties());
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return  transactionManager;
    }
}
