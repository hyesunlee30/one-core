package com.sun.ray.hrinterface

import com.sun.ray.hrdomain.HrDomain
import com.sun.ray.hrdomain.configuration.datasource.DataSourceProvider
import com.sun.ray.hrdomain.configuration.datasource.JpaProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling

@Suppress("SpellCheckingInspection")
@Import(value = [DataSourceProvider::class, JpaProvider::class])
@SpringBootApplication(
    scanBasePackageClasses = [HrDomain::class, HrInterface::class],
    exclude = [DataSourceAutoConfiguration::class, ErrorMvcAutoConfiguration::class]
)
@EnableCaching
@EnableScheduling
class OnecoreHrApplication

fun main(args: Array<String>) {
    runApplication<OnecoreHrApplication>(*args)
}