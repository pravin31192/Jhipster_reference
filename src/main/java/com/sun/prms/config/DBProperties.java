package com.sun.prms.config;

/**
 * @author o-vishal
 * created date: Jun 6, 2018 3:30:09 AM
 * 
 * This class contains methods about common hibernate properties configuration
 */

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBProperties {

    @Value("${spring.jpa.show-sql}")
    private String showSql;

    @Value("${spring.jpa.properties.hibernate.id.new_generator_mappings}")
    private String hibernateIdGeneratorMapping;

    @Value("${spring.jpa.properties.hibernate.cache.use_second_level_cache}")
    private String hibernateSecondLevelCache;

    @Value("${spring.jpa.properties.hibernate.cache.use_query_cache}")
    private String hibernateQueryCache;

    @Value("${spring.jpa.properties.hibernate.generate_statistics}")
    private String hibernateStatistics;

    /**
     * @param dialect Hibernate dialect of database
     * This method is used to configure common hibernate property for Database
     * configuration
     */
    public Map<String, Object> hibernateProperties(String dialect) {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", dialect);
        properties.setProperty("hibernate.show_sql", showSql);
        properties.setProperty("hibernate.id.new_generator_mappings", hibernateIdGeneratorMapping);
        properties.setProperty("hibernate.cache.use_second_level_cache", hibernateSecondLevelCache);
        properties.setProperty("hibernate.cache.use_query_cache", hibernateQueryCache);
        properties.setProperty("hibernate.generate_statistics", hibernateStatistics);
        return properties.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue()));
    }

}

