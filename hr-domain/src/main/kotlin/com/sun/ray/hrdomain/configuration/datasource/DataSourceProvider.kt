package com.sun.ray.hrdomain.configuration.datasource

import com.sun.ray.hrdomain.configuration.datasource.mapping.FollowerProperty
import com.sun.ray.hrdomain.configuration.datasource.mapping.LeaderProperty
import com.sun.ray.hrdomain.configuration.property.PropertyHandler
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.logging.log4j.util.Strings
import org.jasypt.encryption.StringEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.annotation.Resource
import javax.sql.DataSource

@Configuration
@Suppress("Duplicates")
class DataSourceProvider {

    @Autowired
    lateinit var leaderProperty: LeaderProperty

    @Autowired
    lateinit var followerProperty: FollowerProperty

    @Resource
    lateinit var propertyEncryptor: StringEncryptor

    @Bean(name = ["leaderDataSource"])
    fun leaderDataSource(): DataSource {
        val leaderSourceConfig = HikariConfig()
        leaderSourceConfig.maximumPoolSize = leaderProperty.maximumPoolSize
        leaderSourceConfig.maxLifetime = leaderProperty.maxLifetime
        leaderSourceConfig.jdbcUrl = leaderProperty.jdbcUrl
        leaderSourceConfig.username = leaderProperty.username
        leaderSourceConfig.password = getDecryptedString(leaderProperty.password)

        return HikariDataSource(leaderSourceConfig)
    }

    @Bean(name = ["followerDataSource"])
    fun followerDataSource(): DataSource {
        val followerSourceConfig = HikariConfig()
        followerSourceConfig.maximumPoolSize = followerProperty.maximumPoolSize
        followerSourceConfig.maxLifetime = followerProperty.maxLifetime
        followerSourceConfig.jdbcUrl = followerProperty.jdbcUrl
        followerSourceConfig.username = followerProperty.username
        followerSourceConfig.password = getDecryptedString(followerProperty.password)

        return HikariDataSource(followerSourceConfig)
    }

    @Bean
    @ConditionalOnBean(name = ["leaderDataSource", "followerDataSource"])
    fun routingDataSource(
        @Qualifier(value = "leaderDataSource")
        leaderDataSource: DataSource,
        @Qualifier(value = "followerDataSource")
        followerDataSource: DataSource
    ): DataSource {
        val routingDataSource = RoutingDataSource()
        val dataSource: Map<Any, Any> =
            mapOf(DataSourceKeys.LEADER to leaderDataSource, DataSourceKeys.FOLLOWER to followerDataSource)
        routingDataSource.setTargetDataSources(dataSource)
        routingDataSource.setDefaultTargetDataSource(leaderDataSource)
        return routingDataSource
    }

    @Primary
    @Bean(name = ["currentDataSource"])
    @ConditionalOnBean(name = ["routingDataSource"])
    fun currentDataSource(routingDataSource: DataSource) = LazyConnectionDataSourceProxy(routingDataSource)

    private fun getDecryptedString(encryptedString: String) : String {
        return if (encryptedString.startsWith(PropertyHandler.PREFIX)) {
            val encrypted = encryptedString.replace(PropertyHandler.PREFIX, Strings.EMPTY)
                .replace(PropertyHandler.POSTFIX, Strings.EMPTY)
            propertyEncryptor.decrypt(encrypted)
        } else {
            encryptedString
        }
    }
}