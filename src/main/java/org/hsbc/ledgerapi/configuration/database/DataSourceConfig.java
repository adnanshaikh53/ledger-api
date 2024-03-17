package org.hsbc.ledgerapi.configuration.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource-master.jdbc-url}")
    private String masterUrl;

    @Value("${spring.datasource-master.username}")
    private String masterUsername;

    @Value("${spring.datasource-master.password}")
    private String masterPassword;

    @Value("${spring.datasource-replica.jdbc-url}")
    private String replicaUrl;

    @Value("${spring.datasource-replica.username}")
    private String replicaUsername;

    @Value("${spring.datasource-replica.password}")
    private String replicaPassword;

    @Bean
    public DataSource dataSource(){
        MasterSlaveRoutingDataSource masterSlaveRoutingDataSource = new MasterSlaveRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseEnvironment.UPDATABLE, masterDataSource());
        targetDataSources.put(DatabaseEnvironment.READONLY, slaveDataSource());
        masterSlaveRoutingDataSource.setTargetDataSources(targetDataSources);

        // Set as all transaction point to master
        masterSlaveRoutingDataSource.setDefaultTargetDataSource(masterDataSource());
        return masterSlaveRoutingDataSource;
    }

    public DataSource slaveDataSource() {

        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(replicaUrl);
        hikariDataSource.setUsername(replicaUsername);
        hikariDataSource.setPassword(replicaPassword);
        return hikariDataSource;
    }

    public DataSource masterDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(masterUrl);
        hikariDataSource.setUsername(masterUsername);
        hikariDataSource.setPassword(masterPassword);
        return hikariDataSource;
    }
}


