package org.hsbc.ledgerapi.configuration.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MasterSlaveRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DatabaseContextHolder.getEnvironment();
    }
}
