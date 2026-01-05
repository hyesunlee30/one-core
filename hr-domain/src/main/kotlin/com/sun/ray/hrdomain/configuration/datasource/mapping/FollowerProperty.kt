package com.sun.ray.hrdomain.configuration.datasource.mapping

import com.sun.ray.hrdomain.configuration.property.PropertyHandler
import org.apache.logging.log4j.util.Strings
import org.jasypt.encryption.StringEncryptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.follower")
class FollowerProperty {

    @Resource
    lateinit var propertyEncryptor: StringEncryptor

    var jdbcUrl: String = Strings.EMPTY
    var username: String = Strings.EMPTY
    var password: String = Strings.EMPTY
        get() {
            if(field.startsWith(PropertyHandler.PREFIX)){
                val encrypted = field.replace(PropertyHandler.PREFIX, Strings.EMPTY)
                    .replace(PropertyHandler.POSTFIX, Strings.EMPTY)
                return propertyEncryptor.decrypt(encrypted)
            }
            return field
        }
    var maximumPoolSize: Int = Int.MIN_VALUE
    var maxLifetime: Long = Long.MIN_VALUE
    var minimumIdle: Int = Int.MIN_VALUE
}