package com.sun.ray.hrdomain.configuration.datasource

import com.sun.ray.hrdomain.entity.Entities
import com.sun.ray.hrdomain.repository.JpaRepositories
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
    basePackageClasses = [JpaRepositories::class]
)
@EntityScan(
    basePackageClasses = [Entities::class]
)
class JpaProvider