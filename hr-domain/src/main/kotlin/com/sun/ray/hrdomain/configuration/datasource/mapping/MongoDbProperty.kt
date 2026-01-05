package com.sun.ray.hrdomain.configuration.datasource.mapping

import com.sun.ray.hrdomain.configuration.property.PropertyHandler
import org.apache.logging.log4j.util.Strings
import org.jasypt.encryption.StringEncryptor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import javax.annotation.Resource

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mongo-db")
class MongoDbProperty {
    @Resource
    lateinit var propertyEncryptor: StringEncryptor

    var database: String = Strings.EMPTY
    var uri: String = Strings.EMPTY
        get() {
            if(field.startsWith(PropertyHandler.MONGODB_PREFIX)){
                val encrypted = field.replace(PropertyHandler.MONGODB_PREFIX, Strings.EMPTY)
                    .replace(PropertyHandler.POSTFIX, Strings.EMPTY)
                return propertyEncryptor.decrypt(encrypted)
            }
            return field
        }
    var trustStoreFilepath: String = Strings.EMPTY
    var trustStoreType: String = Strings.EMPTY
    var trustStorePassword: String = Strings.EMPTY
        get() {
            if(field.startsWith(PropertyHandler.PREFIX)){
                val encrypted = field.replace(PropertyHandler.PREFIX, Strings.EMPTY)
                    .replace(PropertyHandler.POSTFIX, Strings.EMPTY)
                return propertyEncryptor.decrypt(encrypted)
            }
            return field
        }
}