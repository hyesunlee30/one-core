import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("plugin.serialization")
    kotlin("plugin.jpa")
    kotlin("plugin.allopen")
    kotlin("plugin.noarg")
    kotlin("kapt")
    idea
}

java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    api("com.querydsl:querydsl-jpa:4.4.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.projectreactor:reactor-spring:1.0.1.RELEASE")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.querydsl:querydsl-mongodb:4.4.0")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")
    implementation("com.github.consoleau:kassava:2.1.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.google.code.gson:gson")
    implementation("commons-io:commons-io:2.11.0")
    implementation("io.swagger:swagger-annotations:1.5.20")
    implementation("net.gpedro.integrations.slack:slack-webhook:1.4.0")
    implementation("io.github.resilience4j:resilience4j-spring-boot2:1.7.1")
    implementation("io.github.resilience4j:resilience4j-reactor:1.7.1")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    // actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    // aws
    implementation("software.amazon.awssdk:s3:2.17.100")

    api("net.javacrumbs.shedlock:shedlock-spring:4.42.0")
    implementation("net.javacrumbs.shedlock:shedlock-provider-redis-spring:4.42.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")

    kapt("com.querydsl:querydsl-apt:4.4.0:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")
    runtimeOnly("com.oracle.database.jdbc:ojdbc8:12.2.0.1")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("com.ninja-squad:springmockk:3.1.2")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.4.11")
    testImplementation("com.appmattus.fixture:fixture:1.2.0")
    implementation("com.appmattus.fixture:fixture:1.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.5")
    }
}

allOpen {
    annotation("org.springframework.data.mongodb.core.mapping.Document")
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

noArg {
    annotation("javax.persistence.Entity")
}

idea {
    module {
        val kaptMain = file("$buildDir/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
