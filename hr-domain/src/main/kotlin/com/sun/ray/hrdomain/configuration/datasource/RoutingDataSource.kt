package com.sun.ray.hrdomain.configuration.datasource

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class RoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any =
        when {
            TransactionSynchronizationManager.isCurrentTransactionReadOnly() -> DataSourceKeys.FOLLOWER
            else -> DataSourceKeys.LEADER
        }
}