package com.sun.prms.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.sun.prms")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final DBProperties dbProperties;

    @Value("${spring.jpa.database-platform}")
    private String dialect;

    @Autowired
    public DatabaseConfiguration(DBProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Default or primary datasource to connect database from property
     * configured with 'spring.datasource' in application-<environment>.yml file
     **/

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }
    
    /**
     * Default entity manager configuration Defining this configuration using
     * '@Primary' annotation we don't have to specify which entityManager or
     * persistence context needs to be use, if we don't define the
     * persistenceUnit name it will use entity manager that is defined as
     * primary
     **/

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(defaultDataSource())
            .properties(dbProperties.hibernateProperties(dialect))
            .packages("com.sun", "com.sun.prms")
            .persistenceUnit("defaultDB")
            .build();
    }

    /**
     * Default or Primary transaction manager configuration
     */
    
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
    
    /**
     * Configuration of lawson database using property 'spring.lawson.datasource'
     * from application-<environment>.yml file
     */
    
    @Bean
    @ConfigurationProperties(prefix = "spring.lawson.datasource")
    public DataSourceProperties lawsonDataSourceProperties() {
        log.info("Building Datasource : 'spring.lawson.datasource' ");
        return new DataSourceProperties();
    }
    
    /***
     * To use this entity manager:
     *
     * '@PersistenceConetxt(unitName="lawson")
     *  private EntityManager entityManager;'
     */
    
    @Bean
    @ConfigurationProperties("spring.lawson.datasource")
    public DataSource lawsonDataSource() {
        return lawsonDataSourceProperties().initializeDataSourceBuilder().build();
    }
    /**
    * Configuration of transaction manager for lawsonEntityManager While using
    * lawsonEntityManager we must use '@Transactional' annotation as:
    * '@Transactional(value="lawsonTransactionManager")
    * 
    * Its required that we specify that which transaction manager needs to be
    * used otherwise it will use default transaction manager
    */
    
    @Bean(name = "lawsonEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean lawsonEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        log.info("Create lawson entity manager factory");
        return builder.dataSource(lawsonDataSource())
                .properties(dbProperties.hibernateProperties(dialect))
                .packages("com.sun.prms.repository")
                .persistenceUnit("lawson")
                .build();
    }

    @Bean(name = "lawsonTransactionManager")
    public PlatformTransactionManager lawsonTransactionManager(
            @Qualifier("lawsonEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
}